<template>
    <div class="auth-container">
      <h2 class="title">{{ $t('title') }}</h2>
      <div class="auth-content">
        <div class="register-section">
          <h3>{{ $t('newUser') }}</h3>
          <p class="register-description">{{ $t('registerDescription') }}</p>
          <button class="register-button" @click="goToRegister">{{ $t('register') }}</button>
        </div>
        <div class="divider"></div>
        <div class="login-section">
          <Login class="login-wrapper" />
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useRouter } from 'vue-router';
  import Login from './Login.vue';

  const router = useRouter();

  const goToRegister = () => {
    router.push('/register');
  };
  </script>
  
  <style scoped>
  .auth-container {
    max-width: 1000px; /* Aumentato per dare più spazio */
    margin: 2rem auto;
    padding: 2rem;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  }
  
  .title {
    font-size: 2rem;
    color: #00A86B;
    margin-bottom: 1.5rem;
    text-align: center;
  }
  
  .auth-content {
    display: flex;
    justify-content: space-between;
    align-items: stretch;
  }
  
  .register-section {
    flex: 0 0 35%; /* Ridotto per dare più spazio al login */
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 2rem;
    border-radius: 8px;
  }
  
  .register-section h3 {
    font-size: 1.5rem;
    color: #4CAF50;
    margin-bottom: 1rem;
    text-align: center;
  }
  
  .register-description {
    text-align: center;
    margin-bottom: 1.5rem;
    color: #4a4a4a;
  }
  
  .register-button {
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 12px 24px;
    font-size: 1rem;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
  }
  
  .register-button:hover {
    background-color: #008f5b;
    transform: translateY(-2px);
  }
  
  .register-button:active {
    transform: translateY(0);
  }
  
  .divider {
    width: 1px;
    background-color: #e0e0e0;
    margin: 0 2rem;
  }
  
  .login-section {
    flex: 1; /* Prende tutto lo spazio rimanente */
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 2rem;
  }
  
  .login-wrapper {
    width: 100%; /* Occupa tutta la larghezza disponibile */
    max-width: 600px; /* Aumenta questa dimensione per un login più largo */
  }
  
  @media (max-width: 768px) {
    .auth-content {
      flex-direction: column;
    }
    
    .register-section, .login-section {
      width: 100%;
    }
    
    .divider {
      width: 100%;
      height: 1px;
      margin: 2rem 0;
    }
  }
  </style>