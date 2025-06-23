<template>
  <div class="register-container">
    <div class="register-form">
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            v-model="form.loginAccount"
            name="loginAccount"
            :label="$t('账号')"
            :placeholder="$t('请输入账号')"
            left-icon="user-o"
            :rules="[{ required: true, message: '$t(请填写用户名)' }]"
          />
          <van-field
            v-model="form.phone"
            name="phone"
            :label="$t('电话号码')"
            :placeholder="$t('请输入电话号码')"
            left-icon="phone-o"
            :rules="[{ required: true, message: '$t(请填写电话号码)' }]"
          />
          <van-field
            v-model="form.loginPassword"
            type="password"
            name="loginPassword"
            :label="$t('登陆密码')"
            :placeholder="$t('请输入密码')"
            left-icon="lock"
            :rules="[{ required: true, message: '$t(请填写密码)' }]"
          />
          <van-field
            v-model="form.fundPassword"
            type="password"
            name="fundPassword"
            :label="$t('交易密码')"
            :placeholder="$t('请输入交易密码')"
            left-icon="lock"
            :rules="[{ required: true, message: '$t(请填写交易密码)' }]"
          />
          <van-field
            v-model="form.invitationCode"
            name="invitationCode"
            :label="$t('邀请码')"
            :placeholder="$t('请输入邀请码')"
            :rules="[{ required: true, message: '$t(请填写邀请码)' }]"
            left-icon="friends-o"
          />
        </van-cell-group>
        <div style="margin: 16px">
          <van-button round block type="primary" native-type="submit">
            {{ $t("注册") }}
          </van-button>
        </div>
      </van-form>

      <div class="register-footer">
        <span @click="$router.push('/login')">{{ $t("登录") }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { register } from "../../api/index.js";
import { useRouter } from "vue-router";
import { showToast } from "vant";

const form = reactive({
  loginAccount: "",
  phone: "",
  loginPassword: "",
  fundPassword: "",
  invitationCode: "",
});
const router = useRouter();

function onSubmit(values) {
  console.log("注册提交成功：", values);
  register(form).then((res) => {
    console.log(res);
    if (res.code === 200) {
      showToast({
        message: "操作成功",
        type: "success",
      });
      router.push("/login");
    } else {
      showToast({
        message: res.msg,
        type: "fail",
      });
    }
  });
  // 这里可以调用注册接口
}
</script>

<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
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

.register-form {
  /* background-color: rgba(255, 255, 255, 0.95); */
  padding: 30px 20px;
  border-radius: 8px;
  width: 100%;
  max-width: 400px;
  /* box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1); */
}

@media (min-width: 768px) {
  .register-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  .register-form {
    padding: 40px;
    max-width: 400px;
  }

  .language-selector {
    font-size: 16px;
  }
}

.register-footer {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 20px;
  font-size: 14px;
  color: #fff;
  cursor: pointer;
}
</style>
