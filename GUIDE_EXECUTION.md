# Guide d'Exécution Complet

Ce guide propose deux méthodes pour lancer le projet :
1. **Option Docker (Recommandée)** : Lance tout en une commande.
2. **Option Locale (TP Strict)** : Lance chaque service manuellement (respecte la contrainte "sans docker" de la partie 1).

---

## méthode 1 : Lancement avec Docker Compose (Facile)

C'est la méthode la plus rapide pour voir le projet tourner.

1. **Lancer** (depuis la racine) :
   ```powershell
   docker-compose -f docker-compose-full.yml up --build -d
   ```
   *Cela compile tous les microservices et lance Kafka, Eureka, Gateway, etc.*

2. **Accéder aux Services** :
   - **Eureka Dashboard** : [http://localhost:8761](http://localhost:8761)
   - **Simulateur** : [http://localhost:8081](http://localhost:8081)
   - **API Gateway** : [http://localhost:8080](http://localhost:8080)
   - **Kafka App (Partie 1)** : Disponible sur le port **8085** ([http://localhost:8085/api/messages](http://localhost:8085)).

3. **Vérifier les logs** :
   ```powershell
   # Voir les logs du service de traitement
   docker logs -f processing-service
   
   # Voir les logs du simulateur
   docker logs -f sensor-simulator
   ```

4. **Arrêter** :
   ```powershell
   docker-compose -f docker-compose-full.yml down
   ```

---

## Méthode 2 : Lancement Local Manuel (Respect Consignes TP)

Cette méthode ne nécessite pas Docker (sauf éventuellement pour Kafka si vous le souhaitez) et lance les services via Java/Maven.

## Étape 1 : Démarrer Kafka
Le projet nécessite un broker Kafka accessible sur `localhost:9092`.

### Option A : Avec Docker (Recommandé si permis)
À la racine du projet :
```bash
docker-compose up -d kafka zookeeper
```

### Option B : Installation Locale (Mode KRaft - Sans Zookeeper)
Si vous avez téléchargé Kafka (ex: dans `C:\kafka`), ouvrez un terminal :
1. **Formater le stockage** (une seule fois) :
   ```powershell
   .\bin\windows\kafka-storage.bat format -t <UUID_GENERE> -c .\config\kraft\server.properties
   ```
2. **Démarrer le serveur** :
   ```powershell
   .\bin\windows\kafka-server-start.bat .\config\kraft\server.properties
   ```

---

## Étape 2 : Lancer la Partie 1 (Application Kafka Simple)
*Dossier : `kafka/kafka-app`*

Cette partie teste les producteurs/consommateurs simples et polymorphiques.

1. **Compiler et Lancer** :
   ```powershell
   cd kafka/kafka-app
   mvn spring-boot:run
   ```
2. **Vérifier** :
   - Ouvrez un autre terminal pour envoyer des requêtes :
   - **Message Simple** :
     ```bash
     curl -X POST -d "Hello World" http://localhost:8080/api/messages
     ```
     -> *Vérifiez les logs de l'application : "Message reçu: Hello World"*
   - **Événement JSON** :
     ```bash
     curl -X POST -H "Content-Type: application/json" -d '{"eventId":"UUID","timestamp":"2026-02-07T10:00:00Z","message":"Test JSON"}' http://localhost:8080/api/events/notification
     ```
   - **Événement Polymorphe (User)** :
     ```bash
     curl -X POST -H "Content-Type: application/json" -d '{"eventId":"UUID","timestamp":"NOW","username":"Alice"}' http://localhost:8080/api/events/user-registered
     ```
     -> *Log attendu : ">>> Processing User Registration: Alice"*

---

## Étape 3 : Lancer la Partie 2 (Microservices IoT)
*Dossier : `microservices/`*

Cette partie lance l'architecture complète : Eureka, Gateway, Ingestion, Processing, Simulateur.

### Méthode Automatique (Script PowerShell)
1. Ouvrez un terminal PowerShell dans le dossier `microservices`.
2. Exécutez le script : (Assurez-vous que Kafka tourne déjà)
   ```powershell
   .\start-microservices.ps1
   ```
   Ce script va :
   - Compiler tous les services (`mvn clean install`).
   - Lancer Eureka, Gateway, Ingestion, Processing et le Simulateur dans des fenêtres séparées.

### Méthode Manuelle
Si le script ne fonctionne pas, lancez chaque service dans un terminal distinct (dans l'ordre) :
1. **Eureka Server** : `cd microservices/eureka-server && mvn spring-boot:run`
2. **API Gateway** : `cd microservices/api-gateway && mvn spring-boot:run`
3. **Ingestion Service** : `cd microservices/ingestion-service && mvn spring-boot:run`
4. **Processing Service** : `cd microservices/processing-service && mvn spring-boot:run`
5. **Sensor Simulator** : `cd microservices/sensor-simulator && mvn spring-boot:run`

---

## Étape 4 : Vérifier que "ça marche bien" (Validation)

Une fois tout démarré, effectuez les vérifications suivantes :

### 1. Tableau de bord Eureka
- Accédez à [http://localhost:8761](http://localhost:8761).
- **Attendu** : Vous devez voir apparaître dans "Instances currently registered with Eureka" :
  - `API-GATEWAY`
  - `INGESTION-SERVICE`
  - `PROCESSING-SERVICE`

### 2. Flux de données (Logs)
Regardez les fenêtres de logs des différents services :

- **Sensor Simulator** : Doit afficher régulièrement :
  `Donnée envoyée (http://localhost:8080/api/ingestion/data): 22.5`
  *(Notez qu'il envoie à la Gateway sur le port 8080)*

- **API Gateway** : Si le niveau de log est DEBUG/INFO, vous verrez passer la requête routée vers Ingestion.

- **Ingestion Service** : Doit afficher :
  `Published to Kafka: SensorData{id='TEMP-001', temp=22.5}`

- **Processing Service** (Le Consommateur final) : Doit afficher :
  `Processing received: SensorData{id='TEMP-001', temp=22.5}`
  
  **Test de l'Alerte** : Le simulateur génère des températures entre 15 et 25°C.
  - Quand la température dépasse **24.0°C**, le Processing Service doit afficher en ROUGE/ERREUR :
    `ALERTE CRITIQUE : Température élevée détectée sur le capteur TEMP-001 : 24.x°C`

### 3. Test Graphique (Si Frontend disponible)
Si vous avez lancé le frontend (dossier `frontend` via `npm start`), accédez à [http://localhost:3000](http://localhost:3000) et vérifiez le dashboard IoT.

---

## Résolution de problèmes courants
- **Connection Refused** : Kafka ou Eureka n'est pas démarré. Vérifiez `localhost:9092` et `localhost:8761`.
- **Service introuvable (Gateway)** : Attendez 1-2 minutes que les services s'enregistrent dans Eureka.
- **Erreur de build** : Vérifiez votre version de Java (`java -version` doit être >= 17).
