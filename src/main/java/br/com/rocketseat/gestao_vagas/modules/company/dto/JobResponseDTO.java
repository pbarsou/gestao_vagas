package br.com.rocketseat.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "Vaga para pessoa desenvolvedora Júnior")
    private String description;
    @Schema(example = "Gympass, Plano de Saúde, Day Off, VA/VR")
    private String benefits;
    @Schema(example = "JÚNIOR")
    private String level;
    private UUID companyId;
    private List<UUID> candidates;
}
