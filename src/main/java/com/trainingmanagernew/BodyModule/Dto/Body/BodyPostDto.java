package com.trainingmanagernew.BodyModule.Dto.Body;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public class BodyPostDto {
    @NotNull
    private UUID bodyOwnerId;

    @NotNull(message = "A data não pode ser nula.")
    @PastOrPresent(message = "Insira uma data válida.")
    private LocalDate date;

    @NotNull(message = "O peso não pode ser nulo.")
    @Min(value = 28, message = "Insira um peso válido.")
    @Max(value = 300, message = "Insira um peso válido.")
    private float weight;

    @Min(value = 0, message = "Insira um percentual de gordura válido.")
    @Max(value = 85, message = "Insira um percentual de gordura válido.")
    private float bodyFat;

    private UUID optionalBodyEntityId;

    public UUID getOptionalBodyEntityId(){
        return optionalBodyEntityId;
    }

    public void setOptionalBodyEntityId(UUID optionalBodyEntityId){
        this.optionalBodyEntityId = optionalBodyEntityId;
    }

    public UUID getBodyOwnerId(){
        return bodyOwnerId;
    }

    public void setBodyOwnerId(UUID bodyOwnerId){
        this.bodyOwnerId = bodyOwnerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }
}
