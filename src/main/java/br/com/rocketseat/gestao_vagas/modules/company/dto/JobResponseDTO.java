package br.com.rocketseat.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {

    private String description;
    private String level;
    private String benefits;
    private UUID companyId;
    private List<UUID> candidates;
}
