package com.trainingmanagernew.TrainerModule.Dto;

import com.trainingmanagernew.UserModule.Dto.validation.EnforceBornAfterCertainPeriod;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public class TrainerDto {
    @NotNull(message = "O nome não pode ser nulo.")
    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres.")
    private String name;

    @Size(max = 200, message = "O endereço não pode ter mais de 200 caracteres.")
    private String address;

    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato de CPF inválido. Deve estar no formato XXX.XXX.XXX-XX.")
    private String cpf;

    @Past
    @NotNull
    @EnforceBornAfterCertainPeriod
    private LocalDate birth;

    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
