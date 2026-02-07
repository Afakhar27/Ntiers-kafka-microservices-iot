import React, { useState } from "react";
import { api } from "../api";
import { Box, TextField, Button, Typography, Alert } from "@mui/material";

export default function MessageSender() {
  const [message, setMessage] = useState("");
  const [feedback, setFeedback] = useState("");
  const [loading, setLoading] = useState(false);

  const sendMessage = async () => {
    setLoading(true);
    setFeedback("");
    try {
      await api.post("/api/messages", { message });
      setFeedback("Message envoyé !");
      setMessage("");
    } catch (e) {
      setFeedback("Erreur lors de l'envoi");
    }
    setLoading(false);
  };

  return (
    <Box sx={{ mb: 2 }}>
      <Typography variant="h6">Envoyer un message texte</Typography>
      <TextField
        label="Message"
        value={message}
        onChange={e => setMessage(e.target.value)}
        fullWidth
        sx={{ my: 1 }}
      />
      <Button variant="contained" onClick={sendMessage} disabled={loading || !message}>
        Envoyer
      </Button>
      {feedback && <Alert sx={{ mt: 1 }} severity={feedback.startsWith("Erreur") ? "error" : "success"}>{feedback}</Alert>}
    </Box>
  );
}
