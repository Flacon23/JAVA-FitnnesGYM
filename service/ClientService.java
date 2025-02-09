package com.fitnessapp.service;

import com.fitnessapp.entity.Client;
import com.fitnessapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void addClient(Client client) {
        validateClient(client);
        clientRepository.save(client);
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public boolean updateClient(Long id, Client updatedClient) {
        validateClient(updatedClient);
        return clientRepository.findById(id)
                .map(client -> {
                    client.setNume(updatedClient.getNume());
                    client.setPrenume(updatedClient.getPrenume());
                    client.setEmail(updatedClient.getEmail());
                    client.setTelefon(updatedClient.getTelefon());
                    clientRepository.save(client);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteClientById(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Client> findByName(String nume) {
        return clientRepository.findByNumeContainingIgnoreCase(nume);
    }

    private void validateClient(Client client) {
        StringBuilder errors = new StringBuilder();

        if (!client.getNume().matches("^[a-zA-Z]+$")) {
            errors.append("Numele poate conține doar litere.\n");
        }
        if (!client.getPrenume().matches("^[a-zA-Z]+$")) {
            errors.append("Prenumele poate conține doar litere.\n");
        }
        if (!client.getEmail().contains("@")) {
            errors.append("Emailul trebuie să conțină simbolul '@'.\n");
        }
        if (!client.getTelefon().matches("\\d{10}")) {
            errors.append("Numărul de telefon trebuie să conțină exact 10 cifre.\n");
        }

        if (errors.length() > 0) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
}