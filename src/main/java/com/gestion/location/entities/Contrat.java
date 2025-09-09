package com.gestion.location.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contrat")
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unite_id")
    private Unite unite;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Utilisateur locataire;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String etat; // En cours, Terminé, etc.

    public Contrat() {}

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }

    public Utilisateur getLocataire() { return locataire; }
    public void setLocataire(Utilisateur locataire) { this.locataire = locataire; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}
