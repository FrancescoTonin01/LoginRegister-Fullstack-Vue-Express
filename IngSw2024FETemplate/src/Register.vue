<template>
  <div class="register-container">
    <h2 class="register-title">{{ $t('registration') }}</h2>
    <form @submit.prevent="handleRegister" class="register-form">
      <div class="form-group">
        <label for="username">{{ $t('username') }}</label>
        <input type="text" id="username" v-model="username" :placeholder="$t('usernamePlaceholder')" required />
      </div>
      <div class="form-group">
        <label for="email">{{ $t('email') }}</label>
        <input type="email" id="email" v-model="email" :placeholder="$t('emailPlaceholder')" required @blur="validateEmail" />
        <span v-if="emailError" class="error-text">{{ emailError }}</span>
      </div>
      <div class="form-group">
        <label for="password">{{ $t('password') }}</label>
        <div class="password-input">
          <input :type="showPassword ? 'text' : 'password'" id="password" v-model="password" :placeholder="$t('passwordPlaceholder')" required @blur="validatePassword" />
          <button type="button" @click="togglePasswordVisibility" class="toggle-password">
            {{ showPassword ? '👁️' : '👁️‍🗨️' }}
          </button>
        </div>
        <span v-if="passwordError" class="error-text">{{ passwordError }}</span>
      </div>
      <div class="form-group">
        <label for="birthdate">{{ $t('birthdate') }}</label>
        <input type="date" id="birthdate" v-model="birthdate" :placeholder="$t('birthdatePlaceholder')" required />
      </div>
      <div class="form-group">
        <label for="gender">{{ $t('gender') }}</label>
        <select id="gender" v-model="gender" required>
          <option value="">{{ $t('genderSelect') }}</option>
          <option value="male">{{ $t('male') }}</option>
          <option value="female">{{ $t('female') }}</option>
          <option value="other">{{ $t('other') }}</option>
        </select>
      </div>
      <div class="form-group">
        <button type="submit" class="register-button" :disabled="!isFormValid">{{ $t('registerButton') }}</button>
      </div>
    </form>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { login } from './components/auth';

const router = useRouter();
const { t } = useI18n();

const username = ref('');
const email = ref('');
const password = ref('');
const birthdate = ref('');
const gender = ref('');
const errorMessage = ref('');
const emailError = ref('');
const passwordError = ref('');
const showPassword = ref(false);

const validateEmail = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    emailError.value = t('invalidEmail');
  } else {
    emailError.value = '';
  }
};

const validatePassword = () => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$&-_*])(?=.*[0-9]).{8,}$/;
  if (!passwordRegex.test(password.value)) {
    passwordError.value = t('invalidPassword');
  } else {
    passwordError.value = '';
  }
};

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const isFormValid = computed(() => {
  return username.value && email.value && password.value && birthdate.value && gender.value && !emailError.value && !passwordError.value;
});

const handleRegister = async () => {
  if (!isFormValid.value) return;

  try {
    const response = await axios.post('/api/register', {
      username: username.value,
      email: email.value,
      password: password.value,
      birthdate: birthdate.value,
      gender: gender.value,
    });

    if (response.status === 201) {
      login(response.data);
      router.push('/'); // Reindirizzamento alla home se registrazione effettuata con successo
    }
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      if (error.response) {
        switch (error.response.status) {
          case 400:
            errorMessage.value = t('registerErrors.badRequest', { error: error.response.data });
            break;
          case 409:
            errorMessage.value = t('registerErrors.conflict', { error: error.response.data });
            break;
          case 500:
            errorMessage.value = t('registerErrors.serverError');
            break;
          default:
            errorMessage.value = t('registerErrors.genericError');
        }
      } else {
        errorMessage.value = t('registerErrors.networkError');
      }
    } else {
      errorMessage.value = t('registerErrors.unknownError');
    }
  }
};
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.register-title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 2rem;
  text-align: center;
}

.register-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-size: 0.9rem;
  color: #555;
  margin-bottom: 0.5rem;

}

.form-group input,
.form-group select {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

.register-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 1rem;
  font-size: 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.register-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.error-message {
  color: #d32f2f;
  font-size: 0.9rem;
  margin-top: 1rem;
  text-align: center;
  padding: 0.5rem;
  background-color: #ffebee;
  border-radius: 4px;
}

.password-input {
  position: relative;
}

.toggle-password {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
}

.error-text {
  color: #d32f2f;
  font-size: 0.8rem;
  margin-top: 0.3rem;
}

/* Aggiungere questo stile per il pulsante disabilitato */
.register-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>
