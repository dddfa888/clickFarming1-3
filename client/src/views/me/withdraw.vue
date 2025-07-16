<template>
  <div class="withdraw-page">
    <div class="withdraw-container">
      <div class="header">
        <div @click="toback">
          <van-icon name="arrow-left" />
          <span class="back">{{ t("取款") }}</span>
        </div>
        <span class="balance">{{ balance }} €</span>
      </div>

      <div class="form" v-if="accountName && accountNumber">
        <div class="form-group">
          <label>{{ t("账户名称") }}</label>
          <input type="text" v-model="accountName" disabled />
        </div>

        <div class="form-group">
          <label>{{ t("账号") }}</label>
          <input type="text" v-model="accountNumber" disabled />
        </div>

        <div class="form-group amount-group">
          <label>{{ t("金钱数额") }}</label>
          <input
            style="border: 1px solid #e5e7eb"
            type="text"
            v-model="amount"
            :placeholder="t('金钱数额')"
          />
          <button @click="fillAll">{{ t("全部") }}</button>
        </div>

        <div class="form-group password-group">
          <label>{{ t("提现密码") }}</label>
          <input
            style="border: 1px solid #e5e7eb"
            :type="showPassword ? 'text' : 'password'"
            v-model="password"
            :placeholder="t('提现密码')"
          />
          <van-icon
            :name="showPassword ? 'eye-o' : 'closed-eye'"
            class="eye-icon"
            @click="togglePassword"
          />
        </div>

        <button class="submit-btn" @click="submit">{{ t("取款") }}</button>
      </div>
      <div v-else>
        Bạn chưa điền thông tin ngân hàng vui lòng click vào tin.
        <span
          @click="
            () => {
              router.push('/bankInfo');
            }
          "
          style="color: #20a53a"
        >
          Tại đây</span
        >
        để đi điền thông
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { withdraw, getUserInfo } from "../../api/index.js";
import { useI18n } from "vue-i18n";
import { notify } from "../../utils/notify.js";

const { t } = useI18n();

const balance = ref("");
const accountName = ref("");
const accountNumber = ref("");
const amount = ref("");
const password = ref("");
const showPassword = ref(false);
const router = useRouter();

function togglePassword() {
  showPassword.value = !showPassword.value;
}

function fillAll() {
  amount.value = balance.value;
}

function toback() {
  router.go(-1);
}
function submit() {
  withdraw({ amount: amount.value, fundPassword: password.value }).then(
    (res) => {
      if (res.code === 200) {
        notify({
          message: t("操作成功"),
          type: "success",
          duration: 2000,
        });

        // 清空输入
        amount.value = "";
        password.value = "";

        // 重新获取用户信息更新余额
        getUserInfo().then((res) => {
          balance.value = res.data.accountBalance;
        });
      } else {
        notify({
          message: t(res.msg),
          type: "error",
          duration: 2000,
        });
        amount.value = "";
        password.value = "";
      }
    }
  );
}

getUserInfo().then((res) => {
  accountName.value = res.data.bankAccountName;
  accountNumber.value = res.data.bankAccountNumber;
  balance.value = res.data.accountBalance;
});
</script>

<style scoped>
.withdraw-page {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  padding: 20px;
  color: white;
  font-family: Arial, sans-serif;
}

.withdraw-container {
  max-width: 400px;
  margin: auto;
  padding-top: 40px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

.form {
  padding: 20px;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
  position: relative;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: 100%;
  background-color: transparent !important;
  border: 1px solid #e5e7eb;
  padding: 10px;
  border: none;
  color: #fff;
  border-radius: 4px;
}

input[disabled] {
  background-color: none;
  border: 1px solid #e5e7eb;
  color: #fff;
}

.amount-group button,
.password-group button {
  position: absolute;
  right: 10px;
  top: 32px;
  background: none;
  border: none;
  color: #00f;
  cursor: pointer;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #3b4d63;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  margin-top: 10px;
  cursor: pointer;
}

.password-group {
  position: relative;
}

.eye-icon {
  position: absolute;
  right: 10px;
  top: 34px;
  font-size: 20px;
  color: #fff;
  cursor: pointer;
}
@media screen and (min-width: 768px) {
  .withdraw-page {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    height: 100vh;
    padding: 20px;
    width: 500px;
    margin: 0 auto;
    color: white;
    font-family: Arial, sans-serif;
  }

  .withdraw-container {
    max-width: 400px;
    margin: auto;
    padding-top: 40px;
  }

  .header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 30px;
  }

  .form {
    padding: 20px;
    border-radius: 8px;
  }

  .form-group {
    margin-bottom: 15px;
    position: relative;
  }

  label {
    display: block;
    margin-bottom: 5px;
  }

  input {
    width: 100%;
    background-color: transparent !important;
    border: 1px solid #e5e7eb;
    padding: 10px;
    border: none;
    color: #fff;
    border-radius: 4px;
  }

  input[disabled] {
    background-color: none;
    border: 1px solid #e5e7eb;
    color: #fff;
  }

  .amount-group button,
  .password-group button {
    position: absolute;
    right: 10px;
    top: 32px;
    background: none;
    border: none;
    color: #00f;
    cursor: pointer;
  }

  .submit-btn {
    width: 100%;
    padding: 12px;
    background-color: #3b4d63;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    margin-top: 10px;
    cursor: pointer;
  }

  .password-group {
    position: relative;
  }

  .eye-icon {
    position: absolute;
    right: 10px;
    top: 34px;
    font-size: 20px;
    color: #fff;
    cursor: pointer;
  }
}
</style>
