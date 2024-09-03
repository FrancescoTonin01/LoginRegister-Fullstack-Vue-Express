<template>
    <div class="register-container">
      <h2>Registrazione</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="form-group">
          <button type="submit">Registrati</button>
        </div>
        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      </form>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  import {login} from './components/auth';

  const router = useRouter();
  
  const username = ref('');
  const password = ref('');
  const errorMessage = ref('');
  
  const handleRegister = async () => {
    try {
      const response = await axios.post('/api/register', {
        username: username.value,
        password: password.value,
      });
  
      if (response.data) {
        login(response.data); // Chiama la funzione login da auth
        router.push('/dashboard');
      } else {
        errorMessage.value = 'Registration failed.';
      }
    } catch (error) {
      console.log(error);
      errorMessage.value = 'Registration failed.';
    }
  };
  </script>
  
  <style scoped>
  .register-container {
    max-width: 400px;
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
  </style>
  