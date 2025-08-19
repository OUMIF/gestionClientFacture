package com.example.demo.service;


import com.example.demo.repository.ClientRepository;
import com.example.demo.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll(){
        return clientRepository.findAll();
    }
    public Optional<Client> getById(Integer id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }
}
