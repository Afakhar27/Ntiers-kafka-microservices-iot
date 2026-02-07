# Partie 1 : Objectif 1 - Installation et Configuration de Kafka en mode KRaft

Compte tenu des restrictions de cet environnement, je ne peux pas télécharger et lancer le serveur Kafka binaire pour vous. Cependant, voici le guide complet pour réaliser l'Objectif 1 sur votre machine locale.

## 1. Téléchargement et Initialisation

**Téléchargement :**
Téléchargez la dernière version binaire de Kafka (comportant Scala 2.13 de préférence) depuis [kafka.apache.org/downloads](https://kafka.apache.org/downloads).
*Exemple : kafka_2.13-3.9.0.tgz*

**Justification de la version :**
Nous choisissons une version récente (3.3+) car le mode KRaft est marqué comme "production ready" à partir de ces versions (la dépendance à ZooKeeper est dépréciée).

**Extraction :**
```bash
tar -xzf kafka_2.13-3.9.0.tgz
cd kafka_2.13-3.9.0
```

**Génération de l'UUID du Cluster :**
```bash
# Commande Linux/Mac
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
echo $KAFKA_CLUSTER_ID

# Commande Windows
bin\windows\kafka-storage.bat random-uuid
```
*Copiez cet UUID pour l'étape suivante.*

**Rôle de l'UUID :**
Dans KRaft, l'UUID identifie de manière unique le cluster. Il assure que les brokers et les contrôleurs appartiennent au même groupe logique et empêche, par exemple, qu'un broker ne rejoigne par erreur un autre cluster si les répertoires de logs sont mal configurés.

## 2. Configuration (server.properties)

Éditez le fichier `config/kraft/server.properties`.

**Propriétés critiques :**

*   `process.roles=broker,controller` : Indique que ce nœud agit à la fois comme un broker (stockage des données) et un contrôleur (gestion des métadonnées du cluster). C'est idéal pour le développement local.
*   `node.id=1` : Identifiant unique de ce nœud dans le cluster.
*   `controller.quorum.voters=1@localhost:9093` : Liste des nœuds votants pour le quorum du contrôleur. Format `{id}@{host}:{port}`. Ici, le nœud 1 vote pour lui-même.
*   `listeners=PLAINTEXT://:9092,CONTROLLER://:9093` : Définit les ports d'écoute. Le port 9092 est pour les clients (producers/consumers), le 9093 pour la communication interne du plan de contrôle (Raft).

## 3. Formatage et Démarrage

**Formatage du répertoire de logs :**
Cette étape initialise les structures de données sur le disque. Remplacez `<uuid>` par celui généré à l'étape 1.
```bash
# Linux/Mac
bin/kafka-storage.sh format -t <uuid> -c config/kraft/server.properties

# Windows
bin\windows\kafka-storage.bat format -t <uuid> -c config\kraft\server.properties
```

**Démarrage du Serveur :**
```bash
# Linux/Mac
bin/kafka-server-start.sh config/kraft/server.properties

# Windows
bin\windows\kafka-server-start.bat config\kraft\server.properties
```

**Vérification :**
Vous pouvez vérifier le démarrage en regardant les logs (absence d'erreurs Java stackstrace) ou en vérifiant que le port 9092 est ouvert :
```bash
netstat -an | grep 9092
```

## 4. Test Rapide avec Topics

**Création du topic `simple-messages` :**
```bash
# Linux/Mac
bin/kafka-topics.sh --create --topic simple-messages --bootstrap-server localhost:9092

# Windows
bin\windows\kafka-topics.bat --create --topic simple-messages --bootstrap-server localhost:9092
```

**Vérification de la liste :**
```bash
# Linux/Mac
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# Windows
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```
Vous devriez voir `simple-messages` s'afficher.
