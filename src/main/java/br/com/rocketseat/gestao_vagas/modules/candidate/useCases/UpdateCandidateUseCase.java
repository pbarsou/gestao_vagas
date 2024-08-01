package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());

        CandidateEntity candidate = this.candidateRepository.findById(candidateEntity.getId()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Candidate not found.");
        });

        if(!candidateEntity.getUsername().equals(candidate.getUsername())) {
            this.candidateRepository.findByUsername(candidateEntity.getUsername()).ifPresent((user) -> {
                throw new UserFoundException();
            });
        }

        if(!candidateEntity.getEmail().equals(candidate.getEmail())) {
            this.candidateRepository.findByEmail(candidateEntity.getEmail()).ifPresent((user) -> {
                throw new UserFoundException();
            });
        }

        candidate.setName(candidateEntity.getName());
        candidate.setUsername(candidateEntity.getUsername());
        candidate.setEmail(candidateEntity.getEmail());
        candidate.setPassword(password);
        candidate.setDescription(candidateEntity.getDescription());
        candidate.setCurriculum(candidateEntity.getCurriculum());

        return this.candidateRepository.save(candidate);
    }
}
