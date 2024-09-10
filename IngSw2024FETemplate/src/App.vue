<template>
  <div id="app">
    <Navbar/> <!-- Componente Navbar -->
    <router-view /> <!-- Questo è dove verranno visualizzati i componenti delle rotte -->
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'; 

export default {
  name: 'App',
  components: {
    Navbar, // Registra il componente Navbar
  },
};
</script>

<style>
/* Aggiungi eventuali stili globali qui */
body {
  font-family: Arial, sans-serif;
  background-color: #f0f0f0;
  margin: 0;
  padding: 0;
}
</style>
