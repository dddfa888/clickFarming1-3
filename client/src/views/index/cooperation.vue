<template>
  <div class="company-intro">
    <HeaderBar :title="t('开发合作')" />
    <div class="content-box">
      <p>
        {{ t("平台代理模式") }}
        <br />
        {{ t("Mercado Libre会员可以成为电商平台的代理商") }}
        <br />
        {{
          t(
            "通过推荐新会员成为 Mercado Libre 会员和代理商，可以获得价值 00.01%的额外奖金。直接推广会员级别佣金奖励从0.25%起，并投资资本来计算产品代码赚取的利润。"
          )
        }}
      </p>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
// import { getCompanyCooperation } from "../../api/index";
import { useI18n } from "vue-i18n";
const { t } = useI18n();
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  overflow-y: auto;
}

.content-box {
  background-color: transparent !important;
  -webkit-backdrop-filter: blur(6px) !important;
  backdrop-filter: blur(6px) !important;
  margin: 12px;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #7a797d;
  color: white;
  font-size: 15px;
}
.content-box > p {
  margin: 5px;
}
</style>
