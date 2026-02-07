# Utiliser une image de base avec Java 17
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR compilé (assurez-vous d'avoir exécuté mvn package avant)
COPY target/*.jar app.jar

# Exposer le port par défaut (à adapter selon le service)
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
