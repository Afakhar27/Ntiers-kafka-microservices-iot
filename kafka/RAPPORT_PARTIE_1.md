# Rapport Partie 1 : Application de Messagerie avec Spring Boot et Kafka (KRaft)

## Objectif 1 : Installation et Configuration de Kafka en mode KRaft

### 1. Version et UUID
**Version choisie :** Apache Kafka 3.9.0 (Scala 2.13). Cette version est stable et supporte pleinement le mode KRaft (Kafka Raft Metadata mode) en production, permettant de s'affranchir de ZooKeeper.

**Génération UUID :**
La commande utilisée est `kafka-storage random-uuid`.
L'UUID (Universally Unique Identifier) sert à identifier de manière unique le cluster Kafka. Dans le protocole KRaft, il assure que les fichiers de logs stockés sur le disque appartiennent bien au cluster configuré. Si un broker tente de démarrer avec des logs d'un autre cluster UUID, il échouera, protégeant ainsi l'intégrité des données.

### 2. Configuration server.properties
*   **`process.roles`** : Définit le rôle du nœud. En mode `broker,controller`, le nœud agit à la fois comme un nœud de données qui stocke les messages et comme un contrôleur qui gère les métadonnées du cluster.
*   **`node.id`** : L'identifiant numérique unique du nœud dans le quorum. Chaque broker/contrôleur doit avoir un ID distinct.
*   **`controller.quorum.voters`** : Définit la liste des nœuds qui participent au consensus Raft pour élire le contrôleur actif. Format `id@host:port`.
*   **`listeners`** : Définit les sockets d'écoute. En mode KRaft combiné, on sépare généralement le trafic client (`PLAINTEXT://:9092`) du trafic de contrôle interne (`CONTROLLER://:9093`).

### 3. Formatage et Démarrage
**Séquence de commandes :**
1.  `bin/kafka-storage.sh format -t <UUID> -c config/kraft/server.properties`
2.  `bin/kafka-server-start.sh config/kraft/server.properties`

**Vérification :**
On peut vérifier que le broker est prêt en utilisant la commande `bin/kafka-topics.sh --list --bootstrap-server localhost:9092`. Si elle répond sans erreur (même une liste vide), le broker est opérationnel.

## Objectif 2 : Communication Simple

### 1. Dépendances Maven Minimales
Pour intégrer Spring Web et Spring for Apache Kafka :
*   `spring-boot-starter-web`
*   `spring-kafka`

### 4. Procédure de Test
Pour valider la chaîne de communication :
1.  Démarrer le serveur Kafka localement.
2.  Démarrer l'application Spring Boot (`KakaAppApplication`).
3.  Utiliser un client HTTP (cURL ou Postman) pour envoyer une requête POST :
    `curl -X POST -H "Content-Type: text/plain" -d "Hello Kafka" http://localhost:8080/api/messages`
4.  Observer la console de l'application Spring Boot. Le log du `SimpleConsumer` doit afficher : "Message reçu: Hello Kafka...".

## Objectif 3 : Objets Java et JSON

### 2. Configuration JSON et Trusted Packages
Propriétés ajoutées dans `KafkaConfig` ou `application.properties` :
*   `value-serializer` : `org.springframework.kafka.support.serializer.JsonSerializer`
*   `value-deserializer` : `org.springframework.kafka.support.serializer.JsonDeserializer`

**Trusted Packages (`spring.json.trusted.packages`):**
Par défaut, pour des raisons de sécurité, le `JsonDeserializer` refuse de désérialiser des classes arbitraires pour éviter des attaques par injection de gadgets. Cette propriété définit une liste blanche des packages Java autorisés à être instanciés lors de la désérialisation. C'est crucial pour accepter nos DTOs (`com.example.kafkaapp.dto`).

## Objectif 4 : Polymorphisme

### 2. Gestion du Polymorphisme
Nous avons utilisé une approche hybride robuste :
1.  **Annotations Jackson** (`@JsonTypeInfo`, `@JsonSubTypes`) sur la classe `BaseEvent`. Cela ajoute un champ `type` ("user" ou "order") dans le payload JSON, rendant le message auto-descriptif.
2.  **Configuration Spring Kafka** (`JsonSerializer.TYPE_MAPPINGS`). Spring Kafka ajoute automatiquement un en-tête `__TypeId__` au message Kafka contenant le nom complet de la classe Java.
    *   La propriété `spring.json.type.mapping` permet de mapper un alias court (ex: "user") vers la classe Java complète (`...UserRegisteredEvent`). Cela permet au `JsonDeserializer` de savoir quelle classe concrète instancier avant même de parser tout le JSON, basant le choix sur les headers.

**Mécanisme sous-jacent :**
Le producteur ajoute un header `__TypeId__` avec la classe de l'objet envoyé. Le consommateur lit ce header, vérifie s'il est dans les packages de confiance ou dans les mappings configurés, et instancie la classe correspondante. Cela assure que `listen(BaseEvent event)` reçoit bien un objet `UserRegisteredEvent` ou `OrderCreatedEvent` au runtime.
