package com.trainingmanagernew.BodyModule.Dto.Height;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public class HeightPostDto {
    @NotNull(message = "O ID do clientne não pode ser nulo")
    private UUID bodyOwnerId;

    @NotNull(message = "A data não pode ser nula.")
    @PastOrPresent(message = "Insira uma data válida.")
    private LocalDate date;

    @NotNull
    @Min(value = 60, message = "Insira uma altura válida.")
    @Max(value = 240, message = "Insira uma altura válida.")
    private float height;

    private UUID optionalHeightEntityId;

    public UUID getBodyOwnerId() {
        return bodyOwnerId;
    }

    public void setBodyOwnerId(UUID bodyOwnerId) {
        this.bodyOwnerId = bodyOwnerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UUID getOptionalHeightEntityId() {
        return optionalHeightEntityId;
    }

    public void setOptionalHeightEntityId(UUID optionalHeightEntityId) {
        this.optionalHeightEntityId = optionalHeightEntityId;
    }
}
