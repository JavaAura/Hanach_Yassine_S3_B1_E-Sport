package org.esport.controller;

import org.esport.model.Tournoi;
import org.esport.service.interfaces.TournoiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TournoiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiController.class);
    private final TournoiService tournoiService;

    public TournoiController(TournoiService tournoiService) {
        this.tournoiService = tournoiService;
    }

    public Tournoi creerTournoi(String titre, Long jeuId, LocalDate dateDebut, LocalDate dateFin,
            int nombreSpectateurs) {
        LOGGER.info("Tentative de création d'un nouveau tournoi: {}", titre);
        Tournoi tournoi = new Tournoi();
        tournoi.setTitre(titre);
        // Vous devrez obtenir l'objet Jeu à partir du jeuId et le définir ici
        tournoi.setDateDebut(dateDebut);
        tournoi.setDateFin(dateFin);
        tournoi.setNombreSpectateurs(nombreSpectateurs);
        return tournoiService.creerTournoi(tournoi);
    }

    public Tournoi modifierTournoi(Long id, String nouveauTitre, LocalDate nouvelleDateDebut,
            LocalDate nouvelleDateFin, int nouveauNombreSpectateurs) {
        LOGGER.info("Tentative de modification du tournoi avec l'ID: {}", id);
        Optional<Tournoi> tournoiOptional = tournoiService.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
            tournoi.setTitre(nouveauTitre);
            tournoi.setDateDebut(nouvelleDateDebut);
            tournoi.setDateFin(nouvelleDateFin);
            tournoi.setNombreSpectateurs(nouveauNombreSpectateurs);
            return tournoiService.modifierTournoi(tournoi);
        } else {
            LOGGER.warn("Tournoi avec l'ID {} non trouvé", id);
            return null;
        }
    }

    public void supprimerTournoi(Long id) {
        LOGGER.info("Tentative de suppression du tournoi avec l'ID: {}", id);
        tournoiService.supprimerTournoi(id);
    }

    public Optional<Tournoi> obtenirTournoi(Long id) {
        LOGGER.info("Tentative d'obtention du tournoi avec l'ID: {}", id);
        return tournoiService.obtenirTournoi(id);
    }

    public List<Tournoi> obtenirTousTournois() {
        LOGGER.info("Tentative d'obtention de tous les tournois");
        return tournoiService.obtenirTousTournois();
    }

    public void ajouterEquipeATournoi(Long tournoiId, Long equipeId) {
        LOGGER.info("Tentative d'ajout de l'équipe {} au tournoi {}", equipeId, tournoiId);
        tournoiService.ajouterEquipe(tournoiId, equipeId);
    }

    public void retirerEquipeDeTournoi(Long tournoiId, Long equipeId) {
        LOGGER.info("Tentative de retrait de l'équipe {} du tournoi {}", equipeId, tournoiId);
        tournoiService.retirerEquipe(tournoiId, equipeId);
    }

    public int obtenirDureeEstimeeTournoi(Long tournoiId) {
        LOGGER.info("Tentative d'obtention de la durée estimée du tournoi avec l'ID: {}", tournoiId);
        return tournoiService.obtenirdureeEstimeeTournoi(tournoiId);
    }
}
