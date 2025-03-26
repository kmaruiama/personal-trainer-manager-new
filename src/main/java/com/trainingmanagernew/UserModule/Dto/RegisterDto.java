package com.trainingmanagernew.UserModule.Dto;

import java.time.LocalDate;

import com.trainingmanagernew.UserModule.Dto.validation.EnforceBornAfterCertainPeriod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Past;


//na nova implementação, retirei o lombok
public class RegisterDto {

    @NotNull(message = "O nome de usuário não pode ser nulo.")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres.")
    private String username;

    @NotNull(message = "A senha não pode ser nula.")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$",
            message = "A senha deve ter entre 8 e 20 caracteres e conter pelo menos uma letra e um número.")
    private String password;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
