package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRespository alunoRepository;

    public void criarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodosAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno aluno) {
        Aluno alunoDoBancoDeDados = buscarAlunoPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado do banco de dados"));

        alunoRepository.save(Aluno.builder()
                .id(alunoDoBancoDeDados.getId())
                .nome(aluno.getNome())
                .cpf(aluno.getCpf())
                .email(aluno.getEmail())
                .build());
    }
}
