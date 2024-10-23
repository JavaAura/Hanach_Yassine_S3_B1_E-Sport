package org.esport.presentation.menu;

import org.esport.controller.JoueurController;
import org.esport.controller.EquipeController;
import org.esport.controller.TournoiController;
import org.esport.controller.JeuController;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.esport.service.interfaces.JoueurService;
import org.esport.service.interfaces.EquipeService;
import org.esport.service.interfaces.TournoiService;
import org.esport.service.interfaces.JeuService;

import java.util.Scanner;

public class MainMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);
    private JoueurController joueurController;
    private EquipeController equipeController;
    private TournoiController tournoiController;
    private JeuController jeuController;
    private ConsoleLogger consoleLogger;
    private Scanner scanner;

    // Setters for dependency injection
    public void setJoueurController(JoueurController joueurController) {
        this.joueurController = joueurController;
    }

    public void setEquipeController(EquipeController equipeController) {
        this.equipeController = equipeController;
    }

    public void setTournoiController(TournoiController tournoiController) {
        this.tournoiController = tournoiController;
    }

    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }

    public void setConsoleLogger(ConsoleLogger consoleLogger) {
        this.consoleLogger = consoleLogger;
    }

    public void afficherMenuPrincipal() {
        scanner = new Scanner(System.in);
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Menu principal:");
            consoleLogger.afficherMessage("1. Gestion des joueurs");
            consoleLogger.afficherMessage("2. Gestion des équipes");
            consoleLogger.afficherMessage("3. Gestion des tournois");
            consoleLogger.afficherMessage("4. Gestion des jeux");
            consoleLogger.afficherMessage("0. Quitter");
            consoleLogger.afficherMessage("Choisissez une option:");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    new JoueurMenu(joueurController, consoleLogger, scanner).afficherMenu();
                    break;
                case 2:
                    new EquipeMenu(equipeController, joueurController, consoleLogger, scanner).afficherMenu();
                    break;
                case 3:
                    new TournoiMenu(tournoiController, equipeController, jeuController, consoleLogger, scanner)
                            .afficherMenu();
                    break;
                case 4:
                    new JeuMenu(jeuController, consoleLogger, scanner).afficherMenu();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Option invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    public void setJoueurService(JoueurService joueurService) {
        this.joueurController = new JoueurController(joueurService);
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeController = new EquipeController(equipeService);
    }

    public void setTournoiService(TournoiService tournoiService) {
        this.tournoiController = new TournoiController(tournoiService);
    }

    public void setJeuService(JeuService jeuService) {
        this.jeuController = new JeuController(jeuService);
    }
}
