package com.gestion.location.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "montant", nullable = false)
    private double montant;

    @Column(name = "mois_couvert", nullable = false)
    private String moisCouvert; // Format: "YYYY-MM"

    @Column(name = "methode_paiement", nullable = true)
    private String methodePaiement; // CARTE, VIREMENT, ESPECES, CHEQUE

    @Column(name = "statut_paiement", nullable = true)
    private String statutPaiement; // EN_ATTENTE, VALIDE, REFUSE

    @Column(name = "date_creation", nullable = true)
    private LocalDate dateCreation;

    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "contrat_id", nullable = false)
    private Contrat contrat;

    // Constructeurs
    public Paiement() {
        this.dateCreation = LocalDate.now();
        this.statutPaiement = "EN_ATTENTE";
    }

    public Paiement(LocalDate datePaiement, double montant, String moisCouvert, String methodePaiement) {
        this();
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.moisCouvert = moisCouvert;
        this.methodePaiement = methodePaiement;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getMoisCouvert() { return moisCouvert; }
    public void setMoisCouvert(String moisCouvert) { this.moisCouvert = moisCouvert; }

    public String getMethodePaiement() { return methodePaiement; }
    public void setMethodePaiement(String methodePaiement) { this.methodePaiement = methodePaiement; }

    public String getStatutPaiement() { return statutPaiement; }
    public void setStatutPaiement(String statutPaiement) { this.statutPaiement = statutPaiement; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public Locataire getLocataire() { return locataire; }
    public void setLocataire(Locataire locataire) { this.locataire = locataire; }

    public Contrat getContrat() { return contrat; }
    public void setContrat(Contrat contrat) { this.contrat = contrat; }

    // Méthode pour vérifier si le paiement est en retard
    public boolean isEnRetard() {
        LocalDate aujourdhui = LocalDate.now();
        return "EN_ATTENTE".equals(statutPaiement) &&
                aujourdhui.isAfter(datePaiement.plusDays(5)); // 5 jours de tolérance
    }
}
