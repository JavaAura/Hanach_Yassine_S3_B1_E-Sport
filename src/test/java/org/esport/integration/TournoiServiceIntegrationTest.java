package org.esport.integration;

import org.esport.dao.interfaces.TournoiDao;
import org.esport.dao.interfaces.EquipeDao;
import org.esport.model.Jeu;
import org.esport.model.Tournoi;
import org.esport.model.Equipe;
import org.esport.service.impl.TournoiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.esport.model.enums.TournoiStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TournoiServiceIntegrationTest {

    @Mock
    private TournoiDao tournoiDao;

    @Mock
    private EquipeDao equipeDao;

    @InjectMocks
    private TournoiServiceImpl tournoiService;

    private Tournoi testTournoi;
    private Jeu testJeu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testJeu = new Jeu("Test Game", 2, 30);
        testTournoi = new Tournoi("Test Tournoi", testJeu, LocalDate.now(), LocalDate.now().plusDays(7), 1000);
        testTournoi.setTempsPauseEntreMatchs(15);
        testTournoi.setTempsCeremonie(60);

        Equipe equipe1 = new Equipe("Equipe 1");
        Equipe equipe2 = new Equipe("Equipe 2");
        testTournoi.setEquipes(Arrays.asList(equipe1, equipe2));
    }

    @Test
    void testCreerTournoi() {
        when(tournoiDao.creer(any(Tournoi.class))).thenReturn(testTournoi);
        Tournoi createdTournoi = tournoiService.creerTournoi(testTournoi);
        assertNotNull(createdTournoi);
        assertEquals(testTournoi.getTitre(), createdTournoi.getTitre());
        verify(tournoiDao, times(1)).creer(any(Tournoi.class));
    }

    @Test
    void testModifierTournoi() {
        when(tournoiDao.modifier(any(Tournoi.class))).thenReturn(testTournoi);
        testTournoi.setTitre("Updated Tournoi");
        Tournoi updatedTournoi = tournoiService.modifierTournoi(testTournoi);
        assertNotNull(updatedTournoi);
        assertEquals("Updated Tournoi", updatedTournoi.getTitre());
        verify(tournoiDao, times(1)).modifier(any(Tournoi.class));
    }

    @Test
    void testSupprimerTournoi() {
        doNothing().when(tournoiDao).supprimer(anyLong());
        tournoiService.supprimerTournoi(1L);
        verify(tournoiDao, times(1)).supprimer(1L);
    }

    @Test
    void testObtenirTournoi() {
        Long tournoiId = 1L;
        when(tournoiDao.trouverParIdAvecEquipes(tournoiId)).thenReturn(Optional.of(testTournoi));

        Optional<Tournoi> retrievedTournoi = tournoiService.obtenirTournoi(tournoiId);

        assertTrue(retrievedTournoi.isPresent(), "Le tournoi devrait être présent");
        assertEquals(testTournoi.getTitre(), retrievedTournoi.get().getTitre(),
                "Les titres des tournois devraient correspondre");
        verify(tournoiDao, times(1)).trouverParIdAvecEquipes(tournoiId);
    }

    @Test
    void testObtenirTousTournois() {
        when(tournoiDao.trouverTous()).thenReturn(Arrays.asList(testTournoi));
        List<Tournoi> tournois = tournoiService.obtenirTousTournois();
        assertEquals(1, tournois.size());
        assertEquals(testTournoi.getTitre(), tournois.get(0).getTitre());
    }

    @Test
    void testAjouterEquipe() {
        Equipe equipe = new Equipe("Test Equipe");
        when(equipeDao.trouverParId(anyLong())).thenReturn(Optional.of(equipe));
        doNothing().when(tournoiDao).ajouterEquipe(anyLong(), any(Equipe.class));
        tournoiService.ajouterEquipe(1L, 2L);
        verify(tournoiDao, times(1)).ajouterEquipe(eq(1L), any(Equipe.class));
    }

    @Test
    void testRetirerEquipe() {
        Equipe equipe = new Equipe("Test Equipe");
        when(equipeDao.trouverParId(anyLong())).thenReturn(Optional.of(equipe));
        doNothing().when(tournoiDao).retirerEquipe(anyLong(), any(Equipe.class));
        tournoiService.retirerEquipe(1L, 2L);
        verify(tournoiDao, times(1)).retirerEquipe(eq(1L), any(Equipe.class));
    }

    @Test
    void testCalculerdureeEstimeeTournoi() {
        when(tournoiDao.calculerdureeEstimeeTournoi(anyLong())).thenReturn(135);
        int dureeEstimee = tournoiService.calculerdureeEstimeeTournoi(1L);
        assertEquals(135, dureeEstimee);
    }

    @Test
    void testModifierStatutTournoi() {
        doNothing().when(tournoiDao).modifierStatut(anyLong(), any(TournoiStatus.class));
        tournoiService.modifierStatutTournoi(1L, TournoiStatus.EN_COURS);
        verify(tournoiDao, times(1)).modifierStatut(1L, TournoiStatus.EN_COURS);
    }
}
