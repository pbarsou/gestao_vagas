package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.candidate.dto.JobApplicationResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;

import java.util.List;
import java.util.UUID;

public class ApplyJobCreator {

    private static List<UUID> jobApplicationsId = List.of(UUID.fromString("bf6451df-edea-431c-a06d-e365f25fe1e6"), UUID.fromString("677f1354-83dc-40cd-8fb8-5fdd6513c603"));

    public static ApplyJobEntity createApplyJobToBeSaved() {

        return ApplyJobEntity.builder()
                .jobId(UUID.fromString("5258e073-f0c2-4f51-afcb-dfd7aa33b08e"))
                .candidateId(UUID.fromString("0e80a8e5-a265-4228-801b-a2f32387e8bd"))
                .build();
    }

    public static ApplyJobEntity createValidApplyJob() {

        return ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .jobId(UUID.fromString("5258e073-f0c2-4f51-afcb-dfd7aa33b08e"))
                .candidateId(UUID.fromString("0e80a8e5-a265-4228-801b-a2f32387e8bd"))
                .build();
    }
}
