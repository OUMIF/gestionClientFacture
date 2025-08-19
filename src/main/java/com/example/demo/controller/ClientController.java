package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        return clientService.getById(id)
                .map(client -> ResponseEntity.ok(client))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        // juste pour la validation
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty() || !client.getEmail().contains("@gmail.com")) {
            return ResponseEntity.badRequest().build();
        }

        if (client.getSiret() == null || client.getSiret().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client clientnv) {
        return clientService.getById(id)
                .map(client -> {
                    if (clientnv.getNom() != null) {
                        client.setNom(clientnv.getNom());
                    }
                    if (clientnv.getEmail() != null) {
                        client.setEmail(clientnv.getEmail());
                    }
                    if (clientnv.getSiret() != null) {
                        client.setSiret(clientnv.getSiret());
                    }
                    return ResponseEntity.ok(clientService.save(client));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        if (clientService.getById(id).isPresent()) {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}