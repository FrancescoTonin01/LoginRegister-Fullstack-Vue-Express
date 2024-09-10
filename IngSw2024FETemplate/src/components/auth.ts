import { ref } from 'vue';

export interface UserData {
  username: string;
  email: string;
  password: string;
  birthdate: string; // Cambiato da dataNascita a birthdate
  gender: string; // Cambiato da sesso a gender
}

const isLoggedIn = ref<boolean>(!!localStorage.getItem('user'));

export const login = (userData: UserData) => {
  localStorage.setItem('user', JSON.stringify(userData));
  isLoggedIn.value = true;
};

export const logout = () => {
  localStorage.removeItem('user');
  isLoggedIn.value = false;
};

export const getUser = (): UserData | null => {
  const userString = localStorage.getItem('user');
  return userString ? JSON.parse(userString) : null;
};

export const useAuth = () => {
  return {
    isLoggedIn,
    login,
    logout,
    getUser,
  };
};
