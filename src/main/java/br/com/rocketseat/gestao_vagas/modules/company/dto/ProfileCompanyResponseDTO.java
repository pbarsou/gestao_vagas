package br.com.rocketseat.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCompanyResponseDTO {

    private String name;
    private String username;
    private String email;
    private String website;
    private String description;
}
