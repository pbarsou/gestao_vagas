package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.JobNotFoundException;
import br.com.rocketseat.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.rocketseat.gestao_vagas.util.ApplyJobCreator;
import br.com.rocketseat.gestao_vagas.util.CandidateCreator;
import br.com.rocketseat.gestao_vagas.util.JobCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class ApplyForJobUseCaseTest {

    @InjectMocks
    private ApplyForJobUseCase applyForJobUseCaseMock;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Mock
    private JobRepository jobRepositoryMock;

    @Mock
    private ApplyJobRepository applyJobRepositoryMock;


    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Should not to be able apply for job when candidate not found")
    public void ShouldNotToBeAbleToApplyJob_WhenCandidateNotFound() {
        try {
            this.applyForJobUseCaseMock.execute(null, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job when job not found")
    public void ShouldNotToBeAbleToApplyJob_WhenJobNotFound() {

        when(candidateRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(CandidateCreator.createValidCandidate()));

        var idCandidate = CandidateCreator.createValidCandidate().getId();

        try {
            this.applyForJobUseCaseMock.execute(idCandidate, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job")
    public void ShouldBeAbleToCreateANewApplyJob() {

        UUID candidateId = CandidateCreator.createValidCandidate().getId();
        UUID jobId = JobCreator.createValidJob().getId();

        var applyJob = ApplyJobCreator.createApplyJobToBeSaved();

        var applyJobCreated = ApplyJobCreator.createValidApplyJob();

        when(candidateRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(CandidateCreator.createValidCandidate()));
        when(jobRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(JobCreator.createValidJob()));
        when(applyJobRepositoryMock.save(applyJob)).thenReturn(applyJobCreated);

        var applyJobResponse = applyForJobUseCaseMock.execute(candidateId, jobId);

        assertThat(applyJobResponse.getCandidateId()).isEqualTo(candidateId);

        /*var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().candidateId(candidateId)
                        .jobId(jobId).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepositoryMock.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepositoryMock.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepositoryMock.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyForJobUseCaseMock.execute(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
        Assertions.assertNotNull(result.getId()); */
    }



}
