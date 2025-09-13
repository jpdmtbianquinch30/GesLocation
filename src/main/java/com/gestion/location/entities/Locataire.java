package com.gestion.location.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "locataires")
@PrimaryKeyJoinColumn(name = "utilisateur_id") // FK vers Utilisateur
public class Locataire extends Utilisateur {

    @Column(name = "telephone", length = 20)
    private String numeroTelephone;

    @Column(name = "profession")
    private String profession;

    @OneToMany(mappedBy = "locataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrat> contrats = new ArrayList<>();

    @OneToMany(mappedBy = "locataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements = new ArrayList<>();

    // Constructeurs
    public Locataire() {
        super();
        setRole("LOCATAIRE");
    }

    public Locataire(String nom, String prenom, String email, String motDePasse, String numeroTelephone) {
        super(nom, prenom, email, motDePasse, "LOCATAIRE");
        this.numeroTelephone = numeroTelephone;
    }

    // Getters et Setters
    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public List<Contrat> getContrats() { return contrats; }
    public void setContrats(List<Contrat> contrats) { this.contrats = contrats; }

    public List<Paiement> getPaiements() { return paiements; }
    public void setPaiements(List<Paiement> paiements) { this.paiements = paiements; }

    // Méthodes utilitaires
    public void addContrat(Contrat contrat) {
        if (contrat != null) {
            contrats.add(contrat);
            contrat.setLocataire(this);
        }
    }

    public void removeContrat(Contrat contrat) {
        if (contrat != null) {
            contrats.remove(contrat);
            contrat.setLocataire(null);
        }
    }

    public void addPaiement(Paiement paiement) {
        if (paiement != null) {
            paiements.add(paiement);
            paiement.setLocataire(this);
        }
    }

    public void removePaiement(Paiement paiement) {
        if (paiement != null) {
            paiements.remove(paiement);
            paiement.setLocataire(null);
        }
    }
}
