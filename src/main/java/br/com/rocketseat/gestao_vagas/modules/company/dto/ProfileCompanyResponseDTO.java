package br.com.rocketseat.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCompanyResponseDTO {

    @Schema(example = "Ambev")
    private String name;
    @Schema(example = "ambev")
    private String username;
    @Schema(example = "ambev@ab-inbev.com")
    private String email;
    @Schema(example = "https://www.ambev.com.br")
    private String website;
    @Schema(example = "Maior cervejaria da América Latina.")
    private String description;
}
