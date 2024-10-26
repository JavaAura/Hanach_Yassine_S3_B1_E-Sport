package org.esport.controller;

import org.esport.model.Joueur;
import org.esport.service.interfaces.JoueurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JoueurControllerTest {

    @Mock
    private JoueurService joueurService;

    @InjectMocks
    private JoueurController joueurController;

    @Test
    void creerJoueur_success() {
        Joueur joueur = new Joueur("TestPlayer", 20);
        when(joueurService.inscrireJoueur(any(Joueur.class))).thenReturn(joueur);

        Joueur result = joueurController.inscrireJoueur("TestPlayer", 20);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getPseudo());
        assertEquals(20, result.getAge());
        verify(joueurService, times(1)).inscrireJoueur(any(Joueur.class));
    }

    @Test
    void modifierJoueur_success() {
        Long id = 1L;
        Joueur joueur = new Joueur("UpdatedPlayer", 22);
        when(joueurService.obtenirJoueur(id)).thenReturn(Optional.of(new Joueur()));
        when(joueurService.modifierJoueur(any(Joueur.class))).thenReturn(joueur);

        Joueur result = joueurController.modifierJoueur(id, "UpdatedPlayer", 22);

        assertNotNull(result);
        assertEquals("UpdatedPlayer", result.getPseudo());
        assertEquals(22, result.getAge());
        verify(joueurService, times(1)).obtenirJoueur(id);
        verify(joueurService, times(1)).modifierJoueur(any(Joueur.class));
    }

    @Test
    void supprimerJoueur_success() {
        Long id = 1L;
        doNothing().when(joueurService).supprimerJoueur(id);

        joueurController.supprimerJoueur(id);

        verify(joueurService, times(1)).supprimerJoueur(id);
    }

    @Test
    void obtenirJoueur_success() {
        Long id = 1L;
        Joueur joueur = new Joueur("ExistingPlayer", 25);
        when(joueurService.obtenirJoueur(id)).thenReturn(Optional.of(joueur));

        Optional<Joueur> result = joueurController.obtenirJoueur(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getPseudo());
        assertEquals(25, result.get().getAge());
        verify(joueurService, times(1)).obtenirJoueur(id);
    }

    @Test
    void obtenirTousJoueurs_success() {
        List<Joueur> joueurs = Arrays.asList(
                new Joueur("Player1", 20),
                new Joueur("Player2", 22));
        when(joueurService.obtenirTousJoueurs()).thenReturn(joueurs);

        List<Joueur> result = joueurController.obtenirTousJoueurs();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(joueurService, times(1)).obtenirTousJoueurs();
    }
}
