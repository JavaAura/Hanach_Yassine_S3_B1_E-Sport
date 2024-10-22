package org.esport.presentation.menu;

import org.esport.service.interfaces.JoueurService;
import org.esport.service.interfaces.EquipeService;
import org.esport.service.interfaces.TournoiService;
import org.esport.service.interfaces.JeuService;

public class MainMenu {
    private JoueurService joueurService;
    private EquipeService equipeService;
    private TournoiService tournoiService;
    private JeuService jeuService;

    // Setters for dependency injection
    public void setJoueurService(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public void setTournoiService(TournoiService tournoiService) {
        this.tournoiService = tournoiService;
    }

    public void setJeuService(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    public void start() {
        System.out.println("Welcome to the E-Sports Tournament Management System");
        // Add your menu logic here
    }
}
