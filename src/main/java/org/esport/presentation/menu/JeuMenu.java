package org.esport.presentation.menu;

import org.esport.controller.JeuController;
import org.esport.model.Jeu;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JeuMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(JeuMenu.class);
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public JeuMenu(JeuController jeuController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Menu Jeu:");
            consoleLogger.afficherMessage("1. Créer un jeu");
            consoleLogger.afficherMessage("2. Modifier un jeu");
            consoleLogger.afficherMessage("3. Supprimer un jeu");
            consoleLogger.afficherMessage("4. Afficher un jeu");
            consoleLogger.afficherMessage("5. Afficher tous les jeux");
            consoleLogger.afficherMessage("0. Retour au menu principal");
            consoleLogger.afficherMessage("Choisissez une option:");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    creerJeu();
                    break;
                case 2:
                    modifierJeu();
                    break;
                case 3:
                    supprimerJeu();
                    break;
                case 4:
                    afficherJeu();
                    break;
                case 5:
                    afficherTousJeux();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void creerJeu() {
        // Implement game creation logic using jeuController
    }

    private void modifierJeu() {
        // Implement game modification logic using jeuController
    }

    private void supprimerJeu() {
        // Implement game deletion logic using jeuController
    }

    private void afficherJeu() {
        // Implement single game display logic using jeuController
    }

    private void afficherTousJeux() {
        // Implement all games display logic using jeuController
    }
}
