package org.esport.service.impl;

import org.esport.dao.interfaces.JoueurDao;
import org.esport.model.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoueurServiceImplTest {

    @Mock
    private JoueurDao joueurDao;

    @InjectMocks
    private JoueurServiceImpl joueurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inscrireJoueur_success() {
        Joueur joueur = new Joueur("TestPlayer", 20);
        when(joueurDao.inscrire(any(Joueur.class))).thenReturn(joueur);

        Joueur result = joueurService.inscrireJoueur(joueur);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getPseudo());
        assertEquals(20, result.getAge());
        verify(joueurDao, times(1)).inscrire(any(Joueur.class));
    }

    @Test
    void modifierJoueur_success() {
        Joueur joueur = new Joueur("UpdatedPlayer", 22);
        when(joueurDao.modifier(any(Joueur.class))).thenReturn(joueur);

        Joueur result = joueurService.modifierJoueur(joueur);

        assertNotNull(result);
        assertEquals("UpdatedPlayer", result.getPseudo());
        assertEquals(22, result.getAge());
        verify(joueurDao, times(1)).modifier(any(Joueur.class));
    }

    @Test
    void supprimerJoueur_success() {
        Long id = 1L;
        doNothing().when(joueurDao).supprimer(id);

        joueurService.supprimerJoueur(id);

        verify(joueurDao, times(1)).supprimer(id);
    }

    @Test
    void obtenirJoueur_success() {
        Long id = 1L;
        Joueur joueur = new Joueur("ExistingPlayer", 25);
        when(joueurDao.trouverParId(id)).thenReturn(Optional.of(joueur));

        Optional<Joueur> result = joueurService.obtenirJoueur(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getPseudo());
        assertEquals(25, result.get().getAge());
        verify(joueurDao, times(1)).trouverParId(id);
    }

    @Test
    void obtenirTousJoueurs_success() {
        List<Joueur> joueurs = Arrays.asList(
            new Joueur("Player1", 20),
            new Joueur("Player2", 22)
        );
        when(joueurDao.trouverTous()).thenReturn(joueurs);

        List<Joueur> result = joueurService.obtenirTousJoueurs();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(joueurDao, times(1)).trouverTous();
    }

    @Test
    void obtenirJoueursParEquipe_success() {
        Long equipeId = 1L;
        List<Joueur> joueurs = Arrays.asList(
            new Joueur("Player1", 20),
            new Joueur("Player2", 22)
        );
        when(joueurDao.trouverParEquipe(equipeId)).thenReturn(joueurs);

        List<Joueur> result = joueurService.obtenirJoueursParEquipe(equipeId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(joueurDao, times(1)).trouverParEquipe(equipeId);
    }

    @Test
    void existeParPseudo_success() {
        String pseudo = "TestPlayer";
        when(joueurDao.existeParPseudo(pseudo)).thenReturn(true);

        boolean result = joueurService.existeParPseudo(pseudo);

        assertTrue(result);
        verify(joueurDao, times(1)).existeParPseudo(pseudo);
    }

    @Test
    void trouverParPseudo_success() {
        String pseudo = "TestPlayer";
        Joueur joueur = new Joueur(pseudo, 25);
        when(joueurDao.trouverParPseudo(pseudo)).thenReturn(Optional.of(joueur));

        Optional<Joueur> result = joueurService.trouverParPseudo(pseudo);

        assertTrue(result.isPresent());
        assertEquals(pseudo, result.get().getPseudo());
        assertEquals(25, result.get().getAge());
        verify(joueurDao, times(1)).trouverParPseudo(pseudo);
    }
}
