<route lang="yaml">
meta:
  title: 403
</route>

<script setup>
</script>

<template>
  <div class="graphic-container">
    <!-- 图形区 -->
    <div class="graphic">
      <div class="error-code">4</div>
      <div class="error-code">0</div>
      <div class="error-code">3</div>
    </div>

    <!-- 文案区 -->
    <h1 class="title">页面封锁在数字宇宙</h1>
    <p class="subtitle">您未被授予访问此星系的通行证</p>
  </div>
</template>

<style scoped>
.graphic-container {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: radial-gradient(circle, #f8f9fa 0%, #e9ecef 100%);
}

.graphic {
  position: relative;
  display: flex;
  gap: 2rem;
  margin-bottom: 3rem;
}

.error-code {
  font-size: 8rem;
  font-weight: 800;
  color: #2d3436;
  opacity: 0.9;
}

.title {
  font-size: 2rem;
  color: #2d3436;
  margin-bottom: 1rem;
}

.subtitle {
  color: #636e72;
  margin-bottom: 3rem;
}

.home-btn {
  padding: 1rem 2rem;
  background: #2d3436;
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: transform 0.3s ease;

  &:hover {
    transform: translateY(-3px);
    background: #3f474b;
  }
}

@keyframes pulse {
  0% {
    transform: scale(0.95);
  }

  50% {
    transform: scale(1.15);
  }

  100% {
    transform: scale(0.95);
  }
}
</style>
