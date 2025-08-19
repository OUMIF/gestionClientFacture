package com.example.demo.Repository;

import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface clientRepository extends JpaRepository<Client, Integer> {}
