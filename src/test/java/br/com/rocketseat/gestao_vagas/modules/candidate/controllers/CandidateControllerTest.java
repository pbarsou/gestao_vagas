package br.com.rocketseat.gestao_vagas.modules.candidate.controllers;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.rocketseat.gestao_vagas.util.CandidateCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // jUnit com Spring
@ContextConfiguration(classes = {CandidateControllerTest.TestConfig.class})
class CandidateControllerTest {

    @InjectMocks
    private CandidateController candidateController;
    @Mock
    private CreateCandidateUseCase createCandidateUseCaseMock;

    @Mock
    private ProfileCandidateUseCase profileCandidateUseCaseMock;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Configuration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @BeforeEach
    void setUp() {

        CandidateEntity candidateEntityResponse = CandidateCreator.createValidCandidate();
        ProfileCandidateResponseDTO profileCandidateResponseDTO = CandidateCreator.createProfileCandidateResponse();

        candidateEntityResponse.setPassword(passwordEncoder.encode(candidateEntityResponse.getPassword()));

        BDDMockito.when(createCandidateUseCaseMock.execute(ArgumentMatchers.any()))
                .thenReturn(candidateEntityResponse);

        BDDMockito.when(profileCandidateUseCaseMock.execute(ArgumentMatchers.any(UUID.class)))
                .thenReturn(profileCandidateResponseDTO);
    }

    @Test
    @DisplayName("create should return Candidate with password Encrypt when Suscessful")
    void create_ReturnsCandidate_WithPasswordEncrypt_WhenSucessful() {

        CandidateEntity candidate = CandidateCreator.createValidCandidate();
        CandidateEntity candidateSaved = createCandidateUseCaseMock.execute(candidate);

        Assertions.assertThat(candidateSaved).isNotNull();
        Assertions.assertThat(this.passwordEncoder.matches(candidate.getPassword(), candidateSaved.getPassword())).isTrue();
    }

    @Test
    @DisplayName("create should throw UserFoundException when user already exists")
    void create_ShouldThrowUserFoundException_WhenUserAlreadyExists() {

        BDDMockito.when(createCandidateUseCaseMock.execute(ArgumentMatchers.any(CandidateEntity.class)))
                .thenThrow(new UserFoundException());

        CandidateEntity candidate = CandidateCreator.createValidCandidate();

        Assertions.assertThatThrownBy(() -> createCandidateUseCaseMock.execute(candidate))
                .isInstanceOf(UserFoundException.class)
                .hasMessage("User already exists.");
    }

    @Test
    @DisplayName("get should return candidate profile when Sucessful")
    void get_MayReturnCandidateProfile_WhenSucessful() {

        UUID expectedID = CandidateCreator.createProfileCandidateResponse().getId();

        ProfileCandidateResponseDTO profileCandidateResponse = this.profileCandidateUseCaseMock.execute(expectedID);

        Assertions.assertThat(profileCandidateResponse).isNotNull();
        Assertions.assertThat(profileCandidateResponse.getId()).isEqualTo(expectedID);

    }

    @Test
    @DisplayName("get should throw UserNotFoundException when User not exists")
    void get_ShouldThrowUserNotFoundExeption_WhenUserNotExists() {

        BDDMockito.when(profileCandidateUseCaseMock.execute(ArgumentMatchers.any(UUID.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        UUID expectedID = CandidateCreator.createProfileCandidateResponse().getId();

        Assertions.assertThatThrownBy(() -> profileCandidateUseCaseMock.execute(expectedID))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

}