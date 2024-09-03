import { ref } from 'vue';

const isLoggedIn = ref<boolean>(!!localStorage.getItem('user'));

export const login = (userData: { username: string; password: string }) => {
  localStorage.setItem('user', JSON.stringify(userData));
  isLoggedIn.value = true;
};

export const logout = () => {
  localStorage.removeItem('user');
  isLoggedIn.value = false;
};

export const useAuth = () => {
  return {
    isLoggedIn,
    login,
    logout,
  };
};
