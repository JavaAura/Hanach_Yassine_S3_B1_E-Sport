package org.esport.controller;

import org.esport.model.Jeu;
import org.esport.model.Tournoi;
import org.esport.service.interfaces.TournoiService;
import org.esport.service.interfaces.JeuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import org.esport.model.enums.TournoiStatus;
import java.util.List;
import java.util.Optional;
import org.esport.util.ValidationUtil;

public class TournoiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiController.class);
    private final TournoiService tournoiService;
    private final JeuService jeuService;

    public TournoiController(TournoiService tournoiService, JeuService jeuService) {
        this.tournoiService = tournoiService;
        this.jeuService = jeuService;
    }

    public Tournoi creerTournoi(String titre, Long jeuId, LocalDate dateDebut, LocalDate dateFin,
            int nombreSpectateurs, int dureeMoyenneMatch, int tempsCeremonie, int tempsPauseEntreMatchs) {
        LOGGER.info("Tentative de création d'un nouveau tournoi: {}", titre);

        // Validate inputs
        ValidationUtil.validateTitle(titre);
        ValidationUtil.validateTournamentId(jeuId);
        ValidationUtil.validateDates(dateDebut, dateFin);
        ValidationUtil.validateSpectatorCount(nombreSpectateurs);
        ValidationUtil.validateTime(dureeMoyenneMatch);
        ValidationUtil.validateTime(tempsCeremonie);
        ValidationUtil.validateTime(tempsPauseEntreMatchs);

        Tournoi tournoi = new Tournoi();
        tournoi.setTitre(titre);

        Jeu jeu = jeuService.obtenirJeu(jeuId)
                .orElseThrow(() -> new IllegalArgumentException("Jeu non trouvé pour l'ID: " + jeuId));
        tournoi.setJeu(jeu);

        tournoi.setDateDebut(dateDebut);
        tournoi.setDateFin(dateFin);
        tournoi.setNombreSpectateurs(nombreSpectateurs);
        tournoi.setDureeMoyenneMatch(dureeMoyenneMatch);
        tournoi.setTempsCeremonie(tempsCeremonie);
        tournoi.setTempsPauseEntreMatchs(tempsPauseEntreMatchs);

        // Save the tournament first to get an ID
        Tournoi savedTournoi = tournoiService.creerTournoi(tournoi);

        // Calculate DUREEESTIMEE using the method from TournoiDaoExtension
        int dureeEstimee = tournoiService.calculerdureeEstimeeTournoi(savedTournoi.getId());
        savedTournoi.setDureeEstimee(dureeEstimee);

        // Update the tournament with the calculated DUREEESTIMEE
        return tournoiService.modifierTournoi(savedTournoi);
    }

    public Tournoi modifierTournoi(Long id, String nouveauTitre, LocalDate nouvelleDateDebut,
            LocalDate nouvelleDateFin, int nouveauNombreSpectateurs) {
        LOGGER.info("Tentative de modification du tournoi avec l'ID: {}", id);

        // Validate inputs
        ValidationUtil.validateTournamentId(id);
        ValidationUtil.validateTitle(nouveauTitre);
        ValidationUtil.validateDates(nouvelleDateDebut, nouvelleDateFin);
        ValidationUtil.validateSpectatorCount(nouveauNombreSpectateurs);

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
        ValidationUtil.validateTournamentId(id);
        tournoiService.supprimerTournoi(id);
    }

    public Optional<Tournoi> obtenirTournoi(Long id) {
        LOGGER.info("Tentative d'obtention du tournoi avec l'ID: {}", id);
        ValidationUtil.validateTournamentId(id);
        return tournoiService.obtenirTournoi(id);
    }

    public List<Tournoi> obtenirTousTournois() {
        LOGGER.info("Tentative d'obtention de tous les tournois");
        return tournoiService.obtenirTousTournois();
    }

    public void ajouterEquipeATournoi(Long tournoiId, Long equipeId) {
        LOGGER.info("Tentative d'ajout de l'équipe {} au tournoi {}", equipeId, tournoiId);
        ValidationUtil.validateTournamentId(tournoiId);
        ValidationUtil.validateTournamentId(equipeId);
        tournoiService.ajouterEquipe(tournoiId, equipeId);
    }

    public void retirerEquipeDeTournoi(Long tournoiId, Long equipeId) {
        LOGGER.info("Tentative de retrait de l'équipe {} du tournoi {}", equipeId, tournoiId);
        ValidationUtil.validateTournamentId(tournoiId);
        ValidationUtil.validateTournamentId(equipeId);
        tournoiService.retirerEquipe(tournoiId, equipeId);
    }

    public int obtenirDureeEstimeeTournoi(Long tournoiId) {
        LOGGER.info("Tentative d'obtention de la durée estimée du tournoi avec l'ID: {}", tournoiId);
        ValidationUtil.validateTournamentId(tournoiId);
        return tournoiService.calculerdureeEstimeeTournoi(tournoiId);
    }

    public void modifierStatutTournoi(Long tournoiId, TournoiStatus nouveauStatut) {
        LOGGER.info("Tentative de modification du statut du tournoi {} à {}", tournoiId, nouveauStatut);
        ValidationUtil.validateTournamentId(tournoiId);
        ValidationUtil.validateStatus(nouveauStatut);
        tournoiService.modifierStatutTournoi(tournoiId, nouveauStatut);
    }
}