package com.fitnessapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clienti")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Numele este obligatoriu.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Numele trebuie să conțină doar litere.")
    private String nume;

    @NotBlank(message = "Prenumele este obligatoriu.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Prenumele trebuie să conțină doar litere.")
    private String prenume;

    @NotBlank(message = "Email-ul este obligatoriu.")
    @Email(message = "Email-ul trebuie să fie valid.")
    private String email;

    @NotBlank(message = "Telefonul este obligatoriu.")
    @Pattern(regexp = "^\\d{10}$", message = "Telefonul trebuie să conțină exact 10 cifre.")
    private String telefon;

    // Constructor implicit (fără parametri)
    public Client() {
    }

    // Constructor cu parametri (pentru testele tale)
    public Client(Long id, String nume, String prenume, String email, String telefon) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
