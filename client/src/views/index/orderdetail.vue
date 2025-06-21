<template>
  <div class="order-detail-container">
    <div class="order-header">
      <div>
        <p style="font-size: 15px">订单详细信息</p>
        <div class="data-provider">数据提供者 Mercado Libre</div>
      </div>
      <div class="amount-section">
        <div class="amount-display">{{ formatCurrency(order.amount) }}</div>
        <div class="amount-label">剩余(€)</div>
      </div>
    </div>

    <div class="order-summary">
      <img src="../../assets/img/banner1-8QRSYmQj.png" alt="" />
      <div class="status-badge" @click="handlePay">收到</div>
    </div>

    <div class="order-details-grid">
      <div class="detail-item">
        <div class="detail-label">剩余</div>
        <div class="detail-value">{{ formatCurrency(order.remaining) }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">当前水平</div>
        <div class="detail-value">{{ order.currentLevel }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">订单已付款</div>
        <div class="detail-value">{{ order.paymentStatus }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">昨天折扣</div>
        <div class="detail-value">
          {{ formatCurrency(order.yesterdayDiscount) }}
        </div>
      </div>
      <div class="detail-item">
        <div class="detail-label">今天折扣</div>
        <div class="detail-value">
          {{ formatCurrency(order.todayDiscount) }}
        </div>
      </div>
    </div>

    <div class="foundation-rules">
      <p>介绍基金会规则</p>
      <div class="rules-content">
        <!-- 这里可以添加基金会规则的具体内容 -->
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { createOrder } from "../../api";
import { showToast } from "vant";

const order = ref({
  amount: 537.11,
  remaining: 537.11,
  currentLevel: "Bac 4/60",
  paymentStatus: "昨天折扣",
  yesterdayDiscount: 0.0,
  todayDiscount: 0.0,
});

const formatCurrency = (value) => {
  return value.toLocaleString("de-DE", {
    style: "currency",
    currency: "EUR",
    minimumFractionDigits: 2,
  });
};

const handlePay = () => {
  createOrder().then((res) => {
    if (res.code === 200) {
      showToast({
        message: "操作成功",
        type: "success",
      });
    } else {
      showToast({
        message: res.msg,
        type: "fail",
      });
    }
  });
};
</script>

<style scoped>
.order-detail-container {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  color: #fff;
  padding: 10px;
  padding-bottom: 220px;
  background: url("../../assets/img/BG-nhandon-B-V7rk8F.png") no-repeat center
    center fixed;
  background-size: cover;
}

.order-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #fff;
}

.data-provider {
  font-size: 0.9rem;
  color: #fff;
  margin-top: 5px;
}

.order-summary {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 25px;
  padding: 5px;
  border-radius: 6px;
}
.order-summary > img {
  width: 100%;
}

.amount-section {
  text-align: center;
}

.amount-display {
  color: #fff;
}

.amount-label {
  font-size: 0.9rem;
  color: #fff;
}

.status-badge {
  padding: 6px 59px;
  background-color: #0262b0;
  color: white;
  border-radius: 10px;
  font-size: 0.9rem;
  font-weight: bold;
  margin-top: 30px;
}

.order-details-grid {
  padding: 10px;
  border: 1px solid #7a797d;
  border-radius: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
}

.detail-item {
  display: flex;
  justify-content: space-between;
}

.detail-label {
  font-size: 0.85rem;
  color: #fff;
  margin-bottom: 5px;
}

.detail-value {
  font-size: 1.1rem;
}

.foundation-rules {
  margin-top: 30px;
  padding: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  border: 1px solid #7a797d;
  border-radius: 10px;
  -webkit-backdrop-filter: blur(5px);
  text-align-last: center;
}

.rules-content {
  font-size: 0.95rem;
  line-height: 1.5;
  color: #fff;
}
</style>
