<template>
  <div v-if="user" class="dashboard-container">
    <div class="welcome-banner">
      <h1 class="welcome-title">{{ $t('welcome') }}, {{ user.username }}!</h1>
      <p class="welcome-subtitle">{{ $t('dashboardDescription') }}</p>
    </div>

    <div class="user-info-card">
      <h2 class="card-title">{{ $t('yourData') }}</h2>
      <ul class="user-info-list">
        <li><strong>{{ $t('username') }}:</strong> {{ user.username }}</li>
        <li><strong>{{ $t('email') }}:</strong> {{ user.email }}</li>
        <li><strong>{{ $t('birthdate') }}:</strong> {{ formatDate(user.birthdate) }}</li>
        <li><strong>{{ $t('gender') }}:</strong> {{ $t(user.gender) }}</li>
      </ul>
      <button @click="toggleEditMode" class="edit-button">
        {{ editMode ? $t('cancel') : $t('editData') }}
      </button>
    </div>

    <transition name="slide-fade">
      <div v-if="editMode" class="edit-form-card">
        <h2 class="card-title">{{ $t('editYourData') }}</h2>
        <form @submit.prevent="updateUser" class="edit-form">
          <div class="form-group">
            <label for="newUsername">{{ $t('newUsername') }}:</label>
            <input type="text" id="newUsername" v-model="newUsername" :placeholder="$t('usernamePlaceholder')" />
          </div>
          <div class="form-group">
            <label for="currentPassword">{{ $t('currentPassword') }}:</label>
            <div class="password-input">
              <input :type="showCurrentPassword ? 'text' : 'password'" id="currentPassword" v-model="currentPassword" :placeholder="$t('currentPasswordPlaceholder')" required />
              <button type="button" @click="togglePasswordVisibility('current')" class="toggle-password">
                {{ showCurrentPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
          </div>
          <div class="form-group">
            <label for="newPassword">{{ $t('newPassword') }}:</label>
            <div class="password-input">
              <input :type="showNewPassword ? 'text' : 'password'" id="newPassword" v-model="newPassword" :placeholder="$t('newPasswordPlaceholder')" @blur="validatePassword" />
              <button type="button" @click="togglePasswordVisibility('new')" class="toggle-password">
                {{ showNewPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
            <span v-if="passwordError" class="error-text">{{ passwordError }}</span>
          </div>
          <div class="form-group">
            <label for="newBirthdate">{{ $t('newBirthdate') }}:</label>
            <input type="date" id="newBirthdate" v-model="newBirthdate" :placeholder="$t('birthdatePlaceholder')" />
          </div>
          <div class="form-group">
            <label for="newGender">{{ $t('newGender') }}:</label>
            <select id="newGender" v-model="newGender">
              <option value="">{{ $t('genderSelect') }}</option>
              <option value="male">{{ $t('male') }}</option>
              <option value="female">{{ $t('female') }}</option>
              <option value="other">{{ $t('other') }}</option>
            </select>
          </div>
          <button type="submit" class="submit-button" :disabled="!isFormValid">{{ $t('saveChanges') }}</button>
        </form>
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import axios from 'axios';
import { useAuth, UserData } from '@/components/auth';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const { getUser } = useAuth();
const user = ref<UserData | null>(getUser());

const newUsername = ref(user.value?.username || '');
const currentPassword = ref('');
const newPassword = ref('');
const newBirthdate = ref(user.value?.birthdate || '');
const newGender = ref(user.value?.gender || '');
const errorMessage = ref('');
const passwordError = ref('');
const editMode = ref(false);
const showCurrentPassword = ref(false);
const showNewPassword = ref(false);

const validatePassword = () => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$&-_*])(?=.*[0-9]).{8,}$/;
  if (newPassword.value && !passwordRegex.test(newPassword.value)) {
    passwordError.value = t('invalidPassword');
  } else {
    passwordError.value = '';
  }
};

const togglePasswordVisibility = (type: 'current' | 'new') => {
  if (type === 'current') {
    showCurrentPassword.value = !showCurrentPassword.value;
  } else {
    showNewPassword.value = !showNewPassword.value;
  }
};

const isFormValid = computed(() => {
  return currentPassword.value && !passwordError.value;
});

const formatDate = (dateString: string) => {
  const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

const toggleEditMode = () => {
  editMode.value = !editMode.value;
  if (!editMode.value) {
    // Reset form fields when cancelling
    newUsername.value = user.value?.username || '';
    currentPassword.value = '';
    newPassword.value = '';
    newBirthdate.value = user.value?.birthdate || '';
    newGender.value = user.value?.gender || '';
    errorMessage.value = '';
    passwordError.value = '';
  }
};

const updateUser = async () => {
  errorMessage.value = '';
  if (!isFormValid.value) return;

  try {
    const response = await axios.put('/api/update-user', {
      username: newUsername.value,
      email: user.value?.email,
      currentPassword: currentPassword.value,
      newPassword: newPassword.value,
      birthdate: newBirthdate.value,
      gender: newGender.value,
    });

    if (response.data) {
      if (user.value) {
        user.value = {
          ...user.value,
          username: newUsername.value,
          birthdate: newBirthdate.value,
          gender: newGender.value,
        };
        localStorage.setItem('user', JSON.stringify(user.value));
      }
      editMode.value = false;
      alert(t('updateSuccess'));
    }
  } catch (error) {
    console.error(t('updateError'), error);
    if (error.response) {
      errorMessage.value = `${t('error')} ${error.response.status}: ${error.response.data.message || t('unknownError')}`;
    } else if (error.request) {
      errorMessage.value = t('noServerResponse');
    } else {
      errorMessage.value = t('requestConfigError');
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f8f9fa;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.welcome-banner {
  background-color: #4CAF50;
  color: white;
  padding: 2rem;
  border-radius: 12px;
  margin-bottom: 2rem;
  text-align: center;
}

.welcome-title {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.welcome-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
}

.user-info-card, .edit-form-card {
  background-color: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.card-title {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1rem;
  border-bottom: 2px solid #4CAF50;
  padding-bottom: 0.5rem;
}

.user-info-list {
  list-style-type: none;
  padding: 0;
}

.user-info-list li {
  margin-bottom: 0.8rem;
  font-size: 1.1rem;
}

.edit-button, .submit-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  font-size: 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 1rem;
}

.edit-button:hover, .submit-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #333;
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

.error-text, .error-message {
  color: #d32f2f;
  font-size: 0.9rem;
  margin-top: 0.5rem;
}

.submit-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-20px);
  opacity: 0;
}

.edit-button, .submit-button {
  transition: all 0.3s ease;
}

.edit-button:hover, .submit-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.edit-button:active, .submit-button:active {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  75% { transform: translateX(5px); }
}

.error-message {
  animation: shake 0.5s ease-in-out;
}
</style>
