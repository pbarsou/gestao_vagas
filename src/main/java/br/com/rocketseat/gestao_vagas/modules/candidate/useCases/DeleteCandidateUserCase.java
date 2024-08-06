package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCandidateUserCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public void execute(UUID idCandidate) {
        CandidateEntity candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UsernameNotFoundException("Candidate not found.");
        });

        this.candidateRepository.delete(candidate);
    }
}
