package org.esport.presentation.menu;

import org.esport.controller.EquipeController;
import org.esport.controller.JoueurController;
import org.esport.model.Equipe;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EquipeMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquipeMenu.class);
    private final EquipeController equipeController;
    private final JoueurController joueurController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public EquipeMenu(EquipeController equipeController, JoueurController joueurController, ConsoleLogger consoleLogger,
            Scanner scanner) {
        this.equipeController = equipeController;
        this.joueurController = joueurController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Menu Équipe:");
            consoleLogger.afficherMessage("1. Créer une équipe");
            consoleLogger.afficherMessage("2. Modifier une équipe");
            consoleLogger.afficherMessage("3. Supprimer une équipe");
            consoleLogger.afficherMessage("4. Afficher une équipe");
            consoleLogger.afficherMessage("5. Afficher toutes les équipes");
            consoleLogger.afficherMessage("6. Ajouter un joueur à une équipe");
            consoleLogger.afficherMessage("7. Retirer un joueur d'une équipe");
            consoleLogger.afficherMessage("0. Retour au menu principal");
            consoleLogger.afficherMessage("Choisissez une option:");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    creerEquipe();
                    break;
                case 2:
                    modifierEquipe();
                    break;
                case 3:
                    supprimerEquipe();
                    break;
                case 4:
                    afficherEquipe();
                    break;
                case 5:
                    afficherToutesEquipes();
                    break;
                case 6:
                    ajouterJoueurAEquipe();
                    break;
                case 7:
                    retirerJoueurDeEquipe();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void creerEquipe() {
        // Implement team creation logic using equipeController
    }

    private void modifierEquipe() {
        // Implement team modification logic using equipeController
    }

    private void supprimerEquipe() {
        // Implement team deletion logic using equipeController
    }

    private void afficherEquipe() {
        // Implement single team display logic using equipeController
    }

    private void afficherToutesEquipes() {
        // Implement all teams display logic using equipeController
    }

    private void ajouterJoueurAEquipe() {
        // Implement add player to team logic using equipeController and
        // joueurController
    }

    private void retirerJoueurDeEquipe() {
        // Implement remove player from team logic using equipeController and
        // joueurController
    }
}
