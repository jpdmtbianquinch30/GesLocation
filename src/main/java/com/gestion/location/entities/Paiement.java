package com.gestion.location.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    private String mode; // ex: "espece", "carte"

    @Column(name = "etat")
    private String etat; // "valide", "en retard"

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    // ✅ Relation avec le locataire
    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Utilisateur locataire;

    // ✅ Relation avec l'unité
    @ManyToOne
    @JoinColumn(name = "unite_id")
    private Unite unite;

    // --- Getters et setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public Utilisateur getLocataire() { return locataire; }
    public void setLocataire(Utilisateur locataire) { this.locataire = locataire; }

    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }
}
