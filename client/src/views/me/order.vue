<template>
  <div class="distribution-history">
    <HeaderBar title="订单历史" />
    <div class="history-list">
      <div
        v-for="(item, index) in historyItems"
        :key="index"
        class="history-item"
      >
        <div class="item-header">
          <div class="time-code">
            <span class="time">时间: {{ item.time }}</span>
            <span class="code">代码: {{ item.code }}</span>
          </div>
        </div>

        <div class="product-info">
          <div class="product-name">
            <img
              src="https://res.cloudinary.com/dh7cjddk2/image/upload/v1710674630/order_product/lugtxtmpgt63s0ka6ztq.jpg"
              alt=""
            />
            <div>
              {{ item.product.name }}
            </div>
          </div>
          <div class="product-price">
            {{ item.product.price }} €
            <span class="quantity">x {{ item.product.quantity }}</span>
          </div>
        </div>

        <div class="calculation">
          <div class="calc-row">
            <span>分配总额:</span>
            <span class="amount">{{ item.distributionTotal }} €</span>
          </div>
          <div class="calc-row">
            <span>利润:</span>
            <span class="amount">{{ item.profit }} €</span>
          </div>
          <div class="calc-row">
            <span>退款金额:</span>
            <span class="amount highlight">{{ item.refundAmount }} €</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";

const totalAmount = ref("532,94");

const historyItems = ref([
  {
    time: "17-06-2025 19:53:50",
    code: "65f6d2c747241532e71f3515",
    product: {
      name: "Candy CIT1642C/E1 Piano Cottura a Induzione, 4 fuochi, Vetroceramica, da Incasso, Power Management, Touch, Grill, Blocco Sicurezza Bambino, 59x55x52 cm, Nero",
      price: "200,00",
      quantity: 2,
    },
    distributionTotal: "400,00",
    profit: "0,96",
    refundAmount: "400,96",
  },
  {
    time: "17-06-2025 19:53:13",
    code: "65f6d07a625b1c3a5c3dc062",
    product: {
      name: "G3 Ferrari G10032 Pizzeria Snack Napoletana, Forno Pizza Plus Evo, Doppia Pietra Refrattaria (Diametro 31 Cm), 1200 W, Timer 5', Ricettario Incluso, Rosso",
      price: "99,00",
      quantity: 5,
    },
    distributionTotal: "495,00",
    profit: "1,19",
    refundAmount: "496,19",
  },
  {
    time: "17-06-2025 19:53:13",
    code: "65f6d07a625b1c3a5c3dc062",
    product: {
      name: "G3 Ferrari G10032 Pizzeria Snack Napoletana, Forno Pizza Plus Evo, Doppia Pietra Refrattaria (Diametro 31 Cm), 1200 W, Timer 5', Ricettario Incluso, Rosso",
      price: "99,00",
      quantity: 5,
    },
    distributionTotal: "495,00",
    profit: "1,19",
    refundAmount: "496,19",
  },
]);
const showModal = ref(false);

const handlePay = () => {
  console.log("用户点击支付");
  showModal.value = false;
};

// 可以添加数据更新逻辑
</script>

<style scoped>
.distribution-history {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center fixed;
  background-size: cover;
  color: #fff;
  padding-bottom: 70px;
}

.history-list {
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 20px;
}

.history-item {
  width: 85%;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.item-header {
  margin-bottom: 15px;
}

.time-code {
  display: flex;
  flex-direction: column;
  font-size: 14px;
  color: #fff;
}

.time {
  margin-bottom: 3px;
}

.code {
  font-family: monospace;
  color: #fff;
}

.product-info {
  margin-bottom: 15px;
}

.product-name {
  font-weight: 500;
  margin-bottom: 8px;
  line-height: 1.4;
  display: flex;
  align-items: center;
}

.product-name > img {
  width: 30%;
  margin-right: 10px;
}

.product-price {
  font-weight: bold;
  text-align: right;
}

.quantity {
  font-weight: normal;
  color: #fff;
}

.calculation {
  margin: 15px 0;
  padding: 15px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 6px;
}

.calc-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.calc-row:last-child {
  margin-bottom: 0;
}

.amount {
  font-weight: bold;
}

.highlight {
  color: #e74c3c;
}

.send-button {
  width: 30%;
  padding: 5px;
  font-size: 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.send-button:hover {
  background-color: #2980b9;
}

@media (max-width: 600px) {
  .product-name {
    font-size: 14px;
  }

  .time-code {
    font-size: 12px;
  }
}
</style>
