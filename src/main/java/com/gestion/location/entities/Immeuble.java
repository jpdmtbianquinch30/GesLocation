package com.gestion.location.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "immeuble")
public class Immeuble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String adresse;
    private String description;

    @OneToMany(mappedBy = "immeuble", cascade = CascadeType.ALL)
    private List<Unite> unites;

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Unite> getUnites() { return unites; }
    public void setUnites(List<Unite> unites) { this.unites = unites; }
}
