<!-- src/components/TabBar.vue -->
<template>
    <div class="tabbar">
        <div v-for="(item, index) in tabs" :key="index" class="tabbar-item"
            :class="{ active: currentRoute === item.path }" @click="navigate(item.path)">
            <img :src="currentRoute === item.path ? item.iconActive : item.icon" class="tabbar-icon" />
            <span>{{ item.name }}</span>
        </div>
    </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const tabs = [
    {
        name: '首页',
        path: '/',
        icon: require('@/assets/tabbar/home.png'),
        iconActive: require('@/assets/tabbar/home-active.png'),
    },
    {
        name: '分类',
        path: '/category',
        icon: require('@/assets/tabbar/category.png'),
        iconActive: require('@/assets/tabbar/category-active.png'),
    },
    {
        name: '购物车',
        path: '/cart',
        icon: require('@/assets/tabbar/cart.png'),
        iconActive: require('@/assets/tabbar/cart-active.png'),
    },
    {
        name: '我的',
        path: '/profile',
        icon: require('@/assets/tabbar/user.png'),
        iconActive: require('@/assets/tabbar/user-active.png'),
    },
]

const currentRoute = route.path

const navigate = (path) => {
    if (path !== currentRoute) {
        router.push(path)
    }
}
</script>

<style scoped>
.tabbar {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 60px;
    background-color: #fff;
    display: flex;
    border-top: 1px solid #eee;
    z-index: 999;
}

.tabbar-item {
    flex: 1;
    text-align: center;
    padding: 5px 0;
    font-size: 12px;
    color: #999;
}

.tabbar-item.active {
    color: #409eff;
}

.tabbar-icon {
    width: 24px;
    height: 24px;
}
</style>