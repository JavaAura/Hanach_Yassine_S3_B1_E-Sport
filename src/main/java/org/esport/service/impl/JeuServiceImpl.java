package org.esport.service.impl;

import org.esport.dao.interfaces.JeuDao;
import org.esport.model.Jeu;
import org.esport.service.interfaces.JeuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class JeuServiceImpl implements JeuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JeuServiceImpl.class);

    private final JeuDao jeuDao;

    public JeuServiceImpl(JeuDao jeuDao) {
        this.jeuDao = jeuDao;
    }

    @Override
    public Jeu creerJeu(Jeu jeu) {
        LOGGER.info("Création d'un nouveau jeu");
        return jeuDao.creer(jeu);
    }

    @Override
    public Jeu modifierJeu(Jeu jeu) {
        LOGGER.info("Modification du jeu avec l'ID: {}", jeu.getId());
        return jeuDao.modifier(jeu);
    }

    @Override
    public void supprimerJeu(Long id) {
        LOGGER.info("Suppression du jeu avec l'ID: {}", id);
        jeuDao.supprimer(id);
    }

    @Override
    public Optional<Jeu> obtenirJeu(Long id) {
        LOGGER.info("Recherche du jeu avec l'ID: {}", id);
        return jeuDao.trouverParId(id);
    }

    @Override
    public List<Jeu> obtenirTousJeux() {
        LOGGER.info("Récupération de tous les jeux");
        return jeuDao.trouverTous();
    }
}
