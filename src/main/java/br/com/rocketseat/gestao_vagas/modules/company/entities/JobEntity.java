package br.com.rocketseat.gestao_vagas.modules.company.entities;

import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "job")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;

    @NotBlank(message = "Esse campo é obrigatório.")
    private String level;
    private String benefits;

    @ManyToOne()
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity companyEntity;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "jobs_candidates",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    )
    private List<CandidateEntity> candidates = new ArrayList<>();

    @Column(name = "company_id", nullable = false)
    private UUID company_id;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public List<UUID> getCandidatesId() {
        if(this.candidates != null) {
            return this.getCandidates().stream().map(CandidateEntity::getId).toList();
        } else {
            return null;
        }
    }
}
