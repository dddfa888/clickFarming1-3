<template>
  <div class="login-container">
    <div class="language-selector">
      <span class="language">语言</span>
      <span class="country"></span>
    </div>

    <div class="login-form">
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            v-model="form.loginAccount"
            name="loginAccount"
            label="用户名"
            placeholder="请输入用户名"
            left-icon="user-o"
            :rules="[{ required: true, message: '请填写用户名' }]"
          />
          <van-field
            v-model="form.loginPassword"
            type="password"
            name="loginPassword"
            label="密码"
            placeholder="请输入密码"
            left-icon="lock"
            :rules="[{ required: true, message: '请填写密码' }]"
          />
        </van-cell-group>
        <div style="margin: 16px">
          <van-button round block type="primary" native-type="submit">
            现在登录
          </van-button>
        </div>
      </van-form>

      <div class="login-footer">
        <span>注册</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { login } from "../../api/index.js";
const form = reactive({
  loginAccount: "",
  loginPassword: "",
});
function onSubmit(values) {
  console.log("注册提交成功：", values);
  login(form).then((res) => {
    cobsole.log(res);
  });
  // 这里可以调用注册接口
}
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  background: url("../../assets/img/login-bg.png") no-repeat center center;
  background-size: cover;
  min-height: 100vh;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.language-selector {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 14px;
  color: #fff;
}

.login-form {
  /* background-color: rgba(255, 255, 255, 0.95); */
  padding: 30px 20px;
  border-radius: 8px;
  /* width: 100%; */
  max-width: 400px;
  /* box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1); */
}

@media (min-width: 768px) {
  .login-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .login-form {
    padding: 40px;
    max-width: 400px;
  }

  .language-selector {
    font-size: 16px;
  }
}

.login-footer {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 14px;
  color: #fff;
  cursor: pointer;
}
</style>
