package br.com.rocketseat.gestao_vagas.modules.candidate;

import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data //cria todos os Getters e Setters
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "O campo [name] não pode ser vazio")
    @Schema(example = "Eden", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do candidato")
    private String name;

    @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não deve conter espaços.")
    @Schema(example = "eden", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do candidato")
    private String username;

    @Email(message = "O campo [email] deve contem um e-mail válido.")
    @Schema(example = "eden@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do candidato")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter entre (10) e (100) caracteres.")
    @Schema(example = "eden@123", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;

    @Schema(example = "Desenvolvedor Java", description = "Breve descrição do candidato")
    private String description;

    @Schema(example = "", description = "Currículo do candidato")
    private String curriculum;

    @JsonIgnore
    @ManyToMany(mappedBy = "candidates", cascade = CascadeType.PERSIST)
    private List<JobEntity> jobApplications = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public List<UUID> getJobApplicationsId() {
        return this.getJobApplications().stream().map(JobEntity::getId).toList();
    }
}
