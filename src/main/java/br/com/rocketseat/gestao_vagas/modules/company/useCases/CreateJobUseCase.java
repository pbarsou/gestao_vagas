package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity) {
//        this.jobRepository.findById(jobEntity.getId()).ifPresent(user -> {
//            throw new UserFoundException();
//        });
        return this.jobRepository.save(jobEntity);
    }
}
