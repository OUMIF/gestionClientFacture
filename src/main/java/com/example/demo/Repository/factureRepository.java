package com.example.demo.Repository;

import com.example.demo.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface factureRepository extends JpaRepository<Facture, Integer> {}

