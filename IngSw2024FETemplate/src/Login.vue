<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <button type="submit">Login</button>
      </div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </form>
    <div class="register-link">
      Non hai un account? <router-link to="/register">Registrati</router-link>
    </div>
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

const handleLogin = async () => {
  try {
    const response = await axios.post('/api/login', {
      username: username.value,
      password: password.value,
    });

    console.log(response);

    if (response.data) {
      login(response.data); // Chiama la funzione login da auth
      router.push('/dashboard');
    } else {
      errorMessage.value = 'Invalid username or password.';
    }
  } catch (error) {
    console.error(error);
    errorMessage.value = 'Invalid username or password.';
  }
};
</script>

<style scoped>
.login-container {
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

.register-link {
  margin-top: 20px;
  text-align: center;
}
</style>
