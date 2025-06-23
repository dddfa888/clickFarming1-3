<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal-content">
      <img
        v-if="locale === 'zh'"
        src="../assets/img/mercado_promo.jpg"
        class="modal-image"
      />
      <img
        v-else
        src="../assets/img/edqpdm4z34c737vtlhg8.jpg"
        class="modal-image"
      />
    </div>
    <button class="modal-close" @click="close">×</button>
  </div>
</template>

<script setup>
import { ref, defineExpose } from "vue";
import { useLangStore } from "../store/useLangStore";
import { storeToRefs } from "pinia";

const visible = ref(false);
const show = () => (visible.value = true);
const close = () => (visible.value = false);
const langStore = useLangStore();
const { locale } = storeToRefs(langStore);

defineExpose({ show });
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

@keyframes fadeIn {
  from {
    transform: scale(0.9);
    opacity: 0;
  }

  to {
    transform: scale(1);
    opacity: 1;
  }
}

.modal-content {
  position: relative;
  max-width: 90%;
  border-radius: 10px;
  overflow: hidden;
  animation: fadeIn 0.3s ease-in-out;
}

.modal-image {
  display: block;
  width: 100%;
  height: auto;
  border-radius: 10px;
}

.modal-close {
  position: absolute;
  bottom: 70px;
  left: 50%;
  transform: translateX(-50%);
  background: #000;
  color: white;
  font-size: 20px;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  border: none;
  cursor: pointer;
}
</style>
