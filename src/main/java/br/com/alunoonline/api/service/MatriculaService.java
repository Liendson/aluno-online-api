package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dto.AtualizarNotasDTO;
import br.com.alunoonline.api.dto.DisciplinasAlunoDTO;
import br.com.alunoonline.api.dto.HistoricoAlunoDTO;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Matricula;
import br.com.alunoonline.api.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    public static final double MEDIA_PARA_APROVACAO = 7.0;

    private final MatriculaRepository matriculaAlunoRepository;

    public void criarMatricula(Matricula matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void trancarMatricula(Long matriculaAlunoId) {
        Matricula matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula Aluno não encontrada!"));

        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matricula com o status MATRICULADO");
        }

        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void atualizaNotas(Long matriculaAlunoId, AtualizarNotasDTO atualizarNotasRequest) {
        Matricula matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula Aluno não encontrada!"));

        if (Objects.nonNull(atualizarNotasRequest.getNota1())) {
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }

        if (Objects.nonNull(atualizarNotasRequest.getNota2())) {
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }

        matriculaAluno.setStatus(calculaMedia(matriculaAluno) >= MEDIA_PARA_APROVACAO ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        matriculaAlunoRepository.save(matriculaAluno);

    }

    private double calculaMedia(Matricula matriculaAluno) {
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();

        if (nota1 != null && nota2 != null) {
            double media = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
            return media;
        }
        return 0;
    }

    public HistoricoAlunoDTO emitirHistorico(Long alunoId) {
        List<Matricula> matriculasDoAluno = matriculaAlunoRepository.findByAlunoId(alunoId);

        if (matriculasDoAluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matriculas");
        }

        Aluno aluno = matriculasDoAluno.stream().findFirst().get().getAluno();
        HistoricoAlunoDTO historicoAluno =
                HistoricoAlunoDTO.builder()
                        .nomeAluno(aluno.getNome())
                        .cpfAluno(aluno.getCpf())
                        .emailAluno(aluno.getEmail())
                        .build();

        historicoAluno.setDisciplinasAlunoResponses(
                matriculasDoAluno.stream().map(matricula ->
                                DisciplinasAlunoDTO.builder()
                                        .nomeDisciplina(matricula.getDisciplina().getNome())
                                        .nomeProfessor(matricula.getDisciplina().getProfessor().getNome())
                                        .nota1(matricula.getNota1())
                                        .nota2(matricula.getNota2())
                                        .media(calculaMedia(matricula))
                                        .status(matricula.getStatus())
                                        .build())
                        .toList());

        return historicoAluno;
    }
}
