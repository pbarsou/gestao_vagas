package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompany_id()).orElseThrow(() -> new CompanyNotFoundException());
        return this.jobRepository.save(jobEntity);
    }
}
