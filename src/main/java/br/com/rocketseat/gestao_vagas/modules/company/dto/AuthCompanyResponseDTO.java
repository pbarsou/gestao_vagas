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
public class AuthCompanyResponseDTO {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjI3OTI5NjIsImlzcyI6InZhZ2FzbmV0Iiwicm9sZXMiOlsiQ09NUEFOWSJdLCJzdWIiOiI3OTUwMGYyZi00NzczLTRjODMtYmUxZC0yYTM1YzE1YjZkNjUifQ.6HZCphiIt05-pnyxMmx3vZvbKe1bc3Q1DHDMRwlnZ-E")
    private String acess_token;
    @Schema(example = "1722792962180")
    private Long expires_in;
}
