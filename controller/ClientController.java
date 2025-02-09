/** Clasa pentru gestionre Clienti
 * @author Rau Flavius
 * @version  8 ianuarie 2025
 */
package com.fitnessapp.controller;

import com.fitnessapp.entity.Client;
import com.fitnessapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clienti")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getAllClients(Model model) {
        var clienti = clientService.findAll();
        model.addAttribute("clienti", clienti);
        return "clienti";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addClient(
            @RequestParam String nume,
            @RequestParam String prenume,
            @RequestParam String email,
            @RequestParam String telefon
    ) {
        try {
            // Verificăm dacă vreun câmp este gol
            if (nume == null || nume.trim().isEmpty() ||
                    prenume == null || prenume.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    telefon == null || telefon.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Toate câmpurile sunt obligatorii și nu pot fi goale.");
            }

            // Validăm că numele și prenumele conțin doar litere
            if (!nume.matches("[a-zA-ZăâîșțĂÂÎȘȚ]+")) {
                return ResponseEntity.badRequest().body("Numele trebuie să conțină doar litere.");
            }

            if (!prenume.matches("[a-zA-ZăâîșțĂÂÎȘȚ]+")) {
                return ResponseEntity.badRequest().body("Prenumele trebuie să conțină doar litere.");
            }

            // Validăm email-ul
            if (!email.contains("@")) {
                return ResponseEntity.badRequest().body("Email-ul trebuie să conțină simbolul '@'.");
            }

            // Validăm telefonul (trebuie să fie exact 10 caractere numerice)
            if (!telefon.matches("\\d{10}")) {
                return ResponseEntity.badRequest().body("Numărul de telefon trebuie să aibă exact 10 cifre.");
            }

            // Creăm clientul și setăm valorile
            Client client = new Client();
            client.setNume(nume);
            client.setPrenume(prenume);
            client.setEmail(email);
            client.setTelefon(telefon);

            // Salvăm clientul prin serviciu
            clientService.addClient(client);

            return ResponseEntity.ok("Client adăugat cu succes.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Client>> searchClientsByName(@RequestParam String nume) {
        var clienti = clientService.findByName(nume);
        return ResponseEntity.ok(clienti);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateClient(
            @PathVariable Long id,
            @RequestBody Client updatedClient
    ) {
        try {
            // Validare câmpuri goale
            StringBuilder errors = new StringBuilder();
            if (updatedClient.getNume() == null || updatedClient.getNume().trim().isEmpty()) {
                errors.append("Numele este obligatoriu.\n");
            }
            if (updatedClient.getPrenume() == null || updatedClient.getPrenume().trim().isEmpty()) {
                errors.append("Prenumele este obligatoriu.\n");
            }
            if (updatedClient.getEmail() == null || updatedClient.getEmail().trim().isEmpty()) {
                errors.append("Email-ul este obligatoriu.\n");
            }
            if (updatedClient.getTelefon() == null || updatedClient.getTelefon().trim().isEmpty()) {
                errors.append("Telefonul este obligatoriu.\n");
            }

            if (errors.length() > 0) {
                // Returnăm erorile în cazul în care există câmpuri necompletate
                return ResponseEntity.badRequest().body(errors.toString());
            }

            // Încercăm actualizarea clientului
            boolean success = clientService.updateClient(id, updatedClient);
            if (success) {
                return ResponseEntity.ok("Client actualizat cu succes.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Eroare la actualizare: " + e.getMessage());
        }
    }


    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteClient(@RequestParam Long id) {
        boolean success = clientService.deleteClientById(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
/* format json*/
@RestController
@RequestMapping("/api/clienti")
class ClientApiController {
    private final ClientService clientService;

    @Autowired
    public ClientApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClientsJson() {
        var clienti = clientService.findAll();
        return ResponseEntity.ok(clienti);
    }

}