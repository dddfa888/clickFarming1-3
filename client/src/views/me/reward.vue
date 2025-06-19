<template>
  <div class="company-intro">
    <HeaderBar title="奖励历史记录" />
    <div class="transaction-list">
      <div
        v-for="(transaction, index) in transactions"
        :key="index"
        class="transaction-item"
      >
        <div class="transaction-time">描述：{{ transaction.describe }}</div>
        <div class="transaction-amount">
          金钱数额：{{ formatAmount(transaction.amount) }}
        </div>
        <div class="transaction-balance">
          剩余: {{ formatBalance(transaction.balance) }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
const transactions = ref([
  { amount: +61.0, balance: 561.24, describe: "订单奖励" },
  { amount: +61.0, balance: 561.84, describe: "订单奖励" },
  { amount: +61.0, balance: 561.8, describe: "订单奖励" },
  { amount: +61.0, balance: 561.24, describe: "订单奖励" },
  { amount: +60.0, balance: 560.11, describe: "订单奖励" },
  { amount: +61.0, balance: 561.53, describe: "订单奖励" },
  { amount: +63.0, balance: 563.14, describe: "订单奖励" },
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
  padding: 10px;
  display: flex;
  color: #fff;
  font-size: 13px;
  gap: 5px;
  flex-direction: column;
  border-bottom: 1px solid #e5e7eb;
}

.transaction-amount {
  grid-column: 2;
  grid-row: 1;
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
