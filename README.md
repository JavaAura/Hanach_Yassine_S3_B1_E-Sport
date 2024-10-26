# Gestion de Tournois E-Sport

## Description du Projet

Ce projet est une application de gestion pour organiser et suivre des tournois de jeux vidéo e-sport. Elle permet de gérer les joueurs, les équipes et les tournois.

## Fonctionnalités Principales

1. Gestion des Joueurs

   - Inscription, modification et suppression de joueurs
   - Affichage des informations d'un joueur ou de tous les joueurs

2. Gestion des Équipes

   - Création et modification d'équipes
   - Ajout et retrait de joueurs dans une équipe
   - Affichage des informations d'une équipe ou de toutes les équipes

3. Gestion des Tournois

   - Création et modification de tournois
   - Ajout et retrait d'équipes dans un tournoi
   - Affichage des informations d'un tournoi ou de tous les tournois
   - Calcul de la durée estimée d'un tournoi

4. Gestion des Jeux
   - Création, modification et suppression de jeux
   - Affichage des informations d'un jeu ou de tous les jeux

## Comment Exécuter le Projet

### Utilisation du fichier JAR

1. Assurez-vous d'avoir Java 8 ou une version supérieure installée sur votre machine.
2. Téléchargez le fichier `Naoufal_Labrihmi_S3_B1_E-Sport-1.0-SNAPSHOT.jar` depuis le dépôt.
3. Ouvrez un terminal et naviguez jusqu'au dossier contenant le fichier JAR.
4. Exécutez la commande suivante :
   ```
   java -jar Naoufal_Labrihmi_S3_B1_E-Sport-1.0-SNAPSHOT.jar
   ```

### Cloner et Exécuter le Projet

1. Clonez le dépôt :
   ```
   git clone https://github.com/JavaAura/Naoufal_Labrihmi_S3_B1_E-Sport.git
   ```
2. Naviguez dans le dossier du projet :
   ```
   cd Naoufal_Labrihmi_S3_B1_E-Sport
   ```
3. Compilez le projet avec Maven :
   ```
   mvn clean package
   ```
4. Exécutez le fichier JAR généré :
   ```
   java -jar target/Naoufal_Labrihmi_S3_B1_E-Sport-1.0-SNAPSHOT.jar
   ```

## Structure du Projet

Le projet suit une architecture en couches :

- Modèle : Entités JPA (Joueur, Equipe, Tournoi, Jeu)
- DAO : Couche d'accès aux données utilisant JPA/Hibernate
- Service : Logique métier
- Présentation : Interface console pour interagir avec l'application

## Technologies Utilisées

- Java 8
- Spring Core pour l'IoC et la DI (configuration XML)
- JPA et Hibernate pour la persistance des données
- H2 comme base de données embarquée
- Maven pour la gestion des dépendances
- JUnit et Mockito pour les tests unitaires
- SLF4J pour le logging

## Gestion de Projet

- Git pour le contrôle de version
- JIRA pour la gestion de projet Scrum : [Lien JIRA](https://naoufallabrihmi.atlassian.net/jira/software/projects/ES/boards/2)

## Diagramme UML

Le diagramme UML du projet est disponible dans le fichier `UML.mdj` à la racine du projet.

## Auteur

Naoufal Labrihmi
