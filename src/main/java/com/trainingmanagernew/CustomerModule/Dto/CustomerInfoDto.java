package com.trainingmanagernew.CustomerModule.Dto;

import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import com.trainingmanagernew.Shared.Validators.EnforceBornAfterCertainPeriod;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CustomerInfoDto {
    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres.")
    private String nome;

    @NotNull
    @Past
    @EnforceBornAfterCertainPeriod
    private LocalDate dataNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
