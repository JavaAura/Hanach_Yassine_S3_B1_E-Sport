package org.esport.dao.impl;

import org.esport.dao.interfaces.TournoiDao;
import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import org.esport.model.Jeu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class TournoiDaoExtension implements TournoiDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiDaoExtension.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final TournoiDaoImpl tournoiDaoImpl;

    public TournoiDaoExtension(TournoiDaoImpl tournoiDaoImpl) {
        this.tournoiDaoImpl = tournoiDaoImpl;
    }

    @Override
    public Tournoi creer(Tournoi tournoi) {
        return tournoiDaoImpl.creer(tournoi);
    }

    @Override
    public Tournoi modifier(Tournoi tournoi) {
        return tournoiDaoImpl.modifier(tournoi);
    }

    @Override
    public void supprimer(Long id) {
        tournoiDaoImpl.supprimer(id);
    }

    @Override
    public Optional<Tournoi> trouverParId(Long id) {
        return tournoiDaoImpl.trouverParId(id);
    }

    @Override
    public List<Tournoi> trouverTous() {
        return tournoiDaoImpl.trouverTous();
    }

    @Override
    public void ajouterEquipe(Long tournoiId, Equipe equipe) {
        tournoiDaoImpl.ajouterEquipe(tournoiId, equipe);
    }

    @Override
    public void retirerEquipe(Long tournoiId, Equipe equipe) {
        tournoiDaoImpl.retirerEquipe(tournoiId, equipe);
    }

    @Override
    public int calculerdureeEstimeeTournoi(Long tournoiId) {
        Tournoi tournoi = entityManager.find(Tournoi.class, tournoiId);
        if (tournoi != null) {
            int nombreEquipes = tournoi.getEquipes().size();
            Jeu jeu = tournoi.getJeu();
            int dureeMoyenneMatch = jeu.getDureeMoyenneMatch();
            int difficulteJeu = jeu.getDifficulte();
            int tempsPauseEntreMatchs = tournoi.getTempsPauseEntreMatchs();
            int tempsCeremonie = tournoi.getTempsCeremonie();

            int dureeEstimee = (nombreEquipes * dureeMoyenneMatch * difficulteJeu) + tempsPauseEntreMatchs
                    + tempsCeremonie;
            LOGGER.info("Durée estimée calculée (avancée) pour le tournoi avec l'ID {}: {} minutes", tournoiId,
                    dureeEstimee);
            return dureeEstimee;
        } else {
            LOGGER.warn("Tentative de calcul de la durée estimée (avancée) pour un tournoi inexistant avec l'ID: {}",
                    tournoiId);
            return 0;
        }
    }
}