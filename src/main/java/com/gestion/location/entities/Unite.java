package com.gestion.location.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "unite")
public class Unite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;
    private Integer pieces;
    private Double superficie;
    private Double loyer;

    @ManyToOne
    @JoinColumn(name = "immeuble_id")
    private Immeuble immeuble;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getPieces() { return pieces; }
    public void setPieces(Integer pieces) { this.pieces = pieces; }

    public Double getSuperficie() { return superficie; }
    public void setSuperficie(Double superficie) { this.superficie = superficie; }

    public Double getLoyer() { return loyer; }
    public void setLoyer(Double loyer) { this.loyer = loyer; }

    public Immeuble getImmeuble() { return immeuble; }
    public void setImmeuble(Immeuble immeuble) { this.immeuble = immeuble; }
}
