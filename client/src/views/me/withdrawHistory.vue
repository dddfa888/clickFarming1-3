<template>
  <div class="company-intro">
    <HeaderBar title="提款记录" />
    <div class="transaction-list">
      <div
        v-for="(transaction, index) in transactions"
        :key="index"
        class="transaction-item"
      >
        <div class="transaction-info">
          <div class="transaction-time">时间:{{ transaction.createTime }}</div>
          <div
            class="transaction-amount"
            :class="{ negative: transaction.amount < 0 }"
          >
            金钱数额:- {{ formatAmount(transaction.amount) }}
          </div>
          <div class="transaction-balance">
            剩余: {{ formatAmount(transaction.accountBack) }}
          </div>
        </div>
        <div class="transaction-status">
          {{ transaction.status === 1 ? "成功" : "失败" }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getWithdrawRecord } from "../../api/index.js";
const transactions = ref([]);

const formatAmount = (amount) => {
  return amount.toFixed(2).replace(".", ",") + " €";
};

getWithdrawRecord().then((res) => {
  console.log(res.rows);
  transactions.value = res.rows;
  console.log(transactions.value, 21);
});
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
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
.transaction-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
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
  color: #fff;
  text-align: right;
  font-size: 14px;
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
  color: #fff;
  font-size: 14px;
}
</style>
