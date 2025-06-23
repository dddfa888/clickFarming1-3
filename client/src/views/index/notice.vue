<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar :title="t('通知邮件')" />
    <div class="content-box">
      <div class="notice-box">
        <p>Hệ thống đã thanh toán 61.00€ cho bạn!</p>
      </div>
      <div class="notice-box" v-for="item in 35" :key="item">
        <p>Bạn đã đặt lệnh rút 61.00€.Yêu cầu của bạn đang được xét duyệt</p>
      </div>
      <div class="notice-box">
        <p>Hệ thống đã thanh toán 61.00€ cho bạn!</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
import { useI18n } from "vue-i18n";
const { t } = useI18n();
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
  padding-bottom: 70px;
}

.content-box {
  margin: 12px;
  padding: 12px;
  color: white;
  font-size: 12px;
  border-top: 1px solid #dad8da;
}

.notice-box {
  border-bottom: 1px solid #dad8da;
}
</style>
