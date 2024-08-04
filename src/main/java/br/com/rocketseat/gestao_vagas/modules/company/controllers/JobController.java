package br.com.rocketseat.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.gestao_vagas.modules.company.dto.CreateUpdateJobDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.DeleteJobUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.GetCompanyJobsUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.useCases.UpdateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
@PreAuthorize("hasRole('COMPANY'")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;
    @Autowired
    private GetCompanyJobsUseCase getCompanyJobsUseCase;
    @Autowired
    private UpdateJobUseCase updateJobUseCase;
    @Autowired
    private DeleteJobUseCase deleteJobUseCase;

    @PostMapping("/")
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

    @GetMapping("/")
    public ResponseEntity<Object> getCompanyJobs(HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var result = this.getCompanyJobsUseCase.execute(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getCompanyJobs(@PathVariable UUID id, HttpServletRequest request) {
        try {
            var result = this.getCompanyJobsUseCase.execute(id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Object> delete(@PathVariable UUID jobId, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            this.deleteJobUseCase.execute(jobId, UUID.fromString(companyId.toString()));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
