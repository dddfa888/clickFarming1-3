<template>
  <div class="login-container">
    <!--分开的语言选择区域 -->
    <div class="language-selector">
      <span class="label">{{ $t("语言") }}</span>
      <div class="dropdown-wrapper" @click="toggleLangList">
        {{ t(selectedLanguage) }}

        <ul v-if="showLangList" class="lang-dropdown">
          <li
            v-for="(lang, index) in languageList"
            :key="index"
            @click.stop="selectLanguage(lang)"
            :class="{ active: lang === selectedLanguage }"
          >
            {{ t(lang) }}
          </li>
        </ul>
      </div>
    </div>

    <div class="login-footer">
      <span @click="router.push('/login')">{{ t("登录") }}</span>
      <span @click="router.push('/register')">{{ t("注册") }}</span>
    </div>

    <!-- 登录表单 -->
    <div class="login-form">
      <van-form>
        <van-cell-group inset>
          <van-field
            v-model="form.loginAccount"
            name="loginAccount"
            :label="t('用户名')"
            :placeholder="t('请填写用户名')"
            left-icon="user-o"
            :rules="[{ required: true, message: t('请填写用户名') }]"
          />
          <van-field
            v-model="form.loginPassword"
            type="password"
            name="loginPassword"
            :label="t('密码')"
            :placeholder="t('请填写密码')"
            left-icon="lock"
            :rules="[{ required: true, message: t('请填写密码') }]"
          />
        </van-cell-group>
        <div class="login-btn" style="margin: 16px">
          <van-button
            round
            block
            type="primary"
            native-type="submit"
            @click="onSubmit"
          >
            {{ t("现在登录") }}
          </van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "../../api/index.js";
import { showToast } from "vant";
import { useI18n } from "vue-i18n";
import { useLangStore } from "../../store/useLangStore.js";

const router = useRouter();
const form = reactive({
  loginAccount: "",
  loginPassword: "",
});

const langStore = useLangStore();
const { locale } = useI18n();
const { t } = useI18n();
const showLangList = ref(false);
const langMap = {
  越南语: "vi",
  中国: "zh",
  英语: "en",
  // 日本: "ja",
  // 法国: "fr",
  // 俄罗斯: "ru",
  // 韩国: "ko",
};
const languageList = Object.keys(langMap);
const reverseLangMap = Object.fromEntries(
  Object.entries(langMap).map(([k, v]) => [v, k])
);

// 初始化选中语言
const selectedLanguage = ref(reverseLangMap[langStore.locale]);
locale.value = langStore.locale;

function selectLanguage(lang) {
  selectedLanguage.value = lang;
  const langCode = langMap[lang] || "vi";
  langStore.setLocale(langCode);
  locale.value = langCode;
  showLangList.value = !showLangList.value;
}

function toggleLangList() {
  showLangList.value = !showLangList.value;
}

function onSubmit(values) {
  login(form).then((res) => {
    if (res.code === 200) {
      showToast({ message: t("操作成功"), type: "success" });
      localStorage.setItem("token", res.data.token);
      router.push("/");
    } else {
      showToast({ message: t(res.msg), type: "fail" });
    }
  });
}
</script>

<style scoped>
.login-container {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  background: url("../../assets/img/login-bg.png") no-repeat center center /
    cover;
}

/* 登录表单 */
.login-form {
  width: 100%;
  max-width: 500px;
  /* background: rgba(255, 255, 255, 0.08); */
  border-radius: 10px;
  padding: 10px;
  box-sizing: border-box;
}

/* 表单内容透明 */
.login-form :deep(.van-cell-group) {
  background: transparent;
}

/* 表单项留白 */
.login-form :deep(.van-cell) {
  padding: 12px 0;
  align-items: center;
}

/* 注册按钮 */
.login-footer {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  color: #fff;
}

.login-form :deep(.van-field__label) {
  font-size: 13px;
}

/* 语言选择器 */
.language-selector {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #fff;
  z-index: 20;
}
.language-selector .label {
  margin-right: 6px;
}
.dropdown-wrapper {
  position: relative;
  background: rgba(255, 255, 255, 0.15);
  padding: 5px 10px;
  border-radius: 6px;
  cursor: pointer;
  user-select: none;
}
.lang-dropdown {
  position: absolute;
  top: 35px;
  right: 0;
  background: #e5e5e5;
  color: #000;
  list-style: none;
  padding: 5px 0;
  margin: 0;
  border-radius: 4px;
  width: 100px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  z-index: 999;
}
.lang-dropdown li {
  padding: 8px 12px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.lang-dropdown li:hover,
.lang-dropdown li.active {
  background-color: #d3d3d3;
}

/* PC端适配 */
@media (min-width: 768px) {
  .login-container {
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

  /* 登录表单 */
  .login-form {
    width: 100%;
    max-width: 800px;
    background: rgba(255, 255, 255, 0.12);
    border-radius: 12px;
    padding: 20px;
    box-sizing: border-box;
  }

  /* 表单内容透明 */
  .login-form :deep(.van-cell-group) {
    background: transparent;
  }

  .login-form :deep(.van-field__left-icon) {
    display: flex;
    align-items: center; /* 垂直居中 */
    justify-content: center;
    font-size: 20px; /* 图标大小 */
    width: 24px; /* 给一点宽度保证不挤 */
    height: 24px;
  }

  /* 如果想让icon和输入内容间距更大，可以加这个 */
  .login-form :deep(.van-field__body) {
    display: flex;
    align-items: center; /* 整个输入部分垂直居中 */
  }

  /* 输入文本字体 */
  .login-form :deep(.van-field__control) {
    font-size: 18px;
  }

  /* 标签字体 */
  .login-form :deep(.van-field__label) {
    font-size: 16px;
  }

  /* 占位符字体 */
  .login-form :deep(.van-field__control::placeholder) {
    font-size: 16px;
  }

  /* 表单项留白 */
  .login-form :deep(.van-cell) {
    padding: 20px 0;
    height: 100px;
    align-items: center;
    align-content: center;
  }

  /* 输入文本字体 */
  .login-form :deep(.van-field__control) {
    font-size: 18px;
  }

  /* 标签字体 */
  .login-form :deep(.van-field__label) {
    font-size: 13px;
    width: 15%;
    height: 50%;
    display: flex;
    align-self: start;
  }

  /* 占位符字体 */
  .login-form :deep(.van-field__control::placeholder) {
    font-size: 16px;
  }

  /* 输入图标 */
  .login-form :deep(.van-field__left-icon) > i {
    font-size: 20px;
    display: flex;
    align-items: center;
    line-height: 1;
  }

  .login-btn {
    display: flex;
    justify-content: space-around;
  }

  /* 按钮 */
  .login-btn .van-button {
    font-size: 18px;
    height: 48px;
    width: 50%;
  }

  /* 注册按钮 */
  .login-footer {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
    color: #fff;
    margin-bottom: 20px;
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
}
</style>
