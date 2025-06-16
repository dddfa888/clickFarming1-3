<template>
   <div>
    <RouterView/> 
    <TabBar v-if="showTabbar" />
   </div>
</template>
<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import TabBar from './components/TabBar.vue'

const route = useRoute()
const showTabbar = computed(() =>
  ['/', '/category', '/cart', '/profile'].includes(route.path)
)
</script>

<style scoped>
html, body, #app {
  margin: 0;
  padding: 0;
  overflow: hidden;
  height: 100%;
  width: 100%;
}

</style>
