package org.esport.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.esport.model.enums.TournoiStatus;

@Entity
@Table(name = "tournois")
public class Tournoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String titre;

    @ManyToOne
    @JoinColumn(name = "jeu_id")
    private Jeu jeu;

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    @Min(value = 0)
    private int nombreSpectateurs;

    @ManyToMany
    @JoinTable(name = "tournoi_equipe", joinColumns = @JoinColumn(name = "tournoi_id"), inverseJoinColumns = @JoinColumn(name = "equipe_id"))
    private List<Equipe> equipes = new ArrayList<>();

    @Min(value = 0)
    private int dureeEstimee;

    @Min(value = 0)
    private int tempsPauseEntreMatchs;

    @Min(value = 0)
    private int tempsCeremonie;

    @Enumerated(EnumType.STRING)
    private TournoiStatus statut;

    private int dureeMoyenneMatch;

    @OneToMany(mappedBy = "jeu")
    private List<Tournoi> tournois = new ArrayList<>();

    // Constructors
    public Tournoi() {
    }

    public Tournoi(String titre, Jeu jeu, LocalDate dateDebut, LocalDate dateFin) {
        this.titre = titre;
        this.jeu = jeu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = TournoiStatus.PLANIFIE;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreSpectateurs() {
        return nombreSpectateurs;
    }

    public void setNombreSpectateurs(int nombreSpectateurs) {
        this.nombreSpectateurs = nombreSpectateurs;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public int getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(int dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public int getTempsPauseEntreMatchs() {
        return tempsPauseEntreMatchs;
    }

    public void setTempsPauseEntreMatchs(int tempsPauseEntreMatchs) {
        this.tempsPauseEntreMatchs = tempsPauseEntreMatchs;
    }

    public int getTempsCeremonie() {
        return tempsCeremonie;
    }

    public void setTempsCeremonie(int tempsCeremonie) {
        this.tempsCeremonie = tempsCeremonie;
    }

    public TournoiStatus getStatut() {
        return statut;
    }

    public void setStatut(TournoiStatus statut) {
        this.statut = statut;
    }

    public int calculateEstimatedDuration() {
        int baseEstimation = (equipes.size() * jeu.getDureeMoyenneMatch()) + tempsPauseEntreMatchs;
        return baseEstimation + tempsCeremonie;
    }

}
