package org.esport.dao.impl;

import org.esport.dao.interfaces.TournoiDao;
import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import org.esport.model.Jeu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TournoiDaoImpl implements TournoiDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tournoi creer(Tournoi tournoi) {
        entityManager.persist(tournoi);
        LOGGER.info("Tournoi créé avec l'ID: {}", tournoi.getId());
        return tournoi;
    }

    @Override
    public Tournoi modifier(Tournoi tournoi) {
        Tournoi tournoiModifie = entityManager.merge(tournoi);
        LOGGER.info("Tournoi modifié avec l'ID: {}", tournoiModifie.getId());
        return tournoiModifie;
    }

    @Override
    public void supprimer(Long id) {
        Tournoi tournoi = entityManager.find(Tournoi.class, id);
        if (tournoi != null) {
            entityManager.remove(tournoi);
            LOGGER.info("Tournoi supprimé avec l'ID: {}", id);
        } else {
            LOGGER.warn("Tentative de suppression d'un tournoi inexistant avec l'ID: {}", id);
        }
    }

    @Override
    public Optional<Tournoi> trouverParId(Long id) {
        Tournoi tournoi = entityManager.find(Tournoi.class, id);
        return Optional.ofNullable(tournoi);
    }

    @Override
    public List<Tournoi> trouverTous() {
        TypedQuery<Tournoi> query = entityManager.createQuery("SELECT t FROM Tournoi t", Tournoi.class);
        return query.getResultList();
    }

    @Override
    public void ajouterEquipe(Long tournoiId, Equipe equipe) {
        Tournoi tournoi = entityManager.find(Tournoi.class, tournoiId);
        if (tournoi != null) {
            tournoi.getEquipes().add(equipe);
            entityManager.merge(tournoi);
            LOGGER.info("Équipe ajoutée au tournoi avec l'ID: {}", tournoiId);
        } else {
            LOGGER.warn("Tentative d'ajout d'une équipe à un tournoi inexistant avec l'ID: {}", tournoiId);
        }
    }

    @Override
    public void retirerEquipe(Long tournoiId, Equipe equipe) {
        Tournoi tournoi = entityManager.find(Tournoi.class, tournoiId);
        if (tournoi != null) {
            tournoi.getEquipes().remove(equipe);
            entityManager.merge(tournoi);
            LOGGER.info("Équipe retirée du tournoi avec l'ID: {}", tournoiId);
        } else {
            LOGGER.warn("Tentative de retrait d'une équipe d'un tournoi inexistant avec l'ID: {}", tournoiId);
        }
    }

    @Override
    public int calculerdureeEstimeeTournoi(Long tournoiId) {
        Tournoi tournoi = entityManager.find(Tournoi.class, tournoiId);
        if (tournoi != null) {
            int nombreEquipes = tournoi.getEquipes().size();
            Jeu jeu = tournoi.getJeu();
            int dureeMoyenneMatch = jeu.getDureeMoyenneMatch();
            int tempsPauseEntreMatchs = tournoi.getTempsPauseEntreMatchs();

            int dureeEstimee = (nombreEquipes * dureeMoyenneMatch) + tempsPauseEntreMatchs;
            LOGGER.info("Durée estimée calculée pour le tournoi avec l'ID {}: {} minutes", tournoiId, dureeEstimee);
            return dureeEstimee;
        } else {
            LOGGER.warn("Tentative de calcul de la durée estimée pour un tournoi inexistant avec l'ID: {}", tournoiId);
            return 0;
        }
    }
}
