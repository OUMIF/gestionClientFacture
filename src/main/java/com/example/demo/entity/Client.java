package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;


    @Column(nullable = false)
    private String nom;



    @Column(nullable = false, unique = true)
    private String email;



    @Column(nullable = false, unique = true, length = 14)
    private String siret;

    @Column(nullable = false)
    @Builder.Default // Ici il faut comprendre pourquoi ceci
    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Facture> factures;

    /*
      Constructeur pour créer un client avec les données essentielles sinon je devrait passer tout les champs
     */
    public Client(String nom, String email, String siret) {
        this.nom = nom;
        this.email = email;
        this.siret = siret;
        this.dateCreation = LocalDateTime.now();
    }
}