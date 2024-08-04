package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileCandidateResponseDTO execute(CandidateEntity candidateEntity, UUID candidateId) {

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());

        CandidateEntity candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
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

        this.candidateRepository.save(candidate);

        var candidateResponseDTO = ProfileCandidateResponseDTO.builder()
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .jobApplications(candidate.getJobApplicationsId())
                .build();

        return candidateResponseDTO;
    }
}
