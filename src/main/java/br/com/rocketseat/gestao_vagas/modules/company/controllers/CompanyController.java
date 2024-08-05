package br.com.rocketseat.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.DeleteCompanyUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.ProfileCompanyUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.UpdateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Informações da empresa")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;
    @Autowired
    UpdateCompanyUseCase updateCompanyUseCase;
    @Autowired
    DeleteCompanyUseCase deleteCompanyUseCase;
    @Autowired
    ProfileCompanyUseCase profileCompanyUseCase;

    @PostMapping("/")
    @Operation(summary = "Criação de uma empresa", description = "Essa função é responsável por criar uma empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CompanyEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists.")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "Perfil da empreasa", description = "Essa função é responsável por buscar as informações do perfil da empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCompanyResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {

        var companyId = request.getAttribute("company_id");

        try {
            var company = this.profileCompanyUseCase.execute(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtem dados de uma empreasa", description = "Essa função é responsável por buscar as informações de uma empresa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCompanyResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCompany(@PathVariable UUID id, HttpServletRequest request) {
        try {
            var company = this.profileCompanyUseCase.execute(UUID.fromString(id.toString()));
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/")
    @Operation(summary = "Atualização de cadastro da empresa", description = "Essa função é responsável por atualizar os dados da empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCompanyResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> put(@Valid @RequestBody CompanyEntity companyEntity, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        // Ensuring that only the user can change their information
        companyEntity.setId(UUID.fromString(companyId.toString()));

        try {
            var result = this.updateCompanyUseCase.execute(companyEntity, UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualização de cadastro de uma empresa", description = "Essa função é responsável por atualizar os dados de uma empresa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCompanyResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> putAdmin(@Valid @RequestBody CompanyEntity companyEntity, @PathVariable UUID id, HttpServletRequest request) {
        try {
            var result = this.updateCompanyUseCase.execute(companyEntity, id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remoção de uma empreasa", description = "Essa função é responsável por deletar uma empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Company not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> delete(@PathVariable UUID id, HttpServletRequest request) {
        try {
            this.deleteCompanyUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
