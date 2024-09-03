// main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router.js'; // Assicurati che il percorso sia corretto

const app = createApp(App);
app.use(router);
app.mount('#app');