<template>
  <div
    v-if="visible"
    :class="['notify', type]"
    @mouseenter="pauseTimer"
    @mouseleave="resumeTimer"
  >
    <div class="notify-content">
      <strong class="title">{{ title }}</strong>
      <p class="message">{{ message }}</p>
    </div>
    <span class="close-btn" @click="close">×</span>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watchEffect } from "vue";

const props = defineProps({
  title: { type: String, default: "提示" },
  message: { type: String, default: "" },
  duration: { type: Number, default: 3000 },
  type: {
    type: String,
    default: "info", // success, warning, error, info
    validator: (val) => ["success", "warning", "error", "info"].includes(val),
  },
});

const emit = defineEmits(["close"]);

const visible = ref(true);
let timer = null;

const startTimer = () => {
  if (props.duration > 0) {
    timer = setTimeout(() => {
      close();
    }, props.duration);
  }
};

const pauseTimer = () => {
  if (timer) clearTimeout(timer);
};

const resumeTimer = () => {
  startTimer();
};

const close = () => {
  visible.value = false;
  emit("close");
};

onMounted(() => {
  startTimer();
});

onUnmounted(() => {
  if (timer) clearTimeout(timer);
});
</script>

<style scoped>
.notify {
  position: fixed;
  right: 20px;
  top: 20px;
  min-width: 250px;
  padding: 12px 16px;
  border-radius: 4px;
  color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  display: flex;
  align-items: center;
  justify-content: space-between;
  animation: fadeIn 0.3s ease;
  z-index: 999;
}
.notify .title {
  font-weight: bold;
}
.notify .message {
  margin: 4px 0 0;
}
.notify.success {
  background-color: #67c23a;
}
.notify.warning {
  background-color: #e6a23c;
}
.notify.error {
  background-color: #f56c6c;
}
.notify.info {
  background-color: #409eff;
}
.close-btn {
  cursor: pointer;
  font-size: 18px;
  margin-left: 12px;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (min-width: 768px) {
  .notify {
    position: fixed;
    right: 20px;
    top: 20px;
    min-width: 250px;
    padding: 12px 16px;
    border-radius: 4px;
    color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    display: flex;
    align-items: center;
    justify-content: space-between;
    animation: fadeIn 0.3s ease;
  }
  .notify .title {
    font-weight: bold;
  }
  .notify .message {
    margin: 4px 0 0;
  }
  .notify.success {
    background-color: #67c23a;
  }
  .notify.warning {
    background-color: #e6a23c;
  }
  .notify.error {
    background-color: #f56c6c;
  }
  .notify.info {
    background-color: #409eff;
  }
  .close-btn {
    cursor: pointer;
    font-size: 18px;
    margin-left: 12px;
  }
  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
}
</style>
