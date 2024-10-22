package org.esport.service.interfaces;

import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import java.util.List;
import java.util.Optional;

public interface TournoiService {
    Tournoi creerTournoi(Tournoi tournoi);

    Tournoi modifierTournoi(Tournoi tournoi);

    void supprimerTournoi(Long id);

    Optional<Tournoi> obtenirTournoi(Long id);

    List<Tournoi> obtenirTousTournois();

    void ajouterEquipe(Long tournoiId, Long equipeId);

    void retirerEquipe(Long tournoiId, Long equipeId);

    int obtenirdureeEstimeeTournoi(Long tournoiId);
}
