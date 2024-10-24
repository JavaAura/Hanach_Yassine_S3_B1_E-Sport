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
            consoleLogger.afficherMessage("6. Afficher les joueurs d'une équipe");
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
                case 6:
                    afficherJoueursParEquipe();
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
        consoleLogger.afficherMessage("Entrez le pseudo du joueur:");
        String pseudo = scanner.nextLine();
        consoleLogger.afficherMessage("Entrez l'âge du joueur:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Joueur nouveauJoueur = joueurController.inscrireJoueur(pseudo, age);
        if (nouveauJoueur != null) {
            consoleLogger.afficherMessage("Joueur inscrit avec succès. ID: " + nouveauJoueur.getId());
        } else {
            consoleLogger.afficherErreur("Échec de l'inscription du joueur.");
        }
    }

    private void modifierJoueur() {
        consoleLogger.afficherMessage("Entrez l'ID du joueur à modifier:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        consoleLogger.afficherMessage("Entrez le nouveau pseudo du joueur:");
        String nouveauPseudo = scanner.nextLine();
        consoleLogger.afficherMessage("Entrez le nouvel âge du joueur:");
        int nouvelAge = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Joueur joueurModifie = joueurController.modifierJoueur(id, nouveauPseudo, nouvelAge);
        if (joueurModifie != null) {
            consoleLogger.afficherMessage("Joueur modifié avec succès.");
        } else {
            consoleLogger.afficherErreur("Échec de la modification du joueur.");
        }
    }

    private void supprimerJoueur() {
        consoleLogger.afficherMessage("Entrez l'ID du joueur à supprimer:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        joueurController.supprimerJoueur(id);
        consoleLogger.afficherMessage("Joueur supprimé avec succès.");
    }

    private void afficherJoueur() {
        consoleLogger.afficherMessage("Entrez l'ID du joueur à afficher:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Joueur> joueurOptional = joueurController.obtenirJoueur(id);
        if (joueurOptional.isPresent()) {
            Joueur joueur = joueurOptional.get();
            consoleLogger.afficherMessage("Détails du joueur:");
            consoleLogger.afficherMessage("ID: " + joueur.getId());
            consoleLogger.afficherMessage("Pseudo: " + joueur.getPseudo());
            consoleLogger.afficherMessage("Âge: " + joueur.getAge());
        } else {
            consoleLogger.afficherErreur("Joueur non trouvé.");
        }
    }

    private void afficherTousJoueurs() {
        List<Joueur> joueurs = joueurController.obtenirTousJoueurs();
        if (!joueurs.isEmpty()) {
            consoleLogger.afficherMessage("Liste de tous les joueurs:");
            for (Joueur joueur : joueurs) {
                consoleLogger.afficherMessage(
                        "ID: " + joueur.getId() + ", Pseudo: " + joueur.getPseudo() + ", Âge: " + joueur.getAge());
            }
        } else {
            consoleLogger.afficherMessage("Aucun joueur trouvé.");
        }
    }

    private void afficherJoueursParEquipe() {
        consoleLogger.afficherMessage("Entrez l'ID de l'équipe:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine();

        List<Joueur> joueurs = joueurController.obtenirJoueursParEquipe(equipeId);
        if (!joueurs.isEmpty()) {
            consoleLogger.afficherMessage("Joueurs de l'équipe (ID: " + equipeId + "):");
            for (Joueur joueur : joueurs) {
                consoleLogger.afficherMessage(
                        "ID: " + joueur.getId() + ", Pseudo: " + joueur.getPseudo() + ", Âge: " + joueur.getAge());
            }
        } else {
            consoleLogger.afficherMessage("Aucun joueur trouvé pour cette équipe.");
        }
    }
}
