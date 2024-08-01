package br.com.rocketseat.gestao_vagas.modules.candidate.controllers;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.DeleteCandidateUserCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.UpdateCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;
    @Autowired
    private UpdateCandidateUseCase updateCandidateUseCase;
    @Autowired
    private DeleteCandidateUserCase deleteCandidateUserCase;
    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<Object> put(@Valid @RequestBody CandidateEntity candidateEntity, HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        candidateEntity.setId(UUID.fromString(idCandidate.toString()));

        try {
            var result = this.updateCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
        public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            this.deleteCandidateUserCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}