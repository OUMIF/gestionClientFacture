package com.example.demo.repository;

import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneFactureRepository extends JpaRepository<LigneFacture, Integer> {}
