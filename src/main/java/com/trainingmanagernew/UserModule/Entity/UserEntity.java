package com.trainingmanagernew.UserModule.Entity;

import com.trainingmanagernew.SecurityModule.Entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID id;

    @NotNull(message = "O nome de usuário não pode ser nulo.")
    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "A senha não pode ser nula.")
    @NotBlank(message = "A senha não pode estar vazia.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail não pode estar vazio")
    @Email(message = "O e-mail deve ser válido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "O número não pode ser nulo.")
    @NotBlank(message = "O número não pode estar vazio")
    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 15, message = "Inclua o DDD / O número deve ter entre 10 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O número deve conter apenas dígitos")
    private String number;

    private Long trainerOwnedId;

    private Long scheduleOwnedId;

    private Long customerOwnedId;

    private Long paymentOwnedId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
