<template>
  <div class="company-intro">
    <HeaderBar title="提款记录" />
    <div class="transaction-list">
      <div
        v-for="(transaction, index) in transactions"
        :key="index"
        class="transaction-item"
      >
        <div class="transaction-time">{{ transaction.time }}</div>
        <div
          class="transaction-amount"
          :class="{ negative: transaction.amount < 0 }"
        >
          {{ formatAmount(transaction.amount) }}
        </div>
        <div class="transaction-balance">
          剩余: {{ formatBalance(transaction.balance) }}
        </div>
        <div v-if="transaction.status" class="transaction-status">
          {{ transaction.status }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
const transactions = ref([
  { time: "23:24:01", amount: -61.0, balance: 561.24, status: "成功" },
  { time: "22:22:41", amount: -61.0, balance: 561.84, status: "成功" },
  { time: "23:13:10", amount: -61.0, balance: 561.8, status: "成功" },
  { time: "19:35:30", amount: -61.0, balance: 561.24, status: "成功" },
  { time: "22:23:30", amount: -60.0, balance: 560.11 },
  { time: "22:24:06", amount: -61.0, balance: 561.53 },
  { time: "00:07:06", amount: -63.0, balance: 563.14 },
]);

const formatAmount = (amount) => {
  return amount.toFixed(2).replace(".", ",") + " €";
};

const formatBalance = (balance) => {
  return balance.toFixed(2).replace(".", ",") + " €";
};
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
}

.transaction-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.transaction-item {
  padding: 15px;
  border-radius: 8px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  gap: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.transaction-time {
  grid-column: 1;
  grid-row: 1;
  color: #fff;
  font-size: 14px;
}

.transaction-amount {
  grid-column: 2;
  grid-row: 1;
  text-align: right;
}

.transaction-amount.negative {
  color: #e74c3c;
}
.transaction-balance {
  grid-column: 1 / span 2;
  grid-row: 2;
  color: #fff;
  font-size: 14px;
}

.transaction-status {
  grid-column: 2;
  grid-row: 2;
  text-align: right;
  color: #2ecc71;
}
</style>
