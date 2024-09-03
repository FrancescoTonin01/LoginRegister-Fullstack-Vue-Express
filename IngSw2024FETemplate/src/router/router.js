import { createRouter, createWebHistory } from 'vue-router';
import Login from '../Login.vue';
import Dashboard from '../Dashboard.vue';
import Register from '../Register.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  { path: '/register',
     component: Register },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});


export default router;
