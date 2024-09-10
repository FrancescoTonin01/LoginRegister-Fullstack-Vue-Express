<template>
    <div class="home-container">
      <div class="hero-section">
        <h1 class="hero-title">{{ $t('title') }}</h1>
        <p class="hero-subtitle">{{ $t('subtitle') }}</p>
      </div>

      <div class="feature-grid">
        <router-link 
          v-for="feature in visibleFeatures" 
          :key="feature.title" 
          :to="feature.route" 
          class="feature-card"
        >
          <div class="feature-icon">
            <i :class="feature.icon"></i>
          </div>
          <h3>{{ $t(feature.title) }}</h3>
          <p>{{ $t(feature.description) }}</p>
        </router-link>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useI18n } from 'vue-i18n';
  import { useRouter } from 'vue-router';
  import { useAuth } from './components/auth'; // Assicurati che il percorso sia corretto
  import { computed } from 'vue';
  
  const { t } = useI18n();
  const router = useRouter();
  const { isLoggedIn } = useAuth();
  
  const allFeatures = [
    { icon: 'fas fa-user-plus', title: 'register', description: 'registerDescription', route: '/register' },
    { icon: 'fas fa-sign-in-alt', title: 'login', description: 'loginDescription', route: '/login' },
    { icon: 'fas fa-tachometer-alt', title: 'dashboard', description: 'dashboardDescription', route: '/dashboard' },
    { icon: 'fas fa-question-circle', title: 'helpCenter', description: 'helpCenterDescription', route: '/helpcenter' },
  ];
  
  const visibleFeatures = computed(() => {
    return allFeatures.filter(feature => {
      if (isLoggedIn.value) {
        return !['register', 'login'].includes(feature.title);
      }
      return feature.title !== 'dashboard';
    });
  });
  
  </script>
  
  
  <style scoped>
  .home-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
  }

  .hero-section {
    text-align: center;
    margin-bottom: 3rem;
    padding: 2rem;
    background: linear-gradient(135deg, #4CAF50, #45a049);
    color: white;
    border-radius: 15px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  }

  .hero-title {
    font-size: 2.5rem;
    margin-bottom: 0.5rem;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  }

  .hero-subtitle {
    font-size: 1.2rem;
    opacity: 0.9;
  }

  .feature-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 1.5rem;
  }

  .feature-card {
    background-color: white;
    border-radius: 12px;
    padding: 1.5rem;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    text-decoration: none;
    color: inherit;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    position: relative;
    overflow: hidden;
  }

  .feature-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 3px;
    background: linear-gradient(90deg, #4CAF50, #45a049);
    transform: scaleX(0);
    transition: transform 0.3s ease;
  }

  .feature-card:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  }

  .feature-card:hover::before {
    transform: scaleX(1);
  }

  .feature-icon {
    width: 60px;
    height: 60px;
    background-color: #e8f5e9;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
    transition: all 0.3s ease;
  }

  .feature-card:hover .feature-icon {
    background-color: #4CAF50;
  }

  .feature-icon i {
    font-size: 1.5rem;
    color: #4CAF50;
    transition: all 0.3s ease;
  }

  .feature-card:hover .feature-icon i {
    color: white;
  }

  .feature-card h3 {
    font-size: 1.2rem;
    color: #333;
    margin-bottom: 0.5rem;
    transition: color 0.3s ease;
  }

  .feature-card:hover h3 {
    color: #4CAF50;
  }

  .feature-card p {
    font-size: 0.9rem;
    color: #666;
    transition: color 0.3s ease;
  }

  .feature-card:hover p {
    color: #45a049;
  }
  </style>