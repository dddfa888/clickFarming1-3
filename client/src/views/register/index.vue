<template>
  <div class="register-container">
    <div class="register-footer">
      <span @click="$router.push('/login')">{{ $t("登录") }}</span>
      <span @click="$router.push('/register')">{{ $t("注册") }}</span>
    </div>
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
        <div
          style="
            margin: 16px;
            display: flex;
            justify-content: space-around;
            align-items: center;
          "
        >
          <van-button
            class="register-btn"
            round
            block
            type="primary"
            native-type="submit"
          >
            {{ $t("注册") }}
          </van-button>
        </div>
      </van-form>
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
        message: t("操作成功"),
        type: "success",
      });
      router.push("/login");
    } else {
      showToast({
        message: t(res.msg),
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

.register-footer {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  color: #fff;
}

@media (min-width: 768px) {
  .register-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    width: 100%;
    padding: 40px;
    box-sizing: border-box;
    background: url("../../assets/img/login-bg.png") no-repeat center center /
      cover;
  }

  /* 注册表单 */
  .register-form {
    width: 100%;
    max-width: 800px;
    background: rgba(255, 255, 255, 0.12);
    border-radius: 12px;
    padding: 20px;
    box-sizing: border-box;
  }

  /* 表单内容透明 */
  .register-form :deep(.van-cell-group) {
    background: transparent;
  }

  .register-form :deep(.van-field__left-icon) {
    display: flex;
    align-items: center; /* 垂直居中 */
    justify-content: center;
    font-size: 20px; /* 图标大小 */
    width: 24px; /* 给一点宽度保证不挤 */
    height: 24px;
  }

  /* 如果想让icon和输入内容间距更大，可以加这个 */
  .register-form :deep(.van-field__body) {
    display: flex;
    align-items: center; /* 整个输入部分垂直居中 */
  }

  /* 输入文本字体 */
  .register-form :deep(.van-field__control) {
    font-size: 18px;
  }

  /* 标签字体 */
  .register-form :deep(.van-field__label) {
    font-size: 16px;
  }

  /* 占位符字体 */
  .register-form :deep(.van-field__control::placeholder) {
    font-size: 16px;
  }

  /* 表单项留白 */
  .register-form :deep(.van-cell) {
    padding: 20px 0;
    height: 100px;
    align-items: center;
    align-content: center;
  }

  /* 输入文本字体 */
  .register-form :deep(.van-field__control) {
    font-size: 18px;
  }

  /* 标签字体 */
  .register-form :deep(.van-field__label) {
    font-size: 13px;
    width: 15%;
    height: 50%;
    display: flex;
    align-self: start;
  }

  /* 占位符字体 */
  .register-form :deep(.van-field__control::placeholder) {
    font-size: 16px;
  }

  /* 输入图标 */
  .register-form :deep(.van-field__left-icon) > i {
    font-size: 20px;
    display: flex;
    align-items: center;
    line-height: 1;
  }

  .register-btn {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 35%;
    height: 50%;
    font-size: 18px;
    padding: 10px;
  }

  /* 按钮 */
  .register-btn .van-button {
    font-size: 14px;
    height: 28px;
    width: 50%;
  }

  /* 语言选择器 */
  .language-selector {
    position: absolute;
    top: 20px;
    right: 20px;
    display: flex;
    align-items: center;
    font-size: 16px;
    color: #fff;
    z-index: 20;
  }
  .language-selector .label {
    margin-right: 8px;
  }
  .dropdown-wrapper {
    position: relative;
    background: rgba(255, 255, 255, 0.15);
    padding: 6px 12px;
    border-radius: 6px;
    cursor: pointer;
    user-select: none;
  }
  .lang-dropdown {
    position: absolute;
    top: 38px;
    right: 0;
    background: #e5e5e5;
    color: #000;
    list-style: none;
    padding: 5px 0;
    margin: 0;
    border-radius: 4px;
    width: 120px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    z-index: 999;
  }
  .lang-dropdown li {
    padding: 10px 14px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  .lang-dropdown li:hover,
  .lang-dropdown li.active {
    background-color: #d3d3d3;
  }

  .register-footer {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
    color: #fff;
    margin-bottom: 20px;
  }
}
</style>
