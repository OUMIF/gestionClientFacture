package com.example.demo.controller;

import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.service.FactureService;
import com.example.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST pour la gestion des factures
 */
@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;
    private final ClientService clientService;

    // Taux de TVA autorisés
    private static final List<Float> TAUX_TVA_AUTORISES = Arrays.asList(0.0f, 0.055f, 0.10f, 0.20f);

    public FactureController(FactureService factureService, ClientService clientService) {
        this.factureService = factureService;
        this.clientService = clientService;
    }

    /**
     * Liste de toutes les factures
     */
    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        List<Facture> factures = factureService.getAll();
        return ResponseEntity.ok(factures);
    }

    /**
     * Détail d'une facture par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Integer id) {
        return factureService.getById(id)
                .map(facture -> ResponseEntity.ok(facture))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Création d'une nouvelle facture
     */
    @PostMapping
    public ResponseEntity<?> createFacture(@RequestBody Facture facture) {
        // Validation : facture doit avoir au moins une ligne
        if (facture.getLignes() == null || facture.getLignes().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Une facture doit obligatoirement avoir au moins une ligne"));
        }

        // Validation : client doit exister
        if (facture.getClient() == null || facture.getClient().getIdClient() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Une facture doit être associée à un client"));
        }

        if (!clientService.getById(facture.getClient().getIdClient()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Le client spécifié n'existe pas"));
        }

        // Validation des lignes
        for (LigneFacture ligne : facture.getLignes()) {
            if (!isLigneValide(ligne)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Tous les champs des lignes sont obligatoires"));
            }

            if (!TAUX_TVA_AUTORISES.contains(ligne.getTauxTVA())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Taux de TVA autorisés : 0%, 5.5%, 10% ou 20%"));
            }

            // Associer la ligne à la facture
            ligne.setFacture(facture);
        }

        Facture savedFacture = factureService.save(facture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFacture);
    }

    /**
     * Export JSON d'une facture complète
     */
    @GetMapping("/{id}/export")
    public ResponseEntity<?> exportFacture(@PathVariable Integer id) {
        return factureService.getById(id)
                .map(facture -> {
                    Map<String, Object> export = new HashMap<>();

                    // Informations de la facture
                    export.put("numeroFacture", facture.getIdFacture());
                    export.put("date", facture.getDate());

                    // Informations client
                    Map<String, Object> clientInfo = new HashMap<>();
                    clientInfo.put("nom", facture.getClient().getNom());
                    clientInfo.put("email", facture.getClient().getEmail());
                    clientInfo.put("siret", facture.getClient().getSiret());
                    export.put("client", clientInfo);

                    // Lignes de la facture
                    List<Map<String, Object>> lignesExport = facture.getLignes().stream()
                            .map(ligne -> {
                                Map<String, Object> ligneMap = new HashMap<>();
                                ligneMap.put("description", ligne.getDescription());
                                ligneMap.put("quantite", ligne.getQuantite());
                                ligneMap.put("prixUnitaireHT", ligne.getPrixUnitaireHT());
                                ligneMap.put("tauxTVA", ligne.getTauxTVA());
                                ligneMap.put("totalLigneHT", ligne.getPrixUnitaireHT() * ligne.getQuantite());
                                return ligneMap;
                            })
                            .toList();
                    export.put("lignes", lignesExport);

                    // Totaux
                    Map<String, Object> totaux = new HashMap<>();
                    totaux.put("totalHT", facture.getTotalHT());
                    totaux.put("totalTVA", facture.getTotalTVA());
                    totaux.put("totalTTC", facture.getTotalTTC());
                    export.put("totaux", totaux);

                    return ResponseEntity.ok(export);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Suppression d'une facture
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Integer id) {
        if (factureService.getById(id).isPresent()) {
            factureService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Validation d'une ligne de facture
     */
    private boolean isLigneValide(LigneFacture ligne) {
        return ligne.getDescription() != null && !ligne.getDescription().trim().isEmpty()
                && ligne.getQuantite() != null && ligne.getQuantite() > 0
                && ligne.getPrixUnitaireHT() != null && ligne.getPrixUnitaireHT() > 0
                && ligne.getTauxTVA() != null;
    }
}