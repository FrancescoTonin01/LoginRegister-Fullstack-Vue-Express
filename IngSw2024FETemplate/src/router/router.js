import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '../Dashboard.vue';
import Register from '../Register.vue';
import AuthView from '../AuthView.vue';
import Home from '../Home.vue';
import Login from '../Login.vue';
import HelpCenter from '../HelpCenter.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/AuthView',
    name: 'AuthView',
    component: AuthView,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/helpcenter',
    name: 'HelpCenter',
    component: HelpCenter
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});


export default router;
