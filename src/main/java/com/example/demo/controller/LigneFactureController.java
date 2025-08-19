package com.example.demo.controller;

import com.example.demo.entity.LigneFacture;
import com.example.demo.service.LigneFactureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des lignes de facture
 */
@RestController
@RequestMapping("/api/lignes-facture")
public class LigneFactureController {

    private final LigneFactureService ligneFactureService;

    public LigneFactureController(LigneFactureService ligneFactureService) {
        this.ligneFactureService = ligneFactureService;
    }

    /**
     * Liste de toutes les lignes de facture
     */
    @GetMapping
    public ResponseEntity<List<LigneFacture>> getAllLignesFacture() {
        List<LigneFacture> lignes = ligneFactureService.getAllLignesFacture();
        return ResponseEntity.ok(lignes);
    }

    /**
     * Détail d'une ligne de facture par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LigneFacture> getLigneFactureById(@PathVariable Integer id) {
        return ligneFactureService.getLigneFactureById(id)
                .map(ligne -> ResponseEntity.ok(ligne))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Mise à jour d'une ligne de facture
     */
    @PutMapping("/{id}")
    public ResponseEntity<LigneFacture> updateLigneFacture(@PathVariable Integer id, @RequestBody LigneFacture ligneDetails) {
        LigneFacture updatedLigne = ligneFactureService.updateLigneFacture(id, ligneDetails);
        if (updatedLigne != null) {
            return ResponseEntity.ok(updatedLigne);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Suppression d'une ligne de facture
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneFacture(@PathVariable Integer id) {
        if (ligneFactureService.getLigneFactureById(id).isPresent()) {
            ligneFactureService.deleteLigneFacture(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}