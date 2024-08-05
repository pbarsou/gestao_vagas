package br.com.rocketseat.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "O campo [name] não pode ser vazio")
    @Schema(example = "Ambev", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    private String name;

    @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não deve conter espaços.")
    @Schema(example = "ambev", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username da empresa")
    private String username;

    @Email(message = "O campo [email] deve contem um e-mail válido.")
    @Schema(example = "ambev@ab-inbev.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email da empresa")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter entre (10) e (100) caracteres.")
    @Schema(example = "ambev@123", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha da empresa")
    private String password;

    @Schema(example = "https://www.ambev.com.br", description = "Site da empresa")
    private String website;

    @Schema(example = "Maior cervejaria da América Latina.", description = "Breve descrição da empresa")
    private String description;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
