# Projet Microservices : Kafka & Spring Cloud

Ce dépôt contient la solution complète pour le travail pratique divisé en deux parties.

## Structure du Projet

### Partie 1 : Application de Messagerie avec Spring Boot et Kafka (KRaft)
Dossier : `Partie1-Kafka/`

*   **Code Source** : `kafka-app/` (Projet Maven Spring Boot)
    *   Contient l'implémentation des Producteurs/Consommateurs, sérialisation JSON, et gestion polymorphique d'événements.
*   **Documentation** :
    *   `GUIDE_INSTALLATION_KAFKA.md` : Instructions pas-à-pas pour installer et lancer Kafka en mode KRaft.
    *   `RAPPORT_PARTIE_1.md` : Réponses aux questions théoriques et détails d'implémentation.

### Partie 2 : Architecture Microservices IoT avec Spring Cloud
Dossier : `Partie2-Microservices/`

*   **Code Source** : 5 Projets Maven distincts
    *   `eureka-server` : Serveur de découverte de services.
    *   `api-gateway` : Passerelle API (Spring Cloud Gateway) avec routage dynamique.
    *   `sensor-simulator` : Générateur de données IoT simulées.
    *   `ingestion-service` : Service de réception et publication Kafka.
    *   `processing-service` : Service de traitement critique et consommation Kafka.
*   **Documentation** :
    *   `RAPPORT_PARTIE_2.md` : Diagramme d'architecture, analyse des flux, et justification des choix techniques (Kafka vs REST).

## Comment utiliser ce projet

1.  **Démarrer Kafka** : Suivez le guide dans `Partie1-Kafka/GUIDE_INSTALLATION_KAFKA.md`.
2.  **Partie 1** : Ouvrez `Partie1-Kafka/kafka-app` et lancez `KafkaAppApplication`.
3.  **Partie 2** :
    *   Assurez-vous que Kafka tourne.
    *   Lancez les projets dans l'ordre : Eureka -> Gateway -> Services Applicatifs -> Simulateur.

## Note sur le Rapport PDF
Les rapports sont fournis au format Markdown (`.md`). Vous pouvez les convertir en PDF en utilisant la fonctionnalité d'impression de votre éditeur ou une extension Markdown-to-PDF.
