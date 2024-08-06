package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.CandidateNotFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.JobApplicationFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.JobNotFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyForJobUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new CandidateNotFoundException();
        });

        var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        var candidates = applyJobRepository.findByJobId(job.getId()).stream().map(ApplyJobEntity::getCandidateId).toList();

        if(candidates.contains(candidate)) {
            throw new JobApplicationFoundException();
        }

        var applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();

        return applyJobRepository.save(applyJob);
    }
}
