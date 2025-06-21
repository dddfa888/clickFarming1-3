<template>
  <div class="company-intro">
    <HeaderBar title="团体" />
    <!-- Tab 导航栏 -->
    <div class="tab-header">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        :class="['tab-item', { active: activeTab === index }]"
        @click="activeTab = index"
      >
        {{ tab }}
      </div>
    </div>

    <!-- 内容区域（不同布局） -->
    <div class="tab-content">
      <div v-if="activeTab === 0">
        <div class="group-report">
          <div class="group-member-item">
            <img src="../../assets/img/3-DHl9k9P6.png" alt="" />
            <div>
              <p>Linh199</p>
              <p>剩余: 0,00 €</p>
            </div>
          </div>
          <div>
            <p>SĐT: 191619018917</p>
            <p>注册时间: 2025-06-12</p>
          </div>
        </div>
      </div>

      <div v-else-if="activeTab === 1">1</div>

      <div v-else-if="activeTab === 2">2</div>
      <div v-else-if="activeTab === 3">3</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { getGroupReport } from "../../api/index.js";
import HeaderBar from "../../components/HeaderBar.vue";

// 定义 tab 数据
const tabs = ref(["第1级", "第2级", "第3级", "第4级"]);
const activeTab = ref(0); // 当前激活的 tab 下标
const grouplist = ref([]);

getGroupReport().then((res) => {
  console.log(res.data);
  grouplist.value = res.data;
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

.tab-header {
  display: flex;
  border: 1px solid #fff;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 10px;
  cursor: pointer;
  border: 1px solid #fff;
  color: #fff;
  transition: 0.3s;
}

.tab-item.active {
  color: #3b82f6;
}

.tab-content {
  border-radius: 6px;
  margin-top: 10px;
}

.group-report {
  padding: 10px;
  display: flex;
  color: #fff;
  font-size: 10px;
  align-items: center;
  justify-content: space-between;
}

.group-member-item {
  display: flex;
  align-items: center;
}

.group-member-item > img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
</style>
