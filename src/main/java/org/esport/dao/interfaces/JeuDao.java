package org.esport.dao.interfaces;

import org.esport.model.Jeu;
import java.util.List;
import java.util.Optional;

public interface JeuDao {
    Jeu creer(Jeu jeu);

    Jeu modifier(Jeu jeu);

    void supprimer(Long id);

    Optional<Jeu> trouverParId(Long id);

    List<Jeu> trouverTous();
}
