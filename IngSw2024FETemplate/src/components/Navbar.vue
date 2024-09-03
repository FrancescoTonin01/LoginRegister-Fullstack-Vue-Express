<template>
  <nav>
    <ul>
      <li v-if="!isLoggedIn"><router-link to="/">Login</router-link></li>
      <li v-if="isLoggedIn"><router-link to="/dashboard">Dashboard</router-link></li>
      <li v-if="isLoggedIn"><button @click="handleLogout">Logout</button></li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { useAuth } from './auth'; // Assicurati che il percorso sia corretto
import { useRouter } from 'vue-router';

const { isLoggedIn, logout } = useAuth(); // Destruttura le variabili dal composable
const router = useRouter();

const handleLogout = () => {
  logout(); // Chiama la funzione di logout dal composable
  router.push('/login'); // Reindirizza alla pagina di login
};
</script>

<style scoped>
nav ul {
  list-style: none;
  padding: 0;
}

nav ul li {
  display: inline;
  margin-right: 10px;
}
</style>
