package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;


@Entity
@Table(name = "lignes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLigne;


    @Column(nullable = false)
    private String description;



    @Column(nullable = false)
    private Integer quantite;



    @Column
    private Float prixUnitaireHT;


    @Column
    private Float tauxTVA;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facture", nullable = false)
    @JsonIgnore
    private Facture facture;


    public Integer getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Integer idLigne) {
        this.idLigne = idLigne;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Float getPrixUnitaireHT() {
        return prixUnitaireHT;
    }

    public void setPrixUnitaireHT(Float prixUnitaireHT) {
        this.prixUnitaireHT = prixUnitaireHT;
    }

    public Float getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(Float tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
