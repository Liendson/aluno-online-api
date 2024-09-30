package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public void criarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> listarTodosProfessores() {
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }

    public void deletarProfessorPorId(Long id) {
        professorRepository.deleteById(id);
    }

    public void atualizarProfessorPorId(Long id, Professor professor) {
        Professor professorDoBancoDeDados = buscarProfessorPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado do banco de dados"));

        professorRepository.save(Professor.builder()
                .id(professorDoBancoDeDados.getId())
                .nome(professor.getNome())
                .cpf(professor.getCpf())
                .email(professor.getEmail())
                .build());
    }
}
