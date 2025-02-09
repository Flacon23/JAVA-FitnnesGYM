/** Clasa pentru functionalitatile securitatii aplicatiei
 * @author Rau Flavius
 * @version  23 Decemvrie 2024
 */
package com.fitnessapp.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    private final Map<String, String> userDatabase = new HashMap<>();

    public LoginService() {
        // Adăugăm utilizatori și parole (pentru test)
        userDatabase.put("Antrenor1", "StrongP@ss1234");
        userDatabase.put("Antrenor2", "ParolaSlaba23");
    }

    public boolean authenticate(String username, String password) {
        if (!userDatabase.containsKey(username)) {
            return false;
        }
        String storedPassword = userDatabase.get(username);
        return storedPassword.equals(password) && validatePassword(password).isEmpty();
    }

    public List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (!password.matches(".*[a-z].*")) {
            errors.add("Parola trebuie să conțină cel puțin o literă mică.");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Parola trebuie să conțină cel puțin o literă mare.");
        }
        if (!password.matches(".*\\d.*\\d.*\\d.*\\d.*")) {
            errors.add("Parola trebuie să conțină cel puțin 4 cifre.");
        }
        if (!password.matches(".*[@$!%*?&].*")) {
            errors.add("Parola trebuie să conțină cel puțin un caracter special.");
        }
        if (password.length() < 8) {
            errors.add("Parola trebuie să aibă cel puțin 8 caractere.");
        }

        return errors;
    }
}
