// main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router.js'; // Assicurati che il percorso sia corretto
import i18n from './i18n';

const app = createApp(App);

app.use(router);
app.use(i18n);

app.mount('#app');