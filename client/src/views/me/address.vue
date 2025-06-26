<template>
  <div class="company-intro">
    <HeaderBar :title="t('地址')" />
    <div class="form-group">
      <label :class="{ floated: inputValue }">{{ t("地址") }}</label>
      <input
        v-model="withdrawalAddress"
        type="text"
        placeholder=" "
        class="input"
        disabled="true"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getUserInfo } from "../../api/index";
const inputValue = ref("");
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const withdrawalAddress = ref("");

getUserInfo().then((res) => {
  console.log(res.data.withdrawalAddress);
  withdrawalAddress.value = res.data.withdrawalAddress;
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
.form-group {
  position: relative;
  margin: 20px;
  border: 1px solid #ccc;
  border-radius: 6px;
  padding: 16px 10px 6px;
  background-color: transparent;
  font-family: sans-serif;
  overflow: visible; /* 避免 label 超出边框被裁剪 */
}

.input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 16px;
  background: transparent;
  color: #fff;
  padding: 0;
  z-index: 2;
}

label {
  position: absolute;
  left: 10px;
  top: -13px;
  color: #aaa;
  font-size: 16px;
  pointer-events: none;
  transition: 0.2s ease all;
  background-color: #011727; /* 默认透明 */
  padding: 0 4px;
}

.input:focus + label,
.floated {
  top: -8px; /* 上移多一点 */
  font-size: 12px;
  color: #42b983;
  background-color: white; /* 💡 或改成页面背景色 */
}
</style>
