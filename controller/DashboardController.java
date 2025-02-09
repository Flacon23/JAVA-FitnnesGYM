/** Clasa pentru gestionrea panoului principal
 * @author Rau Flavius
 * @version  23 Decembrie 2024
 */
package com.fitnessapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String showDashboard() {
        return "dashboard";
    }
}
