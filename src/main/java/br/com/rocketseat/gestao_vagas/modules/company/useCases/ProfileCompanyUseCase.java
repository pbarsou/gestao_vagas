package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public ProfileCompanyResponseDTO execute(UUID companyId) {

        var company = this.companyRepository.findById(companyId).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        var companyResponseDTO = ProfileCompanyResponseDTO.builder()
                .name(company.getName())
                .username(company.getUsername())
                .website(company.getWebsite())
                .email(company.getEmail())
                .description(company.getDescription())
                .build();

        return companyResponseDTO;
    }
}
