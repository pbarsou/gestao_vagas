package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.company.dto.JobResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobCreator {

    private static List<UUID> candidatesId = new ArrayList<>();

    public static JobEntity createJobToBeSaved() {

        return JobEntity.builder()
                .description("Desenvolvedor Junior")
                .benefits("Gympass, Plano de Saúde, Day Off, VA/VR")
                .level("JUNIOR")
                .build();
    }

    public static JobEntity createValidJob() {

        return JobEntity.builder()
                .id(UUID.fromString("5258e073-f0c2-4f51-afcb-dfd7aa33b08e"))
                .description("Desenvolvedor Junior")
                .benefits("Gympass, Plano de Saúde, Day Off, VA/VR")
                .level("JUNIOR")
                .build();
    }

    public static JobEntity createValidUpdateJob() {

        return JobEntity.builder()
                .id(UUID.fromString("5258e073-f0c2-4f51-afcb-dfd7aa33b08e"))
                .description("Desenvolvedor Senior")
                .benefits("Gympass, Plano de Saúde, Day Off, VA/VR")
                .level("SENIOR")
                .build();
    }

    public static JobResponseDTO createJobResponse() {

        return JobResponseDTO.builder()
                .description("Desenvolvedor Senior")
                .benefits("Gympass, Plano de Saúde, Day Off, VA/VR")
                .level("SENIOR")
                .companyId(UUID.fromString("a62fb649-dec4-44d8-9a7a-faf78388137a"))
                .candidates(candidatesId)
                .build();
    }
}
