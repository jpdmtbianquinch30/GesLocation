package com.gestion.location.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietaires")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Proprietaire extends Utilisateur {

    @Column(name = "numero_telephone", length = 20)
    private String numeroTelephone;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Immeuble> immeubles = new ArrayList<>();

    // Constructeurs
    public Proprietaire() {
        super();
        setRole("PROPRIETAIRE"); // assure le rôle
    }

    public Proprietaire(String nom, String prenom, String email, String motDePasse, String numeroTelephone) {
        super(nom, prenom, email, motDePasse, "PROPRIETAIRE");
        this.numeroTelephone = numeroTelephone;
    }

    // Getters et Setters
    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public List<Immeuble> getImmeubles() {
        return immeubles;
    }

    public void setImmeubles(List<Immeuble> immeubles) {
        this.immeubles = immeubles;
    }

    // Méthodes utilitaires
    public void addImmeuble(Immeuble immeuble) {
        immeubles.add(immeuble);
        immeuble.setProprietaire(this);
    }

    public void removeImmeuble(Immeuble immeuble) {
        immeubles.remove(immeuble);
        immeuble.setProprietaire(null);
    }
}
