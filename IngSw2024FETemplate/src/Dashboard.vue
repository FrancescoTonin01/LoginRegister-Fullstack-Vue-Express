<template>
  <div class="dashboard-container">
    <h2>Benvenuto, {{ user.username }}</h2>
    <p>I tuoi dati:</p>
    <ul>
      <li>Username: {{ user.username }}</li>
    </ul>

    <button @click="editMode = !editMode">
      {{ editMode ? 'Annulla' : 'Modifica dati' }}
    </button>

    <div v-if="editMode">
      <form @submit.prevent="updateUser">
        <div class="form-group">
          <label for="newUsername">Nuovo Username:</label>
          <input type="text" id="newUsername" v-model="newUsername" />
        </div>
        <div class="form-group">
          <label for="newPassword">Nuova Password:</label>
          <input type="password" id="newPassword" v-model="newPassword" />
        </div>
        <button type="submit">Salva modifiche</button>
        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      </form>
    </div>
    <div v-if="successMessage" class="success">{{ successMessage }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';

const user = ref(JSON.parse(localStorage.getItem('user') || '{}'));
const oldusername = ref(user.value.username);
const newUsername = ref(user.value.username); // Inizializza con il valore corrente
const newPassword = ref('');
const errorMessage = ref('');
const successMessage = ref('');
const editMode = ref(false); // Modalità modifica inizialmente disattivata

const updateUser = async () => {
  try {
    const response = await axios.put('/api/update-user', {
      username: newUsername.value,
      password: newPassword.value,
      oldUsername: oldusername.value
    });

    if (response.data) {
      successMessage.value = 'Dati aggiornati con successo.';
      errorMessage.value = '';
      editMode.value = false; // Esci dalla modalità modifica
      // Aggiorna l'utente nel localStorage
      user.value.username = newUsername.value;
      localStorage.setItem('user', JSON.stringify(user.value));
    } else {
      errorMessage.value = 'Errore durante l\'aggiornamento dei dati.';
      successMessage.value = '';
    }
  } catch (error) {
    console.error(error);
    errorMessage.value = 'Errore durante l\'aggiornamento dei dati.';
    successMessage.value = '';
  }
};
</script>

<style scoped>
.dashboard-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.error {
  color: red;
  margin-top: 10px;
}

.success {
  color: green;
  margin-top: 10px;
}
</style>
