package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.gestao_vagas.util.CandidateCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class) // jUnit com Spring
class CreateCandidateUseCaseTest {

    @InjectMocks
    private CreateCandidateUseCase createCandidateUseCaseMock;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @BeforeEach
    void setUp() {

        List<CandidateEntity> candidates = new ArrayList<>(List.of(CandidateCreator.createValidCandidate()));

        BDDMockito.when(candidateRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CandidateCreator.createValidCandidate()));

        BDDMockito.when(candidateRepositoryMock.findByUsernameOrEmail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CandidateCreator.createValidCandidate()));

        BDDMockito.when(candidateRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(CandidateCreator.createValidCandidate()));
    }

    @Test
    @DisplayName("execute should return Candidate when Sucessfull")
    void execute_ShouldReturnCandidate_WhenSucessful() {

        String expectedUsername = CandidateCreator.createValidCandidate().getUsername();
        String expectedEmail = CandidateCreator.createValidCandidate().getEmail();

        Optional<CandidateEntity> candidate = this.candidateRepositoryMock.findByUsernameOrEmail(expectedUsername, expectedEmail);

        Assertions.assertThat(candidate).isNotNull();
        Assertions.assertThat(candidate.get().getUsername()).isEqualTo(expectedUsername);
        Assertions.assertThat(candidate.get().getEmail()).isEqualTo(expectedEmail);
    }
}