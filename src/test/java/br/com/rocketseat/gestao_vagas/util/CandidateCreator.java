package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CandidateCreator {

    private static final List<UUID> jobApplicationsId = List.of(UUID.fromString("5258e073-f0c2-4f51-afcb-dfd7aa33b08e"));

    public static CandidateEntity createCandidateToBeSaved() {

        return CandidateEntity.builder()
                .name("Eden")
                .username("eden")
                .email("eden@gmail.com")
                .password("eden@123")
                .description("Desenvolvedor Júnior.")
                .build();
    }

    public static CandidateEntity createValidCandidate() {

        return CandidateEntity.builder()
                .id(UUID.fromString("0e80a8e5-a265-4228-801b-a2f32387e8bd"))
                .name("Eden")
                .username("eden")
                .email("eden@gmail.com")
                .password("eden@123")
                .description("Desenvolvedor Júnior.")
                .build();
    }

    public static CandidateEntity createValidUpdatedCandidate() {

        return CandidateEntity.builder()
                .id(UUID.fromString("0e80a8e5-a265-4228-801b-a2f32387e8bd"))
                .name("Eden")
                .username("iameden")
                .email("eden@gmail.com")
                .password("eden@123")
                .description("Desenvolvedor Júnior.")
                .build();
    }

    public static ProfileCandidateResponseDTO createProfileCandidateResponse() {

        return ProfileCandidateResponseDTO.builder()
                .username("moritakamashiro")
                .name("Mashiro Moritaka")
                .email("mashiro.moritaka@gmail.com")
                .description("Mangaká. Meu objetivo é me tornar o maior mangaká de todos os tempos.")
                .jobApplications(jobApplicationsId)
                .build();
    }
}
