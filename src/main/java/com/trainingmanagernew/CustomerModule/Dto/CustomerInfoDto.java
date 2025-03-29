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
    private String name;

    @NotNull
    @Past
    @EnforceBornAfterCertainPeriod
    private LocalDate birthDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
