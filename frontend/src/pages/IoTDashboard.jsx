import React, { useEffect, useState } from "react";
import { api } from "../api";
import { Box, Typography, Alert } from "@mui/material";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";

export default function IoTDashboard() {
  const [data, setData] = useState([]);
  const [alerts, setAlerts] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await api.get("/api/ingestion/data");
        setData(res.data.readings || []);
        setAlerts(res.data.alerts || []);
        setError("");
      } catch (e) {
        setError("Erreur lors de la récupération des données IoT");
      }
    };
    fetchData();
    const interval = setInterval(fetchData, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <Box>
      <Typography variant="h4" sx={{ mb: 2 }}>Dashboard IoT</Typography>
      {error && <Alert severity="error">{error}</Alert>}
      <Typography variant="h6">Températures reçues</Typography>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={data} margin={{ top: 20, right: 30, left: 0, bottom: 0 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="timestamp" tickFormatter={v => v && v.substring(11,19)} />
          <YAxis domain={[10, 30]} />
          <Tooltip />
          <Line type="monotone" dataKey="temperature" stroke="#1976d2" dot={false} />
        </LineChart>
      </ResponsiveContainer>
      <Typography variant="h6" sx={{ mt: 3 }}>Alertes critiques</Typography>
      <ul>
        {alerts.length === 0 && <li>Aucune alerte</li>}
        {alerts.map((a, i) => (
          <li key={i} style={{ color: "red" }}>
            {a}
          </li>
        ))}
      </ul>
    </Box>
  );
}
