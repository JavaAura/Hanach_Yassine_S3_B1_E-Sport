package org.esport.dao.interfaces;

import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import java.util.List;
import java.util.Optional;
import org.esport.model.enums.TournoiStatus;

public interface TournoiDao {
    Tournoi creer(Tournoi tournoi);

    Tournoi modifier(Tournoi tournoi);

    void supprimer(Long id);

    Optional<Tournoi> trouverParId(Long id);

    List<Tournoi> trouverTous();

    void ajouterEquipe(Long tournoiId, Equipe equipe);

    void retirerEquipe(Long tournoiId, Equipe equipe);

    int calculerdureeEstimeeTournoi(Long tournoiId);

    Optional<Tournoi> trouverParIdAvecEquipes(Long id);

    void modifierStatut(Long tournoiId, TournoiStatus nouveauStatut);
}
