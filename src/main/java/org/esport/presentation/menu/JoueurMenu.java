package org.esport.presentation.menu;

import org.esport.controller.JoueurController;
import org.esport.model.Joueur;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JoueurMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoueurMenu.class);
    private final JoueurController joueurController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public JoueurMenu(JoueurController joueurController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.joueurController = joueurController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Menu Joueur:");
            consoleLogger.afficherMessage("1. Inscrire un joueur");
            consoleLogger.afficherMessage("2. Modifier un joueur");
            consoleLogger.afficherMessage("3. Supprimer un joueur");
            consoleLogger.afficherMessage("4. Afficher un joueur");
            consoleLogger.afficherMessage("5. Afficher tous les joueurs");
            consoleLogger.afficherMessage("0. Retour au menu principal");
            consoleLogger.afficherMessage("Choisissez une option:");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    inscrireJoueur();
                    break;
                case 2:
                    modifierJoueur();
                    break;
                case 3:
                    supprimerJoueur();
                    break;
                case 4:
                    afficherJoueur();
                    break;
                case 5:
                    afficherTousJoueurs();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void inscrireJoueur() {
        // Implement player registration logic using joueurController
    }

    private void modifierJoueur() {
        // Implement player modification logic using joueurController
    }

    private void supprimerJoueur() {
        // Implement player deletion logic using joueurController
    }

    private void afficherJoueur() {
        // Implement single player display logic using joueurController
    }

    private void afficherTousJoueurs() {
        // Implement all players display logic using joueurController
    }
}
