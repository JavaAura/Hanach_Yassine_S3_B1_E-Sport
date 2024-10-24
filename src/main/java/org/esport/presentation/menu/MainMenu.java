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
    private final JoueurController joueurController;
    private final EquipeController equipeController;
    private final TournoiController tournoiController;
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public MainMenu(JoueurController joueurController, EquipeController equipeController,
            TournoiController tournoiController, JeuController jeuController,
            ConsoleLogger consoleLogger) {
        this.joueurController = joueurController;
        this.equipeController = equipeController;
        this.tournoiController = tournoiController;
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
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
}
