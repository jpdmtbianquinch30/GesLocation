package com.gestion.location.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "immeuble")
public class Immeuble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String description;
    private String equipements; // <-- nouveau champ

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Utilisateur proprietaire;

    @OneToMany(mappedBy = "immeuble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unite> unites;

    public Immeuble() {}

    public Immeuble(String nom, String adresse, String description, String equipements, Utilisateur proprietaire) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.equipements = equipements; // <-- initialisation
        this.proprietaire = proprietaire;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEquipements() { return equipements; }
    public void setEquipements(String equipements) { this.equipements = equipements; }

    public Utilisateur getProprietaire() { return proprietaire; }
    public void setProprietaire(Utilisateur proprietaire) { this.proprietaire = proprietaire; }

    public List<Unite> getUnites() { return unites; }
    public void setUnites(List<Unite> unites) { this.unites = unites; }
}
