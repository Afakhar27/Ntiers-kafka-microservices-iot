import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import MessagingDemo from "./pages/MessagingDemo";
import IoTDashboard from "./pages/IoTDashboard";
import ServiceStatus from "./components/ServiceStatus";
import About from "./pages/About";
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";

function App() {
  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Microservices IoT Demo
          </Typography>
          <Button color="inherit" component={Link} to="/">Accueil</Button>
          <Button color="inherit" component={Link} to="/messaging">Messagerie Kafka</Button>
          <Button color="inherit" component={Link} to="/iot">Dashboard IoT</Button>
          <Button color="inherit" component={Link} to="/status">Statut Services</Button>
          <Button color="inherit" component={Link} to="/about">Rapport</Button>
        </Toolbar>
      </AppBar>
      <Box sx={{ p: 2 }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/messaging" element={<MessagingDemo />} />
          <Route path="/iot" element={<IoTDashboard />} />
          <Route path="/status" element={<ServiceStatus />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </Box>
    </Router>
  );
}

export default App;
