package br.com.rocketseat.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.gestao_vagas.modules.company.dto.CreateUpdateJobDTO;
import br.com.rocketseat.gestao_vagas.modules.company.dto.JobResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Vagas", description = "Informações das vagas")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;
    @Autowired
    private GetJobUseCase getJobUseCase;
    @Autowired
    private GetCompanyJobsUseCase getCompanyJobsUseCase;
    @Autowired
    private UpdateJobUseCase updateJobUseCase;
    @Autowired
    private DeleteJobUseCase deleteJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Cadastro de vaga", description = "Essa função é responsável por cadastrar as vagas dentro da empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUpdateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
                .company_id(UUID.fromString(companyId.toString()))
                .description(createJobDTO.getDescription())
                .benefits(createJobDTO.getBenefits())
                .level(createJobDTO.getLevel())
                .build();

        try {
            var result = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Obtem as informações de uma vaga cadastrada pela empresa", description = "Essa função é responsável por obter as informações de uma vaga cadastrada pela empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Job not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCompanyJob(@PathVariable UUID id, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var result = this.getJobUseCase.execute(id, UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('COMPANY'")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtem as informações de qualquer vaga presente na base de dados", description = "Essa função é responsável por obter as informações de qualquer vaga.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Job not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getAnyJob(@PathVariable UUID id, HttpServletRequest request) {
        try {
            var result = this.getJobUseCase.executeAdmin(id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Listagem de vagas da empresa", description = "Essa função é responsável por listas as vagas criadas pela empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCurrentCompanyJobs(HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var result = this.getCompanyJobsUseCase.execute(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/")
    @PreAuthorize("hasRole('COMPANY'")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listagem de vagas de uma empresa", description = "Essa função é responsável por listas as vagas criadas por uma empresa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Company not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCompanyJobs(@RequestParam UUID companyId, HttpServletRequest request) {
        try {
            var result = this.getCompanyJobsUseCase.execute(companyId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Atualizar dados da vaga", description = "Essa função é responsável por atualizar os dados da vaga.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = JobResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "You don't have access for alter this job."),
            @ApiResponse(responseCode = "400", description = "Job not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> put(@Valid @RequestBody CreateUpdateJobDTO updateJobDTO, @PathVariable UUID id, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var result = this.updateJobUseCase.execute(updateJobDTO, UUID.fromString(id.toString()), UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Remoção de um vaga", description = "Essa função é responsável por deletar uma vaga.")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Job not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> delete(@PathVariable UUID jobId, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            this.deleteJobUseCase.execute(jobId, UUID.fromString(companyId.toString()));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{jobId}")
    @PreAuthorize("hasRole('COMPANY'")
    @Operation(summary = "Remoção de um vaga", description = "Essa função é responsável por deletar uma vaga.")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Job not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteAnyJob(@PathVariable UUID jobId, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            this.deleteJobUseCase.executeAdmin(jobId, UUID.fromString(companyId.toString()));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
