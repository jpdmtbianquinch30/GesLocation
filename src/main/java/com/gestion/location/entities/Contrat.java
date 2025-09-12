package com.gestion.location.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "loyer_mensuel", nullable = false)
    private double loyerMensuel;

    @Column(name = "caution", nullable = false)
    private double caution;

    @Column(name = "etat_contrat")
    private String etatContrat; // EN_ATTENTE, ACTIF, RESILIE, TERMINE

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Locataire locataire;

    @OneToOne
    @JoinColumn(name = "unite_id", nullable = false)
    private Unite unite;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)
    private List<Paiement> paiements = new ArrayList<>();

    // Constructeurs
    public Contrat() {
        this.dateCreation = LocalDate.now();
    }

    public Contrat(LocalDate dateDebut, LocalDate dateFin, double loyerMensuel, double caution, String etatContrat) {
        this();
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.loyerMensuel = loyerMensuel;
        this.caution = caution;
        this.etatContrat = etatContrat;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public double getLoyerMensuel() {
        return loyerMensuel;
    }

    public void setLoyerMensuel(double loyerMensuel) {
        this.loyerMensuel = loyerMensuel;
    }

    public double getCaution() {
        return caution;
    }

    public void setCaution(double caution) {
        this.caution = caution;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Locataire getLocataire() {
        return locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    // Méthodes utilitaires
    public void addPaiement(Paiement paiement) {
        paiements.add(paiement);
        paiement.setContrat(this);
    }

    public void removePaiement(Paiement paiement) {
        paiements.remove(paiement);
        paiement.setContrat(null);
    }

    // Méthode pour vérifier si le contrat est actif
    public boolean isActif() {
        LocalDate aujourdhui = LocalDate.now();
        return "ACTIF".equals(etatContrat) &&
                !aujourdhui.isBefore(dateDebut) &&
                !aujourdhui.isAfter(dateFin);
    }
}