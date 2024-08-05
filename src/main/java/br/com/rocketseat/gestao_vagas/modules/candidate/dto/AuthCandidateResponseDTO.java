package br.com.rocketseat.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateResponseDTO {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjI4MTc3NjAsImlzcyI6InZhZ2FzbmV0Iiwic3ViIjoiNGJmMTBjZjQtM2Q2Zi00NmVmLWFkNzAtY2MxNzU4ZDQzNjhlIiwicm9sZXMiOlsiQ0FORElEQVRFIl19.QMI8gEEUi3XCjGqYH3bvh5sXAqlvjOufpDn6SrINRSk")
    private String access_token;
    @Schema(example = "1722817760713")
    private Long expires_in;
}
