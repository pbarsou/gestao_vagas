package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.gestao_vagas.modules.company.dto.JobResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
public class GetJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public JobResponseDTO execute(UUID jobId, UUID companyId) throws AccessDeniedException {

        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        System.out.println(job.getId());
        System.out.println(companyId);

        if(!job.getCompany_id().equals(companyId)) {
            throw new AccessDeniedException("You don't have access to delete this job.");
        }

        var candidates = applyJobRepository.findByJobId(job.getId()).stream().map(ApplyJobEntity::getCandidateId).toList();

        var jobResponseDTO = JobResponseDTO.builder()
                .description(job.getDescription())
                .benefits(job.getBenefits())
                .level(job.getLevel())
                .companyId(job.getCompany_id())
                .candidates(candidates)
                .build();

        return jobResponseDTO;
    }

    public JobResponseDTO executeAdmin(UUID jobId) {

        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        var candidates = applyJobRepository.findByJobId(job.getId()).stream().map(ApplyJobEntity::getCandidateId).toList();

        var jobResponseDTO = JobResponseDTO.builder()
                .description(job.getDescription())
                .benefits(job.getBenefits())
                .level(job.getLevel())
                .companyId(job.getCompany_id())
                .candidates(candidates)
                .build();

        return jobResponseDTO;
    }
}
