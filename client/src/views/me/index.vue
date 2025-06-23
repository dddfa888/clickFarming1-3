<template>
  <div class="user-center">
    <!-- 用户信息头部 -->
    <div class="user-header">
      <div class="avatar">
        <img src="../../assets/img/smc5jpzklmkircocfkq1.png" alt="" />
      </div>
      <div class="user-info">
        <p class="username">{{ userInfo.loginAccount }}</p>
        <p class="user-id">ID:</p>
      </div>
    </div>

    <!-- 会员级别 -->
    <div class="user-level-info">
      <div class="balance-info">
        <p class="balance-label">剩余</p>
        <p class="balance-amount">{{ userInfo.accountBalance }} €</p>
      </div>
      <div class="member-level">
        <p class="section-label">会员级别</p>
        <p class="level-name">{{ userInfo.levelName }}</p>
      </div>
    </div>

    <!-- 功能按钮区域 -->
    <div class="action-buttons">
      <!-- <button class="action-btn" @click="handleAction('withdraw')">提款</button> -->
      <button class="action-btn" @click="handleAction('withdraw')">提现</button>
      <button class="action-btn" @click="handleAction('withdrawHistory')">
        提款记录
      </button>
      <button class="action-btn" @click="handleAction('depositHistory')">
        存款记录
      </button>
      <button class="action-btn" @click="handleAction('orderHistory')">
        订单历史
      </button>
      <button class="action-btn" @click="handleAction('rewardHistory')">
        奖励历史记录
      </button>
      <button class="action-btn" @click="handleAction('groupReport')">
        小组报告
      </button>
      <button class="action-btn" @click="handleAction('bankInfo')">
        银行信息
      </button>
      <button class="action-btn" @click="handleAction('address')">地址</button>
      <button class="action-btn" @click="handleAction('language')">语言</button>
      <button class="language-btn" @click="toggleDropdown">
        {{ selectedLanguage }}
      </button>

      <!-- 语言选择下拉框 -->
      <ul v-if="showDropdown" class="language-dropdown">
        <li
          v-for="lang in languageOptions"
          :key="lang"
          @click.stop="selectLanguage(lang)"
          :class="{ active: lang === selectedLanguage }"
        >
          {{ lang }}
        </li>
      </ul>
    </div>

    <!-- 登出按钮 -->

    <button class="logout-btn" @click="handleLogout">登出</button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { useLangStore } from "../../store/useLangStore";
import { storeToRefs } from "pinia";
import { getUserInfo } from "../../api/index";

const router = useRouter();
const { locale: i18nLocale } = useI18n();
const userInfo = ref({});
const langStore = useLangStore();
const { locale: langStoreLocale } = storeToRefs(langStore);

i18nLocale.value = langStoreLocale.value; // 同步 i18n

const langMap = {
  中国: "zh",
  越南语: "vi",
};
const reverseLangMap = Object.fromEntries(
  Object.entries(langMap).map(([k, v]) => [v, k])
);

const selectedLanguage = ref(reverseLangMap[langStoreLocale.value] || "中国");
const languageOptions = Object.keys(langMap);
const showDropdown = ref(false);

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

const selectLanguage = (lang) => {
  selectedLanguage.value = lang;
  const langCode = langMap[lang] || "zh";
  langStore.setLocale(langCode);
  i18nLocale.value = langCode;
  showDropdown.value = false;
};

getUserInfo().then((res) => {
  userInfo.value = res.data;
});
</script>

<style scoped>
.user-center {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  background: url("../../assets/img/BG-canhan-B0_5I1_l.png") no-repeat center
    center fixed;
  background-size: cover;
  padding-bottom: 120px;
  min-height: 100vh;
}

.user-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 5px;
}

.user-info {
  color: #fff;
  font-size: 12px;
}

.balance-label {
  margin: 0;
  font-size: 14px;
  color: #fff;
}

.balance-amount {
  margin: 5px 0 0;
  font-weight: bold;
  color: #fff;
}

.section-label {
  margin: 0 0 5px;
  font-size: 14px;
  color: #fff;
}

.avatar > img {
  width: 50px;
  height: 50px;
}

.level-name {
  margin: 0;
  color: #fff;
}

.action-buttons {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-bottom: 25px;
}

.action-btn {
  padding: 8px;
  font-size: 14px;
  color: #fff;
  text-align: left;
  border: none;
  box-sizing: border-box;
  border-top: 1px solid #616272;
  cursor: pointer;
  transition: all 0.2s;
  background-color: transparent;
}

.language-btn {
  width: 30%;
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: span 2;
  background-color: #0d324f;
  color: #fff;
  padding: 4px 8px;
  font-size: 14px;
  border: none;
  border-radius: 10px;
}

.language-arrow {
  margin-left: 5px;
  font-size: 12px;
}

.logout-btn {
  width: 20%;
  margin-left: 40%;
  padding: 10px;
  background-color: #5b0606;
  border: none;
  border-radius: 6px;
  font-size: 12px;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

@media (max-width: 480px) {
  .action-buttons {
    grid-template-columns: 1fr;
  }

  .language-btn {
    grid-column: span 1;
  }
}
.user-level-info {
  width: 200px;
  font-size: 12px;
  display: flex;
  padding: 17px;
  align-items: center;
  justify-content: space-around;
  border: 1px solid #ccc;
  border-radius: 20px;
  text-align: center;
  margin: 0 auto;
  margin-bottom: 15px;
}

.language-dropdown {
  list-style: none;
  padding: 0;
  margin-top: 461px;
  background: #0b1e34;
  color: white;
  border: 1px solid #ccc;
  border-radius: 6px;
  position: absolute;
  width: 100px;
  z-index: 100;
}

.language-dropdown li {
  padding: 8px;
  cursor: pointer;
  font-size: 14px;
}

.language-dropdown li:hover,
.language-dropdown li.active {
  background-color: gray;
}
</style>
