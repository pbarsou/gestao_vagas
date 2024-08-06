package br.com.rocketseat.gestao_vagas.modules.candidate.controllers;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.JobApplicationResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.*;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;
    @Autowired
    private UpdateCandidateUseCase updateCandidateUseCase;
    @Autowired
    private DeleteCandidateUserCase deleteCandidateUserCase;
    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    @Autowired
    private ApplyForJobUseCase applyForJobUseCase;
    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists.")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Aplicar a uma vaga", description = "Essa função é responsável por aplicar um candidato a uma vaga")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ApplyJobEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found."),
            @ApiResponse(responseCode = "400", description = "Job not found."),
            @ApiResponse(responseCode = "400", description = "Candidate has already applied for this job.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(@RequestBody UUID id, HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");

        try {
            var result = this.applyForJobUseCase.execute(UUID.fromString(candidateId.toString()), id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil de um candidato", description = "Essa função é responsável por buscar as informações do perfil de um candidato específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCandidate(@PathVariable UUID id, HttpServletRequest request) {
        try {
            var candidate = this.profileCandidateUseCase.execute(UUID.fromString(id.toString()));
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis baseadas no filtro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Atualização de cadastro do candidato", description = "Essa função é responsável por atualizar as informações do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found."),
            @ApiResponse(responseCode = "400", description = "User already exists.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> put(@Valid @RequestBody CandidateEntity candidateEntity, HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        candidateEntity.setId(UUID.fromString(idCandidate.toString()));

        try {
            var result = this.updateCandidateUseCase.execute(candidateEntity, candidateEntity.getId());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Atualização de cadastro de um candidato", description = "Essa função é responsável por atualizar as informações de um candidato específico cadastrado na base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found."),
            @ApiResponse(responseCode = "400", description = "User already exists.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> put(@Valid @RequestBody CandidateEntity candidateEntity, @PathVariable UUID id, HttpServletRequest request) {
        try {
            var result = this.updateCandidateUseCase.execute(candidateEntity, id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remoção de um candidato", description = "Essa função é responsável por deletar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Candidate not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> delete(@PathVariable UUID id, HttpServletRequest request) {
        try {
            this.deleteCandidateUserCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
