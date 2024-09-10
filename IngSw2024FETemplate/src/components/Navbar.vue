<template>
  <nav>
    <ul>
      <li class="home-link"><router-link to="/">{{ $t('Home') }}</router-link></li>
      <div class="right-links">
        <li v-if="!isLoggedIn"><router-link to="/AuthView">{{ $t('login') }}</router-link></li>
        <li v-if="isLoggedIn"><router-link to="/dashboard">{{ $t('dashboard') }}</router-link></li>
        <li v-if="isLoggedIn"><button @click="handleLogout">{{ $t('logout') }}</button></li>
        <li class="language-selector">
          <button @click="toggleLanguageMenu" class="language-button">
            {{ currentLanguageIcon }} {{ currentLanguage.toUpperCase() }}
          </button>
          <div v-if="showLanguageMenu" class="language-menu">
            <button @click="changeLanguage('it')" :class="{ active: currentLanguage === 'it' }">
              🇮🇹 Italiano
            </button>
            <button @click="changeLanguage('en')" :class="{ active: currentLanguage === 'en' }">
              🇬🇧 English
            </button>
          </div>
        </li>
      </div>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { useAuth } from './auth'; // Assicurati che il percorso sia corretto
import { useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

const { isLoggedIn, logout } = useAuth(); // Destruttura le variabili dal composable
const router = useRouter();
const { locale, t } = useI18n();

const currentLanguage = computed({
  get: () => locale.value,
  set: (value) => { locale.value = value }
});

const showLanguageMenu = ref(false);
const currentLanguageIcon = computed(() => {
  return currentLanguage.value === 'it' ? '🇮🇹' : '🇬🇧';
});

const toggleLanguageMenu = () => {
  showLanguageMenu.value = !showLanguageMenu.value;
};

const changeLanguage = (lang: string) => {
  currentLanguage.value = lang;
  showLanguageMenu.value = false;
};

const handleLogout = () => {
  logout(); // Chiama la funzione di logout dal composable
  router.push('/login'); // Reindirizza alla pagina di login
};

// ... altro codice esistente ...
</script>

<style scoped>
nav {
  background-color: #4CAF50;
  padding: 1rem;
}

nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.right-links {
  display: flex;
  align-items: center;
}

nav ul li {
  margin-left: 1.5rem;
}

nav ul li a, nav ul li button {
  text-decoration: none;
  color: white;
  font-weight: 600;
  padding: 0.5rem 1rem;
  transition: all 0.3s ease;
  position: relative;
}

nav ul li a::after, nav ul li button::after {
  content: '';
  position: absolute;
  width: 0;
  height: 2px;
  bottom: 0;
  left: 50%;
  background-color: white;
  transition: all 0.3s ease;
}

nav ul li a:hover::after, nav ul li button:hover::after {
  width: 100%;
  left: 0;
}

nav ul li button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
}

select {
  padding: 0.5rem;
  border: 1px solid white;
  background-color: #4CAF50;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

select:hover {
  background-color: #45a049;
}

.language-selector {
  position: relative;
}

.language-button {
  background: none;
  border: none;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  padding: 0.5rem 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.language-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #00A86B;
  border-radius: 4px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  overflow: hidden;
  z-index: 10;
}

.language-menu button {
  display: block;
  width: 100%;
  padding: 0.75rem 1rem;
  text-align: left;
  border: none;
  background: none;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.language-menu button:hover,
.language-menu button.active {
  background-color: #009060;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

.language-menu {
  animation: fadeIn 0.3s ease;
}
</style>
