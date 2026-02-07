import React from "react";

export default function Home() {
  return (
    <div>
      <h2>Bienvenue sur le projet Microservices IoT</h2>
      <p>
        Ce frontend permet de tester et visualiser l'architecture distribuée (Kafka, Spring Cloud, IoT).
      </p>
      <img src="/architecture.svg" alt="Diagramme d'architecture" style={{maxWidth: 600}} />
      <ul>
        <li>Messagerie Kafka : envoi et réception de messages</li>
        <li>Dashboard IoT : visualisation des températures et alertes</li>
        <li>Statut des services : état des microservices</li>
        <li>Rapport : explications et guide</li>
      </ul>
    </div>
  );
}
