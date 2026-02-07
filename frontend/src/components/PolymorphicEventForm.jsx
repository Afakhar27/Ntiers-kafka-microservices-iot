import React, { useState } from "react";
import { api } from "../api";
import { Box, TextField, Button, Typography, Alert, MenuItem } from "@mui/material";

export default function PolymorphicEventForm() {
  const [type, setType] = useState("user");
  const [fields, setFields] = useState({ username: "", orderId: "", amount: "" });
  const [feedback, setFeedback] = useState("");
  const [loading, setLoading] = useState(false);

  const handleTypeChange = e => {
    setType(e.target.value);
    setFields({ username: "", orderId: "", amount: "" });
  };

  const handleFieldChange = e => {
    setFields({ ...fields, [e.target.name]: e.target.value });
  };

  const sendEvent = async () => {
    setLoading(true);
    setFeedback("");
    try {
      if (type === "user") {
        await api.post("/api/events/user-registered", {
          type: "user",
          username: fields.username 
        });
      } else {
        await api.post("/api/events/order-created", { 
          type: "order",
          orderId: fields.orderId, 
          amount: fields.amount 
        });
      }
      setFeedback("Evénement envoyé !");
      setFields({ username: "", orderId: "", amount: "" });
    } catch (e) {
      setFeedback("Erreur lors de l'envoi");
    }
    setLoading(false);
  };

  return (
    <Box sx={{ mb: 2 }}>
      <Typography variant="h6">Envoyer un événement polymorphe</Typography>
      <TextField select label="Type d'événement" value={type} onChange={handleTypeChange} fullWidth sx={{ my: 1 }}>
        <MenuItem value="user">UserRegisteredEvent</MenuItem>
        <MenuItem value="order">OrderCreatedEvent</MenuItem>
      </TextField>
      {type === "user" ? (
        <TextField label="username" name="username" value={fields.username} onChange={handleFieldChange} fullWidth sx={{ my: 1 }} />
      ) : (
        <>
          <TextField label="orderId" name="orderId" value={fields.orderId} onChange={handleFieldChange} fullWidth sx={{ my: 1 }} />
          <TextField label="amount" name="amount" value={fields.amount} onChange={handleFieldChange} fullWidth sx={{ my: 1 }} />
        </>
      )}
      <Button variant="contained" onClick={sendEvent} disabled={loading || (type === "user" ? !fields.username : !fields.orderId || !fields.amount)}>
        Envoyer
      </Button>
      {feedback && <Alert sx={{ mt: 1 }} severity={feedback.startsWith("Erreur") ? "error" : "success"}>{feedback}</Alert>}
    </Box>
  );
}
