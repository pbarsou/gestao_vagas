package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetCompanyJobsUseCase {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(UUID companyId) {

        var company = this.companyRepository.findById(companyId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Company not found.");
        });

        return this.jobRepository.findByCompanyId(companyId);
        //return this.jobRepository.findByCompanyId(companyId).stream().map(JobEntity::getId).toList();
    }
}
