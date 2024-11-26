package br.com.alunoonline.api.dto;

import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplinasAlunoDTO {

    private String nomeDisciplina;
    private String nomeProfessor;
    private Double nota1;
    private Double nota2;
    private Double media;
    private MatriculaAlunoStatusEnum status;
}
