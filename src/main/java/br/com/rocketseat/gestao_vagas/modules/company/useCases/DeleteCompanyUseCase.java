package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    public void execute(UUID idCompany) {
        var company = this.companyRepository.findById(idCompany).orElseThrow(() -> {
            throw new UsernameNotFoundException("Company not found.");
        });

        this.companyRepository.delete(company);
    }
}
