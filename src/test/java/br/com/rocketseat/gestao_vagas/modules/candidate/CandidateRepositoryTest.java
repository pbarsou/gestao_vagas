package br.com.rocketseat.gestao_vagas.modules.candidate;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.gestao_vagas.util.CandidateCreator;
import jakarta.validation.*;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@DisplayName("Tets for Candidate Repository")
@Log4j2
class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Save persists candidate when Sucessful")
    public void save_PersistCandidate_WhenSucessful() {

        CandidateEntity candidateToBeSaved = CandidateCreator.createCandidateToBeSaved();
        CandidateEntity candidateSaved = this.candidateRepository.save(candidateToBeSaved);

        Assertions.assertThat(candidateSaved).isNotNull();
        Assertions.assertThat(candidateSaved.getId()).isNotNull();
        Assertions.assertThat(candidateSaved.getName()).isEqualTo(candidateToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates candidate when Sucessful")
    public void save_UpdatesCandidate_WhenSucessful() {

        CandidateEntity candidateTobeSaved = CandidateCreator.createCandidateToBeSaved();
        CandidateEntity candidateSaved = this.candidateRepository.save(candidateTobeSaved);

        candidateSaved.setName("Mashiro Moritaka");

        CandidateEntity candidateUpdated = this.candidateRepository.save(candidateSaved);

        log.info(candidateUpdated.getName());
        Assertions.assertThat(candidateUpdated).isNotNull();
        Assertions.assertThat(candidateUpdated.getId()).isNotNull();
        Assertions.assertThat(candidateUpdated.getName()).isEqualTo(candidateSaved.getName());
    }

    @Test
    @DisplayName("Delete removes candidate when Sucessful")
    public void delete_RemovesCandidate_WhenSucessful() {

        CandidateEntity candidateToBeSeved = CandidateCreator.createCandidateToBeSaved();
        CandidateEntity candidateSaved = this.candidateRepository.save(candidateToBeSeved);

        this.candidateRepository.delete(candidateSaved);

        Optional<CandidateEntity> candidateOptional = this.candidateRepository.findById(candidateSaved.getId());

        Assertions.assertThat(candidateOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of candidate when Sucessful")
    public void findByName_ReturnsCandidate_WhenSucessful() {

        CandidateEntity candidateToBeSeved = CandidateCreator.createCandidateToBeSaved();
        CandidateEntity candidateSaved = this.candidateRepository.save(candidateToBeSeved);

        String name = candidateSaved.getName();

        List<CandidateEntity> candidates = this.candidateRepository.findByName(name);

        Assertions.assertThat(candidates)
                .isNotEmpty()
                .contains(candidateSaved);
    }

    @Test
    @DisplayName("findByName returns empty list when no candidate is not found")
    public void findByName_ReturnsEmptyList_WhenCandidateIsNotFound() {

        List<CandidateEntity> candidates = this.candidateRepository.findByName("totoro");

        Assertions.assertThat(candidates).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintValidationException when name is empty")
    public void save_ThrowConstraintValidationException_WhenNameIsEmpty() {

        CandidateEntity candidate = new CandidateEntity();
//
//        Assertions.assertThatThrownBy(() -> this.candidateRepository.save(candidate))
//                .isInstanceOf(ConstraintViolationException.class);

        Set<ConstraintViolation<CandidateEntity>> violations = validator.validate(candidate);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("O campo [name] não pode ser vazio");
    }

    @Test
    @DisplayName("User should not validate when Username contains space")
    public void user_ShouldNotValidate_WhenUsernameContainsSpace() {
        CandidateEntity candidate = CandidateEntity.builder()
                .name("Eden")
                .username("eden project")
                .email("eden@gmail.com")
                .password("eden@12345")
                .description("Desenvolvedor Júnior.")
                .build();

        Set<ConstraintViolation<CandidateEntity>> violations = validator.validate(candidate);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("O campo [username] não deve conter espaços.");
    }

    @Test
    @DisplayName("User should not validate when email is not valid")
    public void user_ShouldNotValidate_WhenEmailIsNotValid() {
        CandidateEntity candidate = CandidateEntity.builder()
                .name("Eden")
                .username("edenproject")
                .email("edenproject")
                .password("eden@12345")
                .description("Desenvolvedor Júnior.")
                .build();

        Set<ConstraintViolation<CandidateEntity>> violations = validator.validate(candidate);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("O campo [email] deve contem um e-mail válido.");
    }

    @Test
    @DisplayName("User should not validate when password is not valid")
    public void user_ShouldNotValidate_WhenPasswordIsNotValid() {
        CandidateEntity candidate = CandidateEntity.builder()
                .username("eden")
                .name("Eden")
                .email("eden@gmail.com")
                .password("eden@")
                .description("Desenvolvedor Júnior.")
                .build();

        Set<ConstraintViolation<CandidateEntity>> violations = validator.validate(candidate);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha deve conter entre (10) e (100) caracteres.");
    }
}