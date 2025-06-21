<template>
  <div class="distribution-history">
    <div class="header">
      <h2>
        发行历史 <span class="total-amount">{{ totalAmount }} €</span>
      </h2>
      <div class="provider">
        数据提供者 Mercado Libre <span class="remaining">剩余(€)</span>
      </div>
    </div>

    <div class="history-list">
      <div
        v-for="(item, index) in historyItems"
        :key="index"
        class="history-item"
      >
        <div class="item-header">
          <div class="time-code">
            <span class="time">时间: {{ item.createTime }}</span>
            <span class="code">代码: {{ item.productId }}</span>
          </div>
        </div>

        <div class="product-info">
          <div class="product-name">
            <img :src="productImageUrl" alt="" />
            <div>
              {{ item.productName }}
            </div>
          </div>
          <div class="product-price">
            {{ item.unitPrice }} €
            <span class="quantity">x {{ item.number }}</span>
          </div>
        </div>

        <div class="calculation">
          <div class="calc-row">
            <span>分配总额:</span>
            <span class="amount">{{ item.totalAmount }} €</span>
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

        <button
          v-if="item.processStatus === 'Waiting'"
          class="send-button"
          @click="showModal = true"
        >
          发送分发
        </button>
      </div>
    </div>
    <ProductModal
      v-if="showModal"
      :time="'17-06-2025 19:53:50'"
      :id="'65f6d2c747241532e71f3515'"
      :image="'/path/to/product.png'"
      :title="'Candy CITT642C/E1 Piano Cottura...'"
      :price="200"
      :count="2"
      :total="400"
      :profit="0.96"
      :refund="400.96"
      @close="showModal = false"
      @pay="handlePay"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import ProductModal from "../../components/ProductModal.vue";
import { getOrderList } from "../../api/index.js";

const totalAmount = ref("532,94");

const historyItems = ref([
  // {
  //   time: "17-06-2025 19:53:13",
  //   code: "65f6d07a625b1c3a5c3dc062",
  //   product: {
  //     name: "G3 Ferrari G10032 Pizzeria Snack Napoletana, Forno Pizza Plus Evo, Doppia Pietra Refrattaria (Diametro 31 Cm), 1200 W, Timer 5', Ricettario Incluso, Rosso",
  //     price: "99,00",
  //     quantity: 5,
  //   },
  //   distributionTotal: "495,00",
  //   profit: "1,19",
  //   refundAmount: "496,19",
  // },
]);
const showModal = ref(false);

const handlePay = () => {
  console.log("用户点击支付");
  showModal.value = false;
};

getOrderList().then((res) => {
  console.log(res.rows);
  historyItems.value = res.rows;
  console.log(historyItems.value);
});
// 可以添加数据更新逻辑
</script>

<style scoped>
.distribution-history {
  font-family: Arial, sans-serif;
  max-width: 800px;
  height: 100vh;
  margin: 0 auto;
  background: url("../../assets/img/BG-kho-B9q9tfZS.png") no-repeat center
    center fixed;
  background-size: cover;
  color: #fff;
  padding: 10px;
  padding-bottom: 70px;
}

.header {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.header h2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0;
  font-size: 20px;
}

.total-amount {
  font-weight: bold;
  color: #fff;
}

.provider {
  display: flex;
  justify-content: space-between;
  margin-top: 5px;
  font-size: 14px;
  color: #fff;
}

.remaining {
  font-weight: bold;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.history-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
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
