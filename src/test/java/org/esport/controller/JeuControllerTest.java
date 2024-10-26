package org.esport.controller;

import org.esport.model.Jeu;
import org.esport.service.interfaces.JeuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JeuControllerTest {

    @Mock
    private JeuService jeuService;

    @InjectMocks
    private JeuController jeuController;

    @BeforeEach
    void setUp() {
        // No need to call MockitoAnnotations.openMocks(this) when using
        // @ExtendWith(MockitoExtension.class)
    }

    @Test
    void creerJeu_success() {
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(jeuService.creerJeu(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuController.creerJeu("Test Game", 5, 30);

        assertNotNull(result);
        assertEquals("Test Game", result.getNom());
        assertEquals(5, result.getDifficulte());
        assertEquals(30, result.getDureeMoyenneMatch());
        verify(jeuService, times(1)).creerJeu(any(Jeu.class));
    }

    @Test
    void modifierJeu_success() {
        Long id = 1L;
        Jeu jeu = new Jeu("Updated Game", 7, 45);
        when(jeuService.obtenirJeu(id)).thenReturn(Optional.of(new Jeu()));
        when(jeuService.modifierJeu(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuController.modifierJeu(id, "Updated Game", 7, 45);

        assertNotNull(result);
        assertEquals("Updated Game", result.getNom());
        assertEquals(7, result.getDifficulte());
        assertEquals(45, result.getDureeMoyenneMatch());
        verify(jeuService, times(1)).obtenirJeu(id);
        verify(jeuService, times(1)).modifierJeu(any(Jeu.class));
    }

    @Test
    void supprimerJeu_success() {
        Long id = 1L;
        doNothing().when(jeuService).supprimerJeu(id);

        jeuController.supprimerJeu(id);

        verify(jeuService, times(1)).supprimerJeu(id);
    }

    @Test
    void obtenirJeu_success() {
        Long id = 1L;
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(jeuService.obtenirJeu(id)).thenReturn(Optional.of(jeu));

        Optional<Jeu> result = jeuController.obtenirJeu(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getNom());
        verify(jeuService, times(1)).obtenirJeu(id);
    }

    @Test
    void obtenirTousJeux_success() {
        List<Jeu> jeux = Arrays.asList(
                new Jeu("Game 1", 3, 20),
                new Jeu("Game 2", 7, 40));
        when(jeuService.obtenirTousJeux()).thenReturn(jeux);

        List<Jeu> result = jeuController.obtenirTousJeux();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jeuService, times(1)).obtenirTousJeux();
    }
}
