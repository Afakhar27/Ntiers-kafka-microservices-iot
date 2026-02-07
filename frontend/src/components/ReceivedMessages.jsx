import React, { useState, useEffect } from "react";
import { api } from "../api";
import { Box, Typography, Paper, List, ListItem, ListItemText, Button } from "@mui/material";

export default function ReceivedMessages() {
  const [messages, setMessages] = useState([]);

  const fetchMessages = async () => {
    try {
      const response = await api.get("/api/messages");
      setMessages(response.data);
    } catch (e) {
      console.error("Erreur lors de la récupération des messages", e);
    }
  };

  useEffect(() => {
    fetchMessages();
    const interval = setInterval(fetchMessages, 3000); // Poll every 3 seconds
    return () => clearInterval(interval);
  }, []);

  return (
    <Box sx={{ mt: 4 }}>
      <Typography variant="h5" sx={{ mb: 2 }}>Messages Reçus (Consumer)</Typography>
      <Button variant="outlined" onClick={fetchMessages} sx={{ mb: 2 }}>Actualiser</Button>
      <Paper elevation={3} sx={{ p: 2, maxHeight: 300, overflow: "auto" }}>
        {messages.length === 0 ? (
          <Typography color="text.secondary">Aucun message reçu pour le moment...</Typography>
        ) : (
          <List>
            {messages.map((msg, index) => (
              <ListItem key={index} divider>
                <ListItemText primary={msg} />
              </ListItem>
            ))}
          </List>
        )}
      </Paper>
    </Box>
  );
}
