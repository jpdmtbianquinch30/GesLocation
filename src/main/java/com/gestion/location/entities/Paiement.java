package com.gestion.location.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    private String mode; // carte, cash, mobile money

    private String statut; // en_attente, valide, echec

    private LocalDateTime datePaiement = LocalDateTime.now();

    // --- Constructeurs ---
    public Paiement() {}
    public Paiement(Double montant, String mode, String statut) {
        this.montant = montant;
        this.mode = mode;
        this.statut = statut;
    }

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
}
