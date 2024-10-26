package org.esport.service.impl;

import org.esport.dao.interfaces.JeuDao;
import org.esport.model.Jeu;
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

public class JeuServiceImplTest {

    @Mock
    private JeuDao jeuDao;

    @InjectMocks
    private JeuServiceImpl jeuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creerJeu_success() {
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(jeuDao.creer(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuService.creerJeu(jeu);

        assertNotNull(result);
        assertEquals("Test Game", result.getNom());
        verify(jeuDao, times(1)).creer(any(Jeu.class));
    }

    @Test
    void modifierJeu_success() {
        Jeu jeu = new Jeu("Updated Game", 7, 45);
        when(jeuDao.modifier(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuService.modifierJeu(jeu);

        assertNotNull(result);
        assertEquals("Updated Game", result.getNom());
        verify(jeuDao, times(1)).modifier(any(Jeu.class));
    }

    @Test
    void supprimerJeu_success() {
        Long id = 1L;
        doNothing().when(jeuDao).supprimer(id);

        jeuService.supprimerJeu(id);

        verify(jeuDao, times(1)).supprimer(id);
    }

    @Test
    void obtenirJeu_success() {
        Long id = 1L;
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(jeuDao.trouverParId(id)).thenReturn(Optional.of(jeu));

        Optional<Jeu> result = jeuService.obtenirJeu(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getNom());
        verify(jeuDao, times(1)).trouverParId(id);
    }

    @Test
    void obtenirTousJeux_success() {
        List<Jeu> jeux = Arrays.asList(
                new Jeu("Game 1", 3, 20),
                new Jeu("Game 2", 7, 40));
        when(jeuDao.trouverTous()).thenReturn(jeux);

        List<Jeu> result = jeuService.obtenirTousJeux();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jeuDao, times(1)).trouverTous();
    }
}
