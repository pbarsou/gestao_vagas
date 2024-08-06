package br.com.rocketseat.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Gympass, Plano de Saúde, Day Off, VA/VR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "JÚNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
