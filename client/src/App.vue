<template>
  <div>
    <RouterView />
    <!-- 只在指定页面显示 TabBar -->
    <TabBar v-if="showTabBar" />
  </div>
</template>
<script setup>
import { useRoute } from "vue-router";
import { computed } from "vue";
import TabBar from "./components/TabBar.vue";

const route = useRoute();
const tabbarPaths = [
  "/",
  "/warehouse",
  "/cskh",
  "/me",
  "/company",
  "/rule",
  "/cooperation",
  "/notice",
  "/withdraw",
  "/orderHistory",
  "/orderdetail",
  "/address",
  "/bankInfo",
  "/groupReport",
  "/rewardHistory",
  "/depositHistory",
  "/withdrawHistory",
];

const showTabBar = computed(() => tabbarPaths.includes(route.path));
</script>

<style scoped>
html,
body,
#app {
  margin: 0;
  padding: 0;
  overflow: hidden;
  height: 100vh;
  width: 100vw;
}

body,
html,
* {
  cursor: default;
}
</style>
