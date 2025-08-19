package com.example.demo.entity;

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
    private Client client;

    // Relation OneToMany avec LigneFacture
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LigneFacture> lignes;

    // Champs calculés automatiquement
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


}