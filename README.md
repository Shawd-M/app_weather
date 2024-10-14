# Application Mobile Android - News App

## Description

Ce projet consiste à créer une **application mobile Android** qui permet aux utilisateurs d'accéder aux **dernières actualités** et de rechercher des articles publiés à l'aide de filtres avancés. L'application utilise l'API **NewsAPI** pour récupérer les actualités en temps réel. Les utilisateurs peuvent afficher les dernières actualités, effectuer des recherches par mot-clé, filtrer par catégorie, langue, pays, et trier les résultats par popularité ou par dates.

## Fonctionnalités

### 1. Page d'Accueil
- Affiche les **dernières actualités** à la une.
- Filtrer les actualités par **pays**.
- Filtrer les actualités par **catégorie** (ex. : politique, technologie, sport).

### 2. Recherche Avancée
- **Recherche par mot-clé** : Permet de trouver des articles correspondant à un ou plusieurs mots-clés spécifiques.
- **Filtrer par langue** : Afficher uniquement les articles dans une langue spécifique.
- **Trier par popularité** : Classement des articles les plus populaires ou les plus consultés.
- **Filtrer par date** : Afficher les articles publiés entre deux dates données.

### 3. Affichage des Articles
- Les articles sont affichés sous forme de **liste**, chaque élément contenant des informations minimales comme le **titre**, un **résumé**, et la **date** de publication.
- **Détail d'un article** : Lorsqu'un utilisateur sélectionne un article dans la liste, une nouvelle page s'ouvre pour afficher les **informations détaillées** de l'article.

## API Utilisée

L'application utilise l'API suivante pour récupérer les données d'actualités en temps réel :
- **[NewsAPI](https://newsapi.org/docs)** : La version gratuite de l'API est suffisante pour les besoins du projet.

### Endpoints Utilisés
- `GET /v2/top-headlines` : Pour afficher les dernières actualités à la une.
- `GET /v2/everything` : Pour rechercher et filtrer les articles.

## Prérequis

Avant de commencer, il est nécessaire d'obtenir une clé API sur [NewsAPI](https://newsapi.org/). Voici comment configurer le projet :

1. **Obtenir une clé API** sur [NewsAPI](https://newsapi.org/register).
2. Ajouter cette clé API dans le fichier de configuration de l'application.

