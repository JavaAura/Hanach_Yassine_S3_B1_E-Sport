package org.esport.presentation.menu;

import org.esport.controller.EquipeController;
import org.esport.controller.JoueurController;
import org.esport.model.Equipe;
import org.esport.model.Joueur;
import org.esport.util.ConsoleLogger;
import org.esport.util.ValidationUtil;
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
        int choix = -1;
        do {
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

            try {
                choix = ValidationUtil.readNonNegativeInt(scanner, "Choisissez une option:");
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
                        break;
                    default:
                        consoleLogger.afficherErreur("Option invalide. Veuillez réessayer.");
                }
            } catch (IllegalArgumentException e) {
                consoleLogger.afficherErreur(e.getMessage());
            }
        } while (choix != 0);
    }

    private void creerEquipe() {
        consoleLogger.afficherMessage("Création d'une nouvelle équipe");
        String nom = ValidationUtil.readNonEmptyString(scanner, "Entrez le nom de l'équipe:");

        try {
            Equipe equipeCreee = equipeController.creerEquipe(nom);
            if (equipeCreee != null) {
                consoleLogger.afficherMessage("Équipe créée avec succès. ID: " + equipeCreee.getId());
            } else {
                consoleLogger.afficherErreur("Erreur lors de la création de l'équipe.");
            }
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }

    private void modifierEquipe() {
        consoleLogger.afficherMessage("Modification d'une équipe");
        Long id = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID de l'équipe à modifier:");
        String nouveauNom = ValidationUtil.readNonEmptyString(scanner, "Entrez le nouveau nom de l'équipe:");

        try {
            Equipe equipeModifiee = equipeController.modifierEquipe(id, nouveauNom);
            if (equipeModifiee != null) {
                consoleLogger.afficherMessage("Équipe modifiée avec succès.");
            } else {
                consoleLogger.afficherErreur("Erreur lors de la modification de l'équipe.");
            }
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }

    private void supprimerEquipe() {
        consoleLogger.afficherMessage("Suppression d'une équipe");
        Long id = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID de l'équipe à supprimer:");

        try {
            equipeController.supprimerEquipe(id);
            consoleLogger.afficherMessage("Équipe supprimée avec succès (si elle existait).");
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }

    private void afficherEquipe() {
        consoleLogger.afficherMessage("Affichage d'une équipe");
        Long id = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID de l'équipe à afficher:");

        try {
            Optional<Equipe> equipeOptionnelle = equipeController.obtenirEquipe(id);
            if (equipeOptionnelle.isPresent()) {
                Equipe equipe = equipeOptionnelle.get();
                consoleLogger.afficherMessage("ID: " + equipe.getId());
                consoleLogger.afficherMessage("Nom: " + equipe.getNom());
                consoleLogger.afficherMessage("Classement: " + equipe.getClassement());
                consoleLogger.afficherMessage("Joueurs:");
                for (Joueur joueur : equipe.getJoueurs()) {
                    consoleLogger.afficherMessage("  - " + joueur.getPseudo());
                }
            } else {
                consoleLogger.afficherErreur("Équipe non trouvée.");
            }
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }

    private void afficherToutesEquipes() {
        consoleLogger.afficherMessage("Liste de toutes les équipes:");
        List<Equipe> equipes = equipeController.obtenirToutesEquipes();
        if (!equipes.isEmpty()) {
            for (Equipe equipe : equipes) {
                consoleLogger.afficherMessage("ID: " + equipe.getId() + ", Nom: " + equipe.getNom() + ", Classement: "
                        + equipe.getClassement());
            }
        } else {
            consoleLogger.afficherMessage("Aucune équipe trouvée.");
        }
    }

    private void ajouterJoueurAEquipe() {
        consoleLogger.afficherMessage("Ajout d'un joueur à une équipe");
        Long equipeId = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID de l'équipe:");
        Long joueurId = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID du joueur à ajouter:");

        try {
            equipeController.ajouterJoueurAEquipe(equipeId, joueurId);
            consoleLogger.afficherMessage("Joueur ajouté à l'équipe avec succès (si les deux existent).");
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }

    private void retirerJoueurDeEquipe() {
        consoleLogger.afficherMessage("Retrait d'un joueur d'une équipe");
        Long equipeId = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID de l'équipe:");
        Long joueurId = ValidationUtil.readPositiveLong(scanner, "Entrez l'ID du joueur à retirer:");

        try {
            equipeController.retirerJoueurDeEquipe(equipeId, joueurId);
            consoleLogger.afficherMessage("Joueur retiré de l'équipe avec succès (si les deux existent).");
        } catch (IllegalArgumentException e) {
            consoleLogger.afficherErreur("Erreur: " + e.getMessage());
        }
    }
}
