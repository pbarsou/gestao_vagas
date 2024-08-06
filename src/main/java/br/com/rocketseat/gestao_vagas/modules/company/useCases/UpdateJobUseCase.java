package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.gestao_vagas.modules.company.dto.CreateUpdateJobDTO;
import br.com.rocketseat.gestao_vagas.modules.company.dto.JobResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
public class UpdateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public JobResponseDTO execute(CreateUpdateJobDTO updateJobDTO, UUID jobId, UUID companyId) throws AccessDeniedException {

        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        if(!job.getCompany_id().equals(companyId)) {
            throw new AccessDeniedException("You don't have access for alter this job.");
        }

        var candidates = applyJobRepository.findByJobId(job.getId()).stream().map(ApplyJobEntity::getCandidateId).toList();

        job.setDescription(updateJobDTO.getDescription());
        job.setBenefits(updateJobDTO.getBenefits());
        job.setLevel(updateJobDTO.getLevel());

        var jobResponseDTO = JobResponseDTO.builder()
                .description(job.getDescription())
                .benefits(job.getBenefits())
                .level(job.getLevel())
                .companyId(companyId)
                .candidates(candidates)
                .build();

        this.jobRepository.save(job);

        return jobResponseDTO;
    }
}
