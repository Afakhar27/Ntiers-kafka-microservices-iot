FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
# Port aléatoire dans le code mais on peut fixer pour docker ou laisser dynamique
ENTRYPOINT ["java", "-jar", "app.jar"]
