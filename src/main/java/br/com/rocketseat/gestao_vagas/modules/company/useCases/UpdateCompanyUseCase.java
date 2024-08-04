package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileCompanyResponseDTO execute(CompanyEntity companyEntity, UUID companyId) {

        var password = passwordEncoder.encode(companyEntity.getPassword());

        var company = this.companyRepository.findById(companyId).orElseThrow(() -> {
            throw new UsernameNotFoundException("Company not found.");
        });

        // User cannot update their username to an existing username
        if(!companyEntity.getUsername().equals(company.getUsername())) {
            this.companyRepository.findByUsername(companyEntity.getUsername()).ifPresent((user) -> {
                throw new UserFoundException();
            });
        }

        // User cannot their email to an existing email
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

        var companyResponseDTO = ProfileCompanyResponseDTO.builder()
                .name(company.getName())
                .username(company.getUsername())
                .email(company.getEmail())
                .website(company.getWebsite())
                .description(company.getDescription())
                .build();

        this.companyRepository.save(company);

        return companyResponseDTO;
    }
}
