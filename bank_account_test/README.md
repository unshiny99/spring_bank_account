# Bank Account Test pour Exalt

## Installation

### Pré-requis

1. JDK 17
2. Maven (https://maven.apache.org/)

### Lancement

1. Se déplacer dans le répertoire racine du projet : `cd bank_account_test/`
2. Lancer les tests unitaires : `mvn test`
3. Lancer l'application en local `mvn spring-boot:run`
4. Pour tester les différents endpoints d'API fournis, il est possible d'importer la [collection Postman](./Exalt_IT_DOJO.postman_collection.json)

## Outils utilisés

- Maven
- Spring (Boot, Data JPA)
- Sytème de Gestion de Base de Données H2
- Postman

## Remarques

### Implémentations supplémentaires

- API permettant de récupérer la liste de tous les comptes (GET)

### Retour d'expérience

J'ai apprécié travailler sur ce projet from-scratch, utilisant une architecture que je n'avais jamais utilisée jusqu'alors. \
J'y ai passé environ 8 heures. Cela est principalement dû au fait que j'ai eu à me former sur l'architecture hexagonale, ainsi qu'à la réalisation et implémentation des tests.

### Améliorations possibles du projet

Prioritaires :
- Avoir une base de données persistante (qui conserve les données même au redémarrage du serveur)
- Retourner du JSON dans les APIs plutôt que des formats différents et non standardisés
- Avoir une TransactionEntity plutôt qu'une simple liste par compte sauvegardée dans le compte lui même

"Nice to have" :
- Mettre en plac des tests d'intégration
- Implémenter un accès sécurisé à la BDD (Spring Security)
- Mise en place d'une interface utilisateur permettant de consulter les comptes

## Licence

Auteur : Maxime Frémeaux \
Contact : maxime.fremeaux2@gmail.com | (+33)6 27 02 44 68