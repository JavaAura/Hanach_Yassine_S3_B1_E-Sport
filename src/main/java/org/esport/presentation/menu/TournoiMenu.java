package org.esport.presentation.menu;

import org.esport.controller.TournoiController;
import org.esport.controller.EquipeController;
import org.esport.controller.JeuController;
import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import org.esport.model.Jeu;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.esport.model.enums.TournoiStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TournoiMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiMenu.class);
    private final TournoiController tournoiController;
    private final EquipeController equipeController;
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public TournoiMenu(TournoiController tournoiController, EquipeController equipeController,
            JeuController jeuController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.tournoiController = tournoiController;
        this.equipeController = equipeController;
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix;
        do {
            System.out.println("\n--- Menu Tournoi ---");
            System.out.println("1. Créer un tournoi");
            System.out.println("2. Modifier un tournoi");
            System.out.println("3. Supprimer un tournoi");
            System.out.println("4. Afficher un tournoi");
            System.out.println("5. Afficher tous les tournois");
            System.out.println("6. Ajouter une équipe à un tournoi");
            System.out.println("7. Retirer une équipe d'un tournoi");
            System.out.println("8. Calculer la durée estimée d'un tournoi");
            System.out.println("9. Modifier le statut d'un tournoi");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choix) {
                case 1:
                    creerTournoi();
                    break;
                case 2:
                    modifierTournoi();
                    break;
                case 3:
                    supprimerTournoi();
                    break;
                case 4:
                    afficherTournoi();
                    break;
                case 5:
                    afficherTousTournois();
                    break;
                case 6:
                    ajouterEquipeATournoi();
                    break;
                case 7:
                    retirerEquipeDeTournoi();
                    break;
                case 8:
                    calculerDureeEstimeeTournoi();
                    break;
                case 9:
                    modifierStatutTournoi();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (choix != 0);
    }

    private void creerTournoi() {
        consoleLogger.afficherMessage("Création d'un nouveau tournoi");
        consoleLogger.afficherMessage("Entrez le titre du tournoi:");
        String titre = scanner.nextLine();

        consoleLogger.afficherMessage("Entrez l'ID du jeu pour ce tournoi:");
        Long jeuId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        consoleLogger.afficherMessage("Entrez la date de début (format: dd/MM/yyyy):");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.afficherMessage("Entrez la date de fin (format: dd/MM/yyyy):");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.afficherMessage("Entrez le nombre de spectateurs attendus:");
        int nombreSpectateurs = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        consoleLogger.afficherMessage("Entrez la durée moyenne d'un match (en minutes):");
        int dureeMoyenneMatch = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        consoleLogger.afficherMessage("Entrez le temps de cérémonie (en minutes):");
        int tempsCeremonie = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        consoleLogger.afficherMessage("Entrez le temps de pause entre les matchs (en minutes):");
        int tempsPauseEntreMatchs = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Tournoi nouveauTournoi = tournoiController.creerTournoi(titre, jeuId, dateDebut, dateFin, nombreSpectateurs,
                dureeMoyenneMatch, tempsCeremonie, tempsPauseEntreMatchs);
        if (nouveauTournoi != null) {
            consoleLogger.afficherMessage("Tournoi créé avec succès. ID: " + nouveauTournoi.getId());
        } else {
            consoleLogger.afficherErreur("Erreur lors de la création du tournoi.");
        }
    }

    private void modifierTournoi() {
        consoleLogger.afficherMessage("Modification d'un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi à modifier:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
            consoleLogger.afficherMessage("Entrez le nouveau titre (ou appuyez sur Entrée pour garder l'ancien):");
            String nouveauTitre = scanner.nextLine();
            if (nouveauTitre.isEmpty()) {
                nouveauTitre = tournoi.getTitre();
            }

            consoleLogger.afficherMessage(
                    "Entrez la nouvelle date de début (format: dd/MM/yyyy) (ou appuyez sur Entrée pour garder l'ancienne):");
            String dateDebutStr = scanner.nextLine();
            LocalDate nouvelleDateDebut = dateDebutStr.isEmpty() ? tournoi.getDateDebut()
                    : LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.afficherMessage(
                    "Entrez la nouvelle date de fin (format: dd/MM/yyyy) (ou appuyez sur Entrée pour garder l'ancienne):");
            String dateFinStr = scanner.nextLine();
            LocalDate nouvelleDateFin = dateFinStr.isEmpty() ? tournoi.getDateFin()
                    : LocalDate.parse(dateFinStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.afficherMessage("Entrez le nouveau nombre de spectateurs (ou -1 pour garder l'ancien):");
            int nouveauNombreSpectateurs = scanner.nextInt();
            if (nouveauNombreSpectateurs == -1) {
                nouveauNombreSpectateurs = tournoi.getNombreSpectateurs();
            }
            scanner.nextLine(); // Consommer la nouvelle ligne

            Tournoi tournoiModifie = tournoiController.modifierTournoi(id, nouveauTitre, nouvelleDateDebut,
                    nouvelleDateFin, nouveauNombreSpectateurs);
            if (tournoiModifie != null) {
                consoleLogger.afficherMessage("Tournoi modifié avec succès.");
            } else {
                consoleLogger.afficherErreur("Erreur lors de la modification du tournoi.");
            }
        } else {
            consoleLogger.afficherErreur("Tournoi non trouvé.");
        }
    }

    private void supprimerTournoi() {
        consoleLogger.afficherMessage("Suppression d'un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi à supprimer:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            consoleLogger.afficherMessage(
                    "Êtes-vous sûr de vouloir supprimer le tournoi " + tournoiOptional.get().getTitre() + "? (O/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("O")) {
                tournoiController.supprimerTournoi(id);
                consoleLogger.afficherMessage("Tournoi supprimé avec succès.");
            } else {
                consoleLogger.afficherMessage("Suppression annulée.");
            }
        } else {
            consoleLogger.afficherErreur("Tournoi non trouvé.");
        }
    }

    private void afficherTournoi() {
        consoleLogger.afficherMessage("Affichage d'un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi à afficher:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
            consoleLogger.afficherMessage("Détails du tournoi:");
            consoleLogger.afficherMessage("ID: " + tournoi.getId());
            consoleLogger.afficherMessage("Titre: " + tournoi.getTitre());
            consoleLogger.afficherMessage("Jeu: " + tournoi.getJeu().getNom());
            consoleLogger.afficherMessage("Date de début: " + tournoi.getDateDebut());
            consoleLogger.afficherMessage("Date de fin: " + tournoi.getDateFin());
            consoleLogger.afficherMessage("Nombre de spectateurs: " + tournoi.getNombreSpectateurs());
            consoleLogger.afficherMessage("Statut: " + tournoi.getStatut());
            consoleLogger.afficherMessage("Équipes participantes:");
            for (Equipe equipe : tournoi.getEquipes()) {
                consoleLogger.afficherMessage("- " + equipe.getNom());
            }
        } else {
            consoleLogger.afficherErreur("Tournoi non trouvé.");
        }
    }

    private void afficherTousTournois() {
        consoleLogger.afficherMessage("Liste de tous les tournois:");
        List<Tournoi> tournois = tournoiController.obtenirTousTournois();
        if (!tournois.isEmpty()) {
            for (Tournoi tournoi : tournois) {
                String jeuNom = tournoi.getJeu() != null ? tournoi.getJeu().getNom() : "N/A";
                consoleLogger.afficherMessage("ID: " + tournoi.getId() + ", Titre: " + tournoi.getTitre() + ", Jeu: "
                        + jeuNom + ", Statut: " + tournoi.getStatut());
            }
        } else {
            consoleLogger.afficherMessage("Aucun tournoi trouvé.");
        }
    }

    private void ajouterEquipeATournoi() {
        consoleLogger.afficherMessage("Ajout d'une équipe à un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi:");
        Long tournoiId = scanner.nextLong();
        consoleLogger.afficherMessage("Entrez l'ID de l'équipe à ajouter:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        try {
            tournoiController.ajouterEquipeATournoi(tournoiId, equipeId);
            consoleLogger.afficherMessage("Équipe ajoutée au tournoi avec succès.");
        } catch (Exception e) {
            consoleLogger.afficherErreur("Erreur lors de l'ajout de l'équipe au tournoi: " + e.getMessage());
        }
    }

    private void retirerEquipeDeTournoi() {
        consoleLogger.afficherMessage("Retrait d'une équipe d'un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi:");
        Long tournoiId = scanner.nextLong();
        consoleLogger.afficherMessage("Entrez l'ID de l'équipe à retirer:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        try {
            tournoiController.retirerEquipeDeTournoi(tournoiId, equipeId);
            consoleLogger.afficherMessage("Équipe retirée du tournoi avec succès.");
        } catch (Exception e) {
            consoleLogger.afficherErreur("Erreur lors du retrait de l'équipe du tournoi: " + e.getMessage());
        }
    }

    private void calculerDureeEstimeeTournoi() {
        consoleLogger.afficherMessage("Calcul de la durée estimée d'un tournoi");
        consoleLogger.afficherMessage("Entrez l'ID du tournoi:");
        Long tournoiId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        int dureeEstimee = tournoiController.obtenirDureeEstimeeTournoi(tournoiId);
        if (dureeEstimee > 0) {
            Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(tournoiId);
            if (tournoiOptional.isPresent()) {
                Tournoi tournoi = tournoiOptional.get();
                consoleLogger.afficherMessage(
                        "La durée estimée du tournoi est de " + tournoi.getDureeEstimee() + " minutes.");
            } else {
                consoleLogger.afficherErreur("Tournoi non trouvé après le calcul.");
            }
        } else {
            consoleLogger.afficherErreur("Impossible de calculer la durée estimée du tournoi.");
        }
    }

    private void modifierStatutTournoi() {
        System.out.println("Entrez l'ID du tournoi :");
        Long tournoiId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        System.out.println("Choisissez le nouveau statut :");
        System.out.println("1. PLANIFIE");
        System.out.println("2. EN_COURS");
        System.out.println("3. TERMINE");
        System.out.println("4. ANNULE");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TournoiStatus nouveauStatut;
        switch (choix) {
            case 1:
                nouveauStatut = TournoiStatus.PLANIFIE;
                break;
            case 2:
                nouveauStatut = TournoiStatus.EN_COURS;
                break;
            case 3:
                nouveauStatut = TournoiStatus.TERMINE;
                break;
            case 4:
                nouveauStatut = TournoiStatus.ANNULE;
                break;
            default:
                System.out.println("Choix invalide. Opération annulée.");
                return;
        }

        tournoiController.modifierStatutTournoi(tournoiId, nouveauStatut);
        System.out.println("Statut du tournoi modifié avec succès.");
    }
}
