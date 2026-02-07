@echo off
setlocal

echo ========================================================
echo   AUTOMATISATION DU PROJET MICROSERVICES (Windows)
echo ========================================================
echo.
echo ATTENTION : Assurez-vous que KAFKA (Port 9092) est deja lance !
echo.
pause

:MENU
cls
echo ========================================================
echo   MENU PRINCIPAL
echo ========================================================
echo 1. Compiler tous les projets (A faire en premier)
echo 2. Lancer la PARTIE 1 (App Messagerie Kafka Simple)
echo 3. Lancer la PARTIE 2 (Architecture Microservices IoT)
echo 4. Quitter
echo.
set /p choice=Votre choix (1-4) : 

if "%choice%"=="1" goto BUILD
if "%choice%"=="2" goto RUN_PART1
if "%choice%"=="3" goto RUN_PART2
if "%choice%"=="4" goto END
goto MENU

:BUILD
cls
echo ========================================================
echo   COMPILATION DES PROJETS (Maven)
echo ========================================================
echo.
echo [1/6] Compilation de kafka/kafka-app...
cd kafka/kafka-app
call mvn clean package -DskipTests
cd ../..

echo.
echo [2/6] Compilation de microservices/eureka-server...
cd microservices/eureka-server
call mvn clean package -DskipTests
cd ../..

echo.
echo [3/6] Compilation de microservices/api-gateway...
cd microservices/api-gateway
call mvn clean package -DskipTests
cd ../..

echo.
echo [4/6] Compilation de microservices/ingestion-service...
cd microservices/ingestion-service
call mvn clean package -DskipTests
cd ../..

echo.
echo [5/6] Compilation de microservices/processing-service...
cd microservices/processing-service
call mvn clean package -DskipTests
cd ../..

echo.
echo [6/6] Compilation de microservices/sensor-simulator...
cd microservices/sensor-simulator
call mvn clean package -DskipTests
cd ../..

echo.
echo ========================================================
echo   COMPILATION TERMINEE
echo ========================================================
pause
goto MENU

:RUN_PART1
cls
echo ========================================================
echo   LANCEMENT PARTIE 1 : APP KAFKA SIMPLE
echo ========================================================
echo.
echo Lancement de l'application Spring Boot...
cd kafka/kafka-app
start "Partie 1 - Kafka App" java -jar target/kafka-app-0.0.1-SNAPSHOT.jar
cd ../..
echo.
echo Application lancee dans une nouvelle fenetre.
pause
goto MENU

:RUN_PART2
cls
echo ========================================================
echo   LANCEMENT PARTIE 2 : MICROSERVICES IOT
echo ========================================================
echo.
echo 1. Démarrage Eureka Server...
start "1. Eureka Server" java -jar microservices/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar
timeout /t 10

echo.
echo 2. Démarrage API Gateway...
start "2. API Gateway" java -jar microservices/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
timeout /t 5

echo.
echo 3. Démarrage Ingestion Service...
start "3. Ingestion Service" java -jar microservices/ingestion-service/target/ingestion-service-0.0.1-SNAPSHOT.jar

echo.
echo 4. Démarrage Processing Service...
start "4. Processing Service" java -jar microservices/processing-service/target/processing-service-0.0.1-SNAPSHOT.jar
timeout /t 10

echo.
echo 5. Démarrage Sensor Simulator...
start "5. Sensor Simulator" java -jar microservices/sensor-simulator/target/sensor-simulator-0.0.1-SNAPSHOT.jar

echo.
echo ========================================================
echo   TOUS LES MICROSERVICES SONT LANCES
echo ========================================================
echo Vérifiez les fenêtres ouvertes pour voir les logs.
pause
goto MENU

:END
exit
