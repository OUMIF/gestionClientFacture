package com.example.demo.entity;

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
    private Facture facture;


}
