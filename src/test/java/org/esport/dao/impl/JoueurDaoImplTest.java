package org.esport.dao.impl;

import org.esport.model.Joueur;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class JoueurDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private JoueurDaoImpl joueurDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creer_success() {
        Joueur joueur = new Joueur("TestPlayer", 20);
        doNothing().when(entityManager).persist(any(Joueur.class));

        Joueur result = joueurDao.inscrire(joueur);

        assertNotNull(result);
        assertEquals("TestPlayer", result.getPseudo());
        assertEquals(20, result.getAge());
        verify(entityManager, times(1)).persist(any(Joueur.class));
    }

    @Test
    void modifier_success() {
        Joueur joueur = new Joueur("UpdatedPlayer", 22);
        when(entityManager.merge(any(Joueur.class))).thenReturn(joueur);

        Joueur result = joueurDao.modifier(joueur);

        assertNotNull(result);
        assertEquals("UpdatedPlayer", result.getPseudo());
        assertEquals(22, result.getAge());
        verify(entityManager, times(1)).merge(any(Joueur.class));
    }

    @Test
    void supprimer_success() {
        Long id = 1L;
        Joueur joueur = new Joueur("PlayerToDelete", 25);
        when(entityManager.find(Joueur.class, id)).thenReturn(joueur);
        doNothing().when(entityManager).remove(any(Joueur.class));

        joueurDao.supprimer(id);

        verify(entityManager, times(1)).find(Joueur.class, id);
        verify(entityManager, times(1)).remove(any(Joueur.class));
    }

    @Test
    void trouverParId_success() {
        Long id = 1L;
        Joueur joueur = new Joueur("ExistingPlayer", 25);
        when(entityManager.find(Joueur.class, id)).thenReturn(joueur);

        Optional<Joueur> result = joueurDao.trouverParId(id);

        assertTrue(result.isPresent());
        assertEquals("ExistingPlayer", result.get().getPseudo());
        assertEquals(25, result.get().getAge());
        verify(entityManager, times(1)).find(Joueur.class, id);
    }

    @Test
    void trouverTous_success() {
        List<Joueur> joueurs = Arrays.asList(
                new Joueur("Player1", 20),
                new Joueur("Player2", 22));
        TypedQuery<Joueur> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Joueur.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(joueurs);

        List<Joueur> result = joueurDao.trouverTous();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery(anyString(), eq(Joueur.class));
        verify(query, times(1)).getResultList();
    }
}
