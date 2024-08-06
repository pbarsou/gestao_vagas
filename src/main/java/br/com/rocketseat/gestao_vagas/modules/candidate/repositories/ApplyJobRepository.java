package br.com.rocketseat.gestao_vagas.modules.candidate.repositories;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

    List<ApplyJobEntity> findByJobId(UUID id);
    List<ApplyJobEntity> findByCandidateId(UUID id);
}
