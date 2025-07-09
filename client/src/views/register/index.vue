<template>
  <div class="register-container">
    <!-- 多语言选择器 -->
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

    <div class="register-footer">
      <span @click="$router.push('/login')">{{ $t("登录") }}</span>
      <span @click="$router.push('/register')">{{ $t("注册") }}</span>
    </div>

    <!-- 表单 -->
    <div class="register-form">
      <form @submit.prevent="onSubmit">
        <!-- 账号 -->
        <div class="form-group">
          <i class="iconfont icon-user input-icon"></i>
          <input
            v-model="form.loginAccount"
            type="text"
            :placeholder="t('请输入账号')"
            required
          />
        </div>

        <!-- 手机号带区号 -->
        <div class="form-group phone-group">
          <i class="iconfont icon-phone input-icon"></i>
          <select v-model="form.areaCode" class="area-select">
            <option value="+86">+84</option>
            <option value="+84">+65</option>
            <option value="+1">+66</option>
            <option value="+81">+62</option>
            <option value="+33">+60</option>
            <option value="+33">+63</option>
            <option value="+33">+27</option>
            <option value="+33">+20</option>
            <option value="+33">+234</option>
            <option value="+33">+1</option>
            <option value="+33">+44</option>
            <option value="+33">+61</option>
            <option value="+33">+82</option>
            <option value="+33">+55</option>
            <option value="+33">+52</option>
            <option value="+33">+7</option>
            <option value="+33">+39</option>
            <option value="+33">+34</option>
            <option value="+33">+86</option>
            <option value="+33">+91</option>
            <option value="+33">+49</option>
            <option value="+33">+33</option>
            <option value="+33">+81</option>
            <option value="+33">+886</option>
            <option value="+33">+852</option>
            <option value="+33">+64</option>
            <option value="+33">+996</option>
            <option value="+33">+971</option>
            <option value="+33">+90</option>
          </select>
          <input
            v-model="form.phone"
            type="tel"
            :placeholder="t('请输入电话号码')"
            required
            class="phone-input"
          />
        </div>

        <!-- 动态字段 -->
        <div class="form-group" v-for="(item, key) in fields" :key="key">
          <i :class="item.icon" class="input-icon"></i>
          <input
            v-model="form[key]"
            :type="item.type"
            :placeholder="t(item.placeholder)"
            required
          />
        </div>

        <!-- 注册按钮 -->
        <div class="btn-wrapper">
          <button type="submit" class="register-btn">{{ t("注册") }}</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { register } from "../../api/index.js";
import { useRouter } from "vue-router";
import { notify } from "../../utils/notify.js";
import { useLangStore } from "../../store/useLangStore.js";
import { useI18n } from "vue-i18n";

const { t, locale } = useI18n();
const router = useRouter();
const langStore = useLangStore();
const form = reactive({
  loginAccount: "",
  areaCode: "+86",
  phone: "",
  loginPassword: "",
  fundPassword: "",
  invitationCode: "",
});

// 动态渲染字段（不包含 loginAccount 和 phone）
const fields = {
  loginPassword: {
    placeholder: "请输入密码",
    type: "password",
    icon: "iconfont icon-lock",
  },
  fundPassword: {
    placeholder: "请输入确认密码",
    type: "password",
    icon: "iconfont icon-lock",
  },
  invitationCode: {
    placeholder: "请输入邀请码",
    type: "text",
    icon: "iconfont icon-invite",
  },
};

// 表单提交
function onSubmit() {
  for (const key in form) {
    if (!form[key]) {
      notify({
        title: t("提示"),
        message: t(`请填写${key}`),
        type: "error",
      });
      return;
    }
  }

  const payload = {
    ...form,
    phone: form.areaCode + form.phone, // 拼接完整手机号
  };

  register(payload).then((res) => {
    if (res.code === 200) {
      notify({ title: t("通知"), message: t("操作成功"), type: "success" });
      router.push("/login");
    } else {
      notify({ title: t("通知"), message: t(res.msg), type: "error" });
    }
  });
}

// 语言切换逻辑
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

.phone-group {
  display: flex;
  align-items: center;
  background: #c5c5c5;
  border-radius: 999px;
  border: 1px solid #181818;
  box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  position: relative;
}

.area-select {
  border: none;
  outline: none;
  background: transparent;
  padding: 12px 10px;
  font-size: 14px;
  appearance: none;
  width: 80px;
  text-align: center;
  color: #000;
  background-color: transparent;
}

.phone-input {
  flex: 1;
  padding: 12px;
  border: none;
  outline: none;
  font-size: 16px;
  background-color: transparent;
  color: #000;
}

.input-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  color: #888;
}

.register-footer {
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
  color: #fff;
  margin-bottom: 20px;
}

.language-selector {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 14px;
  color: #fff;
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
  color: #fff;
}
.dropdown-wrapper {
  position: relative;
  background: rgba(255, 255, 255, 0.15);
  padding: 5px 10px;
  border-radius: 6px;
  cursor: pointer;
  color: #fff;

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

.register-form {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.form-group {
  width: 100%;
  position: relative;
  margin-bottom: 16px;
  border: 1px solid #181818;
  box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
  border-radius: 999px;
  background: #c5c5c5;
}

.form-group input {
  width: 100%;
  padding: 12px 12px 12px 40px;
  border: 1px solid #ccc;
  border-radius: 999px;
  font-size: 16px;
  background: #c5c5c5;
  box-sizing: border-box;
}

.input-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  color: #888;
}

.register-btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  background-color: #1989fa;
  color: #fff;
  border: none;
  border-radius: 999px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.btn-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

@media (min-width: 768px) {
  .register-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    background: url("../../assets/img/login-bg.png") no-repeat center center;
    min-height: 100vh;
    width: 540px;
    margin: 0 auto;
    padding: 20px;
    box-sizing: border-box;
  }

  .register-form {
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
  }

  .form-group {
    width: 100%;
    position: relative;
    margin-bottom: 16px;
    border: 1px solid #181818;
    box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
    border-radius: 999px;
  }

  .form-group input {
    width: 100%;
    padding: 12px 12px 12px 40px;
    border: 1px solid #ccc;
    border-radius: 999px;
    font-size: 16px;
    background: #c5c5c5;
    box-sizing: border-box;
  }

  .input-icon {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 18px;
    color: #888;
  }

  .phone-group {
    display: flex;
    align-items: center;
    background: #c5c5c5;
    border-radius: 999px;
    border: 1px solid #181818;
    box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    position: relative;
  }

  .area-select {
    border: none;
    outline: none;
    background: transparent;
    padding: 12px 10px;
    font-size: 14px;
    appearance: none;
    width: 80px;
    text-align: center;
    color: #000;
    background-color: transparent;
  }

  .phone-input {
    flex: 1;
    padding: 12px;
    border: none;
    outline: none;
    font-size: 16px;
    background-color: transparent;
    color: #000;
  }

  .input-icon {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 18px;
    color: #888;
  }

  .register-footer {
    display: flex;
    justify-content: space-between;
    max-width: 500px;
    align-items: center;
    color: #fff;
    margin-bottom: 20px;
  }
  /* 语言选择器 */
  .language-selector {
    position: absolute;
    top: 20px;
    right: 38%;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #fff;
    z-index: 20;
  }
  .language-selector .label {
    margin-right: 6px;
    color: #000;
  }
  .dropdown-wrapper {
    position: relative;
    background: rgba(255, 255, 255, 0.15);
    padding: 5px 10px;
    border-radius: 6px;
    cursor: pointer;
    color: #000;

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

  .form-group {
    position: relative;
    margin-bottom: 20px;
  }

  .form-group input {
    width: 100%;
    padding: 12px 12px 12px 40px;
    border: 1px solid #ccc;
    border-radius: 999px;
    font-size: 16px;
    outline: none;
    background-color: #c5c5c5;
  }

  .input-icon {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 18px;
    color: #888;
  }

  .register-btn {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    background-color: #1989fa;
    color: #fff;
    border: none;
    border-radius: 999px;
    cursor: pointer;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  }

  .btn-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
}
</style>
