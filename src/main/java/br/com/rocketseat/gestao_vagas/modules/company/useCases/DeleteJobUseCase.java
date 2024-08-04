package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
public class DeleteJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public void execute(UUID jobId, UUID companyId) throws AccessDeniedException {
        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        if(!job.getCompany_id().equals(companyId)) {
            throw new AccessDeniedException("You don't have access to delete this job.");
        }

        this.jobRepository.delete(job);
    }
}
