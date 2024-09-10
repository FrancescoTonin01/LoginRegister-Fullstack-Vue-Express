<template>
    <div class="help-center">
      <h1 class="title">{{ $t('helpCenter') }}</h1>
      <p class="subtitle">{{ $t('helpCenterDescription') }}</p>
      <div class="search-container">
        <input type="text" :placeholder="$t('searchPlaceholder')" class="search-input" />
        <button class="search-button">{{ $t('search') }}</button>
      </div>
      <div class="categories">
        <div class="category" v-for="category in categories" :key="category.id">
          <div class="category-icon">{{ category.icon }}</div>
          <h2>{{ $t(category.nameKey) }}</h2>
          <p>{{ $t(category.descriptionKey) }}</p>
        </div>
      </div>
      <div class="faq">
        <h2>{{ $t('frequentlyAskedQuestions') }}</h2>
        <div class="faq-item" v-for="faq in faqs" :key="faq.id">
          <h3>{{ $t(faq.questionKey) }}</h3>
          <p>{{ $t(faq.answerKey) }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  const categories = [
    { id: 1, nameKey: 'gettingStarted', descriptionKey: 'gettingStartedDesc', icon: '🚀' },
    { id: 2, nameKey: 'account', descriptionKey: 'accountDesc', icon: '👤' },
    { id: 3, nameKey: 'payments', descriptionKey: 'paymentsDesc', icon: '💳' },
    { id: 4, nameKey: 'troubleshooting', descriptionKey: 'troubleshootingDesc', icon: '🔧' },
  ];
  
  const faqs = [
    { id: 1, questionKey: 'changePasswordQuestion', answerKey: 'changePasswordAnswer' },
    { id: 2, questionKey: 'paymentMethodsQuestion', answerKey: 'paymentMethodsAnswer' },
  ];
  </script>
  
  <style scoped>
  .help-center {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
    font-family: 'Arial', sans-serif;
    min-height: 100vh;
  }
  
  .title {
    font-size: 3rem;
    color: #00A86B;
    text-align: center;
    margin-bottom: 0.5rem;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
  }
  
  .subtitle {
    text-align: center;
    color: #2c3e50;
    font-size: 1.2rem;
    margin-bottom: 2rem;
  }
  
  .search-container {
    display: flex;
    justify-content: center;
    margin-bottom: 3rem;
  }
  
  .search-input {
    width: 60%;
    padding: 1rem;
    font-size: 1.2rem;
    border: none;
    border-radius: 30px 0 0 30px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  }
  
  .search-button {
    padding: 1rem 2rem;
    font-size: 1.2rem;
    background-color: #00A86B;
    color: white;
    border: none;
    border-radius: 0 30px 30px 0;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .search-button:hover {
    background-color: #008f5b;
  }
  
  .categories {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 2rem;
    margin-bottom: 3rem;
  }
  
  .category {
    background-color: white;
    padding: 2rem;
    border-radius: 15px;
    box-shadow: 0 10px 20px rgba(0,0,0,0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    border: 2px solid #00A86B;
  }
  
  .category:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(0,0,0,0.2);
  }
  
  .category-icon {
    font-size: 3rem;
    margin-bottom: 1rem;
  }
  
  .category h2 {
    color: #00A86B;
    margin-bottom: 0.5rem;
  }
  
  .category p {
    color: #2c3e50;
  }
  
  .faq {
    background-color: white;
    padding: 2rem;
    border-radius: 15px;
    box-shadow: 0 10px 20px rgba(0,0,0,0.1);
    border: 2px solid #00A86B;
  }
  
  .faq h2 {
    color: #00A86B;
    margin-bottom: 1.5rem;
    text-align: center;
  }
  
  .faq-item {
    margin-bottom: 1.5rem;
    padding-bottom: 1.5rem;
    border-bottom: 1px solid #e6f7e9;
  }
  
  .faq-item:last-child {
    border-bottom: none;
  }
  
  .faq-item h3 {
    color: #00A86B;
    margin-bottom: 0.5rem;
  }
  
  .faq-item p {
    color: #2c3e50;
  }
  
  @media (max-width: 768px) {
    .search-input {
      width: 70%;
    }
    
    .categories {
      grid-template-columns: 1fr;
    }
  }
  </style>