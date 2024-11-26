package br.com.alunoonline.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HistoricoAlunoDTO {

    private String nomeAluno;
    private String emailAluno;
    private String cpfAluno;
    private List<DisciplinasAlunoDTO> disciplinasAlunoResponses;
}
