package br.com.alunoonline.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtualizarNotasDTO {

    private Double nota1;
    private Double nota2;
}
