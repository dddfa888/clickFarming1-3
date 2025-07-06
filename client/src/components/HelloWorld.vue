<template>
  <div class="container">
    <div class="overlay"></div>
    <div class="content">
      <h1>MERCADO ASIA</h1>
      <h2>E-commerce shopping center</h2>
      <p>
        We are 21 years old since the beginning of that adventure in a garage in
        the Saavedra neighborhood, in the city of Buenos Aires. We have come a
        long way, but we are convinced that the best is yet to come.
      </p>
      <div class="buttons">
        <button @click="toLogin">LOGIN</button>
        <button @click="toRegister">REGISTER</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const toLogin = () => {
  router.push({ name: "login" });
};
const toRegister = () => {
  router.push({ name: "register" });
};
</script>

<style scoped>
.container {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background: url("../assets/img/background.jpg") no-repeat center center/cover;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
  font-family: "Segoe UI", sans-serif;
}

.overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 0;
}

.content {
  position: relative;
  z-index: 1;
  max-width: 800px;
  padding: 20px;
}

h1 {
  font-size: 3rem;
  margin-bottom: 2rem;
  font-weight: bold;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 3rem;
  font-weight: normal;
}

p {
  margin-bottom: 3rem;
  font-size: 1.5rem;
  line-height: 1.5;
}

.buttons {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
  gap: 20px;
}

button {
  background-color: #ffe600;
  color: black;
  padding: 0.75rem 2rem;
  border: none;
  font-weight: bold;
  width: 15rem;
  cursor: pointer;
  transition: background 0.3s ease;
}

button:hover {
  background-color: #ffdb00;
}

@media (max-width: 768px) {
  h1 {
    font-size: 1.5rem;
  }
  button {
    text-align: center;
    background-color: #ffe600;
    color: black;
    border: none;
    font-size: 0.7rem;
    width: 7rem;
    transition: background 0.3s ease;
  }

  p {
    font-size: 0.8rem;
  }
}
</style>
