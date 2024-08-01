package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        var password = passwordEncoder.encode(companyEntity.getPassword());

        var company = this.companyRepository.findById(companyEntity.getId()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Company not found.");
        });

        if(!companyEntity.getUsername().equals(company.getUsername())) {
            this.companyRepository.findByUsername(companyEntity.getUsername()).ifPresent((user) -> {
                throw new UserFoundException();
            });
        }

        if(!companyEntity.getEmail().equals(company.getEmail())) {
            this.companyRepository.findByEmail(companyEntity.getEmail()).ifPresent((user) -> {
                throw new UserFoundException();
            });
        }

        company.setName(companyEntity.getName());
        company.setUsername(companyEntity.getUsername());
        company.setEmail(companyEntity.getEmail());
        company.setPassword(password);
        company.setWebsite(companyEntity.getWebsite());
        company.setDescription(companyEntity.getDescription());

        return this.companyRepository.save(company);
    }
}
