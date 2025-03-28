package com.trainingmanagernew.CustomerModule.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

//I've realized that asking for a customer address would be weird in a person to person interaction xD
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String nome;

    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "localOwner_id")
    private CustomerEntityOwner localOwnerId;

    //I won't be adding relationships in the entities between modules to keep things decoupled
    private UUID bodyId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
