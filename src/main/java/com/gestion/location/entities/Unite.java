package com.gestion.location.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "unite")  // Nom exact de la table
public class Unite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_unite", nullable = false)
    private String numeroUnite;

    @Column(name = "nombre_pieces", nullable = false)
    private int nombrePieces;

    @Column(nullable = false)
    private double superficie;

    @Column(name = "loyer_mensuel", nullable = false)
    private double loyerMensuel;

    @Column(name = "est_disponible", nullable = false)
    private boolean estDisponible = true;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "immeuble_id", nullable = false)
    private Immeuble immeuble;

    @OneToOne
    @JoinColumn(name = "contrat_id")  // Clé étrangère dans la table contrat si elle existe
    private Contrat contrat;

    // Constructeurs
    public Unite() {}

    public Unite(String numeroUnite, int nombrePieces, double superficie, double loyerMensuel, String description, Immeuble immeuble) {
        this.numeroUnite = numeroUnite;
        this.nombrePieces = nombrePieces;
        this.superficie = superficie;
        this.loyerMensuel = loyerMensuel;
        this.description = description;
        this.immeuble = immeuble;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroUnite() { return numeroUnite; }
    public void setNumeroUnite(String numeroUnite) { this.numeroUnite = numeroUnite; }

    public int getNombrePieces() { return nombrePieces; }
    public void setNombrePieces(int nombrePieces) { this.nombrePieces = nombrePieces; }

    public double getSuperficie() { return superficie; }
    public void setSuperficie(double superficie) { this.superficie = superficie; }

    public double getLoyerMensuel() { return loyerMensuel; }
    public void setLoyerMensuel(double loyerMensuel) { this.loyerMensuel = loyerMensuel; }

    public boolean isEstDisponible() { return estDisponible; }
    public void setEstDisponible(boolean estDisponible) { this.estDisponible = estDisponible; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Immeuble getImmeuble() { return immeuble; }
    public void setImmeuble(Immeuble immeuble) { this.immeuble = immeuble; }

    public Contrat getContrat() { return contrat; }
    public void setContrat(Contrat contrat) { this.contrat = contrat; }
}
