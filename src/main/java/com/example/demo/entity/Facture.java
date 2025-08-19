package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFacture;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate date = LocalDate.now();

    // Relation ManyToOne avec Client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    @JsonIgnore
    private Client client;

    // Relation OneToMany avec LigneFacture
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LigneFacture> lignes;


    @Column
    @Builder.Default
    private Float totalHT = 0f;

    @Column
    private float totalTVA = 0f;

    @Column
    @Builder.Default
    private Float totalTTC = 0f;

    /**
     * Constructeur pour créer une facture avec client et date
     */
    public Facture(Client client, LocalDate date) {
        this.client = client;
        this.date = date;
    }
    public void calculateTotalHT() {
        totalHT = 0f;
        totalTVA = 0f;
        totalTTC = 0f;

        if (lignes != null) {
            for (LigneFacture ligne : lignes) {
                float ligneHT = ligne.getPrixUnitaireHT() * ligne.getQuantite();
                this.totalHT += ligneHT;
                this.totalTVA += ligneHT * ligne.getTauxTVA();
            }
            this.totalTTC = this.totalHT + this.totalTVA;
        }
    }

}