package br.com.rocketseat.gestao_vagas.modules.candidate;

import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
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
    private String name;

    @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não deve conter espaços.")
    private String username;

    @Email(message = "O campo [email] deve contem um e-mail válido.")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter entre (10) e (100) caracteres.")
    private String password;

    private String description;
    private String curriculum;

    @ManyToMany(mappedBy = "candidates", cascade = CascadeType.PERSIST)
    private List<JobEntity> jobApplications = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public List<UUID> getJobApplicationsId() {
        return this.getJobApplications().stream().map(JobEntity::getId).toList();
    }
}
