package br.com.rocketseat.gestao_vagas.modules.company.repositories;

import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    @Query("SELECT j FROM job j WHERE j.company_id = :company_id")
    List<JobEntity> findByCompanyId(@Param("company_id") UUID companyId);
}