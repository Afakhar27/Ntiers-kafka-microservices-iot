import React, { useState } from "react";
import { api } from "../api";
import { Box, TextField, Button, Typography, Alert } from "@mui/material";

export default function NotificationEventForm() {
  const [event, setEvent] = useState({
    eventId: "",
    timestamp: "",
    message: ""
  });
  const [feedback, setFeedback] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = e => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  const sendEvent = async () => {
    setLoading(true);
    setFeedback("");
    try {
      await api.post("/api/events/notification", event);
      setFeedback("Notification envoyée !");
      setEvent({ eventId: "", timestamp: "", message: "" });
    } catch (e) {
      setFeedback("Erreur lors de l'envoi");
    }
    setLoading(false);
  };

  return (
    <Box sx={{ mb: 2 }}>
      <Typography variant="h6">Envoyer une NotificationEvent (JSON)</Typography>
      <TextField label="eventId (UUID)" name="eventId" value={event.eventId} onChange={handleChange} fullWidth sx={{ my: 1 }} />
      <TextField label="timestamp (ISO)" name="timestamp" value={event.timestamp} onChange={handleChange} fullWidth sx={{ my: 1 }} />
      <TextField label="message" name="message" value={event.message} onChange={handleChange} fullWidth sx={{ my: 1 }} />
      <Button variant="contained" onClick={sendEvent} disabled={loading || !event.eventId || !event.timestamp || !event.message}>
        Envoyer
      </Button>
      {feedback && <Alert sx={{ mt: 1 }} severity={feedback.startsWith("Erreur") ? "error" : "success"}>{feedback}</Alert>}
    </Box>
  );
}
