package com.example.demo.service;

import com.example.demo.repository.FactureRepository;
import com.example.demo.entity.Facture;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {
    private final FactureRepository facturerepository;

    public FactureService(FactureRepository facturerep) {
        this.facturerepository = facturerep;
    }

    public List<Facture> getAll() {
        return facturerepository.findAll();
    }

    public Optional<Facture> getById(Integer id) {
        return facturerepository.findById(id);
    }

    public Facture save(Facture facture) {
        facture.calculateTotalHT();
        return facturerepository.save(facture);
    }

    public void delete(Integer id) {
        facturerepository.deleteById(id);
    }
}
