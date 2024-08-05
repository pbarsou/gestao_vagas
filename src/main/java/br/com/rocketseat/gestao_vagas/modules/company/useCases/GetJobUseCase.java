package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.dto.JobResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobResponseDTO execute(UUID jobId) {

        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        var jobResponseDTO = JobResponseDTO.builder()
                .description(job.getDescription())
                .benefits(job.getBenefits())
                .level(job.getLevel())
                .companyId(job.getCompany_id())
                .candidates(job.getCandidatesId())
                .build();

        return jobResponseDTO;
    }
}
