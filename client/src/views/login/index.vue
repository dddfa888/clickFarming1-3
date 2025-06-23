<template>
  <div class="login-container">
    <!--分开的语言选择区域 -->
    <div class="language-selector">
      <span class="label">{{ $t("语言") }}</span>
      <div class="dropdown-wrapper" @click="toggleLangList">
        {{ $t(selectedLanguage) }}

        <ul v-if="showLangList" class="lang-dropdown">
          <li
            v-for="(lang, index) in languageList"
            :key="index"
            @click.stop="selectLanguage(lang)"
            :class="{ active: lang === selectedLanguage }"
          >
            {{ $t(lang) }}
          </li>
        </ul>
      </div>
    </div>

    <!-- 登录表单 -->
    <div class="login-form">
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
            v-model="form.loginAccount"
            name="loginAccount"
            :label="$t('用户名')"
            :placeholder="$t('请填写用户名')"
            left-icon="user-o"
            :rules="[{ required: true, message: $t('请填写用户名') }]"
          />
          <van-field
            v-model="form.loginPassword"
            type="password"
            name="loginPassword"
            :label="$t('密码')"
            :placeholder="$t('请填写密码')"
            left-icon="lock"
            :rules="[{ required: true, message: '请填写密码' }]"
          />
        </van-cell-group>
        <div style="margin: 16px">
          <van-button round block type="primary" native-type="submit">
            {{ $t("现在登录") }}
          </van-button>
        </div>
      </van-form>

      <div class="login-footer">
        <span @click="router.push('/register')">{{ $t("注册") }}</span>
      </div>
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
const showLangList = ref(false);
const langMap = {
  中国: "zh",
  // 英语: "en",
  越南语: "vi",
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
  const langCode = langMap[lang] || "zh";
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
      showToast({ message: "操作成功", type: "success" });
      localStorage.setItem("token", res.data.token);
      router.push("/");
    } else {
      showToast({ message: res.msg, type: "fail" });
    }
  });
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
  border-radius: 8px;
  max-width: 400px;
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
  color: #fff;
  text-align: right;
}

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

.dropdown-wrapper .arrow {
  margin-left: 4px;
  font-size: 10px;
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
</style>
