import React from "react";
import MessageSender from "../components/MessageSender";
import NotificationEventForm from "../components/NotificationEventForm";
import PolymorphicEventForm from "../components/PolymorphicEventForm";
import ReceivedMessages from "../components/ReceivedMessages";
import { Box, Typography } from "@mui/material";

export default function MessagingDemo() {
  return (
    <Box>
      <Typography variant="h4" sx={{ mb: 2 }}>Démo Messagerie Kafka</Typography>
      <MessageSender />
      <NotificationEventForm />
      <PolymorphicEventForm />
      <ReceivedMessages />
    </Box>
  );
}
