package br.com.rocketseat.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthCandidateRequestDTO(
        @Schema(example = "eden")
        String username,
        @Schema(example = "eden@123")
        String password
) {}
