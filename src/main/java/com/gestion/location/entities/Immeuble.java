package com.gestion.location.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "immeuble")
public class Immeuble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    private String description;

    @Column(name = "equipements")
    private String equipements; // Liste des équipements séparés par des virgules

    @OneToMany(mappedBy = "immeuble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unite> unites = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "proprietaire_id", referencedColumnName = "utilisateur_id")
    private Proprietaire proprietaire;

    // Constructeurs
    public Immeuble() {
    }

    public Immeuble(String nom, String adresse, String description, String equipements) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.equipements = equipements;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public List<Unite> getUnites() {
        return unites;
    }

    public void setUnites(List<Unite> unites) {
        this.unites = unites;
    }

    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    // Méthodes utilitaires
    public void addUnite(Unite unite) {
        unites.add(unite);
        unite.setImmeuble(this);
    }

    public void removeUnite(Unite unite) {
        unites.remove(unite);
        unite.setImmeuble(null);
    }
}