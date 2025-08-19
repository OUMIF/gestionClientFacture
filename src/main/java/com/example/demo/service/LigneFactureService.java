package com.example.demo.service;

import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.LigneFactureRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LigneFactureService {

    private final LigneFactureRepository ligneFactureRepository;

    public LigneFactureService(LigneFactureRepository ligneFactureRepository) {
        this.ligneFactureRepository = ligneFactureRepository;
    }


    public List<LigneFacture> getAllLignesFacture() {
        return ligneFactureRepository.findAll();
    }


    public Optional<LigneFacture> getLigneFactureById(Integer id) {
        return ligneFactureRepository.findById(id);
    }


    public LigneFacture updateLigneFacture(Integer id, LigneFacture details) {
        return ligneFactureRepository.findById(id)
                .map(ligne -> {
                    if (details.getDescription() != null) {
                        ligne.setDescription(details.getDescription());
                    }
                    if (details.getQuantite() != null) {
                        ligne.setQuantite(details.getQuantite());
                    }
                    if (details.getPrixUnitaireHT() != null) {
                        ligne.setPrixUnitaireHT(details.getPrixUnitaireHT());
                    }
                    return ligneFactureRepository.save(ligne);
                })
                .orElse(null);
    }

    public void deleteLigneFacture(Integer id) {
        ligneFactureRepository.deleteById(id);
    }
}