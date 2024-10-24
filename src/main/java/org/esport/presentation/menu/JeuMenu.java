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
        consoleLogger.afficherMessage("Création d'un nouveau jeu");
        consoleLogger.afficherMessage("Entrez le nom du jeu:");
        String nom = scanner.nextLine();
        consoleLogger.afficherMessage("Entrez la difficulté du jeu (1-10):");
        int difficulte = scanner.nextInt();
        consoleLogger.afficherMessage("Entrez la durée moyenne d'un match (en minutes):");
        int dureeMoyenneMatch = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Jeu nouveauJeu = jeuController.creerJeu(nom, difficulte, dureeMoyenneMatch);
        if (nouveauJeu != null) {
            consoleLogger.afficherMessage("Jeu créé avec succès. ID: " + nouveauJeu.getId());
        } else {
            consoleLogger.afficherErreur("Erreur lors de la création du jeu.");
        }
    }

    private void modifierJeu() {
        consoleLogger.afficherMessage("Modification d'un jeu");
        consoleLogger.afficherMessage("Entrez l'ID du jeu à modifier:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            Jeu jeu = jeuOptional.get();
            consoleLogger.afficherMessage("Jeu trouvé: " + jeu.getNom());
            consoleLogger.afficherMessage("Entrez le nouveau nom du jeu (ou appuyez sur Entrée pour garder l'ancien):");
            String nouveauNom = scanner.nextLine();
            if (nouveauNom.isEmpty()) {
                nouveauNom = jeu.getNom();
            }

            consoleLogger
                    .afficherMessage("Entrez la nouvelle difficulté du jeu (1-10) (ou -1 pour garder l'ancienne):");
            int nouvelleDifficulte = scanner.nextInt();
            if (nouvelleDifficulte == -1) {
                nouvelleDifficulte = jeu.getDifficulte();
            }

            consoleLogger.afficherMessage(
                    "Entrez la nouvelle durée moyenne d'un match (en minutes) (ou -1 pour garder l'ancienne):");
            int nouvelleDureeMoyenneMatch = scanner.nextInt();
            if (nouvelleDureeMoyenneMatch == -1) {
                nouvelleDureeMoyenneMatch = jeu.getDureeMoyenneMatch();
            }
            scanner.nextLine(); // Consommer la nouvelle ligne

            Jeu jeuModifie = jeuController.modifierJeu(id, nouveauNom, nouvelleDifficulte, nouvelleDureeMoyenneMatch);
            if (jeuModifie != null) {
                consoleLogger.afficherMessage("Jeu modifié avec succès.");
            } else {
                consoleLogger.afficherErreur("Erreur lors de la modification du jeu.");
            }
        } else {
            consoleLogger.afficherErreur("Jeu non trouvé.");
        }
    }

    private void supprimerJeu() {
        consoleLogger.afficherMessage("Suppression d'un jeu");
        consoleLogger.afficherMessage("Entrez l'ID du jeu à supprimer:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            consoleLogger.afficherMessage(
                    "Êtes-vous sûr de vouloir supprimer le jeu " + jeuOptional.get().getNom() + "? (O/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("O")) {
                jeuController.supprimerJeu(id);
                consoleLogger.afficherMessage("Jeu supprimé avec succès.");
            } else {
                consoleLogger.afficherMessage("Suppression annulée.");
            }
        } else {
            consoleLogger.afficherErreur("Jeu non trouvé.");
        }
    }

    private void afficherJeu() {
        consoleLogger.afficherMessage("Affichage d'un jeu");
        consoleLogger.afficherMessage("Entrez l'ID du jeu à afficher:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            Jeu jeu = jeuOptional.get();
            consoleLogger.afficherMessage("Détails du jeu:");
            consoleLogger.afficherMessage("ID: " + jeu.getId());
            consoleLogger.afficherMessage("Nom: " + jeu.getNom());
            consoleLogger.afficherMessage("Difficulté: " + jeu.getDifficulte());
            consoleLogger.afficherMessage("Durée moyenne d'un match: " + jeu.getDureeMoyenneMatch() + " minutes");
        } else {
            consoleLogger.afficherErreur("Jeu non trouvé.");
        }
    }

    private void afficherTousJeux() {
        consoleLogger.afficherMessage("Liste de tous les jeux:");
        List<Jeu> jeux = jeuController.obtenirTousJeux();
        if (!jeux.isEmpty()) {
            for (Jeu jeu : jeux) {
                consoleLogger.afficherMessage("ID: " + jeu.getId() + ", Nom: " + jeu.getNom() + ", Difficulté: "
                        + jeu.getDifficulte() + ", Durée moyenne: " + jeu.getDureeMoyenneMatch() + " minutes");
            }
        } else {
            consoleLogger.afficherMessage("Aucun jeu trouvé.");
        }
    }
}
