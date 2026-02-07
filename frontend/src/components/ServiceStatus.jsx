import React, { useEffect, useState } from "react";
import { api } from "../api";
import { Box, Typography, Chip, CircularProgress, Grid } from "@mui/material";

const services = [
  { name: "Eureka Server", endpoint: "/eureka/status" },
  { name: "API Gateway", endpoint: "/gateway/status" },
  { name: "Kafka Broker", endpoint: "/kafka/status" },
  { name: "Ingestion Service", endpoint: "/ingestion-service/status" },
  { name: "Processing Service", endpoint: "/processing-service/status" },
  { name: "Sensor Simulator", endpoint: "/sensor-simulator/status" },
];

export default function ServiceStatus() {
  const [status, setStatus] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStatus = async () => {
      setLoading(true);
      const newStatus = {};
      for (const s of services) {
        try {
          const res = await api.get(s.endpoint);
          newStatus[s.name] = res.data.status || "OK";
        } catch {
          newStatus[s.name] = "KO";
        }
      }
      setStatus(newStatus);
      setLoading(false);
    };
    fetchStatus();
    const interval = setInterval(fetchStatus, 10000);
    return () => clearInterval(interval);
  }, []);

  return (
    <Box>
      <Typography variant="h4" sx={{ mb: 2 }}>Statut des services</Typography>
      {loading ? <CircularProgress /> : (
        <Grid container spacing={2}>
          {services.map(s => (
            <Grid item xs={12} sm={6} md={4} key={s.name}>
              <Typography>{s.name}</Typography>
              <Chip
                label={status[s.name] === "OK" ? "OK" : "KO"}
                color={status[s.name] === "OK" ? "success" : "error"}
                sx={{ fontWeight: "bold" }}
              />
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}
