<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar title="基本原则" />
    <div class="content-box">
      <p>
        当您成为Mercado
        Libre会员后，您将收到与您的订单相关的产品代码，包括产品信息、订单详情、产品价值、数量...等。
      </p>
      <p>
        Mercado Libre
        会员将作为中间人，帮助确认制造商和合作伙伴（买家）之间的订单。
      </p>
      <p>
        Mercado Libre
        会员将作为中间人，帮助确认制造商和合作伙伴（买家）之间的订单。
      </p>
      <!-- 可将长文本继续补充 -->
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
}

.content-box {
  background-color: transparent !important;
  -webkit-backdrop-filter: blur(6px) !important;
  backdrop-filter: blur(6px) !important;
  margin: 12px;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #7a797d;
  color: white;
  font-size: 15px;
}
.content-box > p {
  margin: 5px;
}
</style>
