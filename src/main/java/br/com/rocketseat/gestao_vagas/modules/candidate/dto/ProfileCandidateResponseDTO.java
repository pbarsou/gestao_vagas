package br.com.rocketseat.gestao_vagas.modules.candidate.dto;

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
public class ProfileCandidateResponseDTO {

    @Schema(example = "Eden")
    private String name;
    @Schema(example = "eden")
    private String username;
    @Schema(example = "eden@gmail.com")
    private String email;
    @Schema(example = "Desenvolvedor Java")
    private String description;
    private List<UUID> jobApplications;
}
