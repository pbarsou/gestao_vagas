package br.com.rocketseat.gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateUpdateJobDTO {

    private String description;
    private String benefits;
    private String level;
}
