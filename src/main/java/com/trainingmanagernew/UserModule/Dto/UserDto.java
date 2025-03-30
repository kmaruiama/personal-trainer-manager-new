package com.trainingmanagernew.UserModule.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.util.UUID;

public class UserDto {

    private UUID id;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail não pode estar vazio")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotNull(message = "O nome de usuário não pode ser nulo.")
    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres.")
    private String username;


    @NotNull(message = "O número não pode ser nulo.")
    @NotBlank(message = "O número não pode estar vazio")
    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 15, message = "Inclua o DDD / O número deve ter entre 10 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O número deve conter apenas dígitos")
    private String number;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
