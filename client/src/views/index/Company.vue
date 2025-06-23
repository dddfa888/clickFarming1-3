<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar :title="t('公司简介')" />
    <div class="content-box">
      <p>
        {{ t("Company Profile") }}
      </p>
      <p></p>
      <!-- 可将长文本继续补充 -->
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
import { useI18n } from "vue-i18n";
// import { getCompanyProfile } from "../../api/index";
const { t } = useI18n();
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
  line-height: 1.8;
  font-size: 14px;
  margin-bottom: 80px;
}
</style>
