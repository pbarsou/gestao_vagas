package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.JobApplicationFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.JobApplicationResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyForJobUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;

    public JobApplicationResponseDTO execute(UUID candidateId, UUID jobiD) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Candidate not found.");
        });

        var job = this.jobRepository.findById(jobiD).orElseThrow(() -> {
            throw new UsernameNotFoundException("Job not found.");
        });

        if(job.getCandidates().contains(candidate)) {
            throw new JobApplicationFoundException();
        }

        job.getCandidates().add(candidate);
        this.jobRepository.save(job);

        return JobApplicationResponseDTO.builder()
                .candidateId(candidateId)
                .jobApplications(candidate.getJobApplicationsId())
                .build();
    }
}
