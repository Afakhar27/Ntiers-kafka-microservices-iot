Write-Host "=== Démarrage des Microservices IoT ==="
Write-Host "Assurez-vous que Kafka est lancé sur localhost:9092 (ou via Docker)"
Write-Host "Appuyez sur une touche pour lancer le build et les services..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

# 1. Build du projet parent (si existe) ou des modules
Write-Host ">>> Construction des projets (Maven)..."
mvn clean install -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Error "Le build Maven a échoué."
    exit $LASTEXITCODE
}

# Fonction pour démarrer un service dans une nouvelle fenêtre
function Start-ServiceWindow {
    param(
        [string]$JarPath,
        [string]$Title,
        [int]$Port
    )
    if (-not (Test-Path $JarPath)) {
        Write-Warning "Fichier JAR introuvable : $JarPath"
        return
    }
    Write-Host "Lancement de $Title..."
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "& { $host.ui.RawUI.WindowTitle = '$Title'; java -jar '$JarPath' }"
}

# Chemins relatifs vers les JARs (à adapter selon votre structure exacte target/)
$EurekaJar = ".\eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar"
$GatewayJar = ".\api-gateway\target\api-gateway-0.0.1-SNAPSHOT.jar"
$IngestionJar = ".\ingestion-service\target\ingestion-service-0.0.1-SNAPSHOT.jar"
$ProcessingJar = ".\processing-service\target\processing-service-0.0.1-SNAPSHOT.jar"
$SimulatorJar = ".\sensor-simulator\target\sensor-simulator-0.0.1-SNAPSHOT.jar"

# 2. Démarrage Eureka
Start-ServiceWindow -JarPath $EurekaJar -Title "1-Eureka-Server" -Port 8761
Start-Sleep -Seconds 10 # Attendre qu'Eureka démarre

# 3. Démarrage Gateway
Start-ServiceWindow -JarPath $GatewayJar -Title "2-API-Gateway" -Port 8080

# 4. Démarrage Services Métier
Start-ServiceWindow -JarPath $IngestionJar -Title "3-Ingestion-Service" -Port 0
Start-ServiceWindow -JarPath $ProcessingJar -Title "4-Processing-Service" -Port 0

# 5. Démarrage Simulateur (après quelques secondes)
Write-Host "Attente du démarrage des services..."
Start-Sleep -Seconds 15
Start-ServiceWindow -JarPath $SimulatorJar -Title "5-Sensor-Simulator" -Port 8081

Write-Host "Tous les services ont été lancés dans des fenêtres séparées."
Write-Host "Vérifiez Eureka sur: http://localhost:8761"
