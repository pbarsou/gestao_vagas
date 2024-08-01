package br.com.rocketseat.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.ProfileCompanyUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    ProfileCompanyUseCase profileCompanyUseCase;

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var idCompany = request.getAttribute("company_id");

        try {
            var company = this.profileCompanyUseCase.execute(UUID.fromString(idCompany.toString()));
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
