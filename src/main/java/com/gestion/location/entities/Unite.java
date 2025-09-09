package com.gestion.location.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "unite")
public class Unite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private Integer pieces;
    private Double superficie;
    private Double loyer;
    private String etat; // Disponible, Louee, En attente, etc.

    @ManyToOne
    @JoinColumn(name = "immeuble_id")
    private Immeuble immeuble;

    public Unite() {}

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getPieces() { return pieces; }
    public void setPieces(Integer pieces) { this.pieces = pieces; }

    public Double getSuperficie() { return superficie; }
    public void setSuperficie(Double superficie) { this.superficie = superficie; }

    public Double getLoyer() { return loyer; }
    public void setLoyer(Double loyer) { this.loyer = loyer; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public Immeuble getImmeuble() { return immeuble; }
    public void setImmeuble(Immeuble immeuble) { this.immeuble = immeuble; }
}
