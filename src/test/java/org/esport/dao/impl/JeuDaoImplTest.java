package org.esport.dao.impl;

import org.esport.model.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JeuDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private JeuDaoImpl jeuDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creer_success() {
        Jeu jeu = new Jeu("Test Game", 5, 30);
        doNothing().when(entityManager).persist(any(Jeu.class));

        Jeu result = jeuDao.creer(jeu);

        assertNotNull(result);
        assertEquals("Test Game", result.getNom());
        verify(entityManager, times(1)).persist(any(Jeu.class));
    }

    @Test
    void modifier_success() {
        Jeu jeu = new Jeu("Updated Game", 7, 45);
        when(entityManager.merge(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuDao.modifier(jeu);

        assertNotNull(result);
        assertEquals("Updated Game", result.getNom());
        verify(entityManager, times(1)).merge(any(Jeu.class));
    }

    @Test
    void supprimer_success() {
        Long id = 1L;
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(entityManager.find(Jeu.class, id)).thenReturn(jeu);
        doNothing().when(entityManager).remove(any(Jeu.class));

        jeuDao.supprimer(id);

        verify(entityManager, times(1)).find(Jeu.class, id);
        verify(entityManager, times(1)).remove(any(Jeu.class));
    }

    @Test
    void trouverParId_success() {
        Long id = 1L;
        Jeu jeu = new Jeu("Test Game", 5, 30);
        when(entityManager.find(Jeu.class, id)).thenReturn(jeu);

        Optional<Jeu> result = jeuDao.trouverParId(id);

        assertTrue(result.isPresent());
        assertEquals("Test Game", result.get().getNom());
        verify(entityManager, times(1)).find(Jeu.class, id);
    }

    @Test
    void trouverTous_success() {
        List<Jeu> jeux = Arrays.asList(
                new Jeu("Game 1", 3, 20),
                new Jeu("Game 2", 7, 40));
        TypedQuery<Jeu> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Jeu.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(jeux);

        List<Jeu> result = jeuDao.trouverTous();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery(anyString(), eq(Jeu.class));
        verify(query, times(1)).getResultList();
    }
}
