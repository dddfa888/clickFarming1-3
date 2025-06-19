import { computed } from 'vue'
import { createWebHashHistory, createRouter } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('../views/index/index.vue') },
  { path: '/login', name: 'login', component: () => import('../views/login/index.vue') },
  { path: '/register', name: 'register', component: () => import('../views/register/index.vue') },
  { path: '/market', name: 'market', component: () => import('../components/HelloWorld.vue') },
  { path: '/warehouse', name: 'warehouse', component: () => import('../views/warehouse/index.vue') },
  { path: '/me', name: 'me', component: () => import('../views/me/index.vue') },
  { path: '/company', name: 'company', component: () => import('../views/index/Company.vue') },
  { path: '/rule', name: 'rule', component: () => import('../views/index/rule.vue') },
  { path: '/cooperation', name: 'cooperation', component: () => import('../views/index/cooperation.vue') },
  { path: '/notice', name: 'notice', component: () => import('../views/index/notice.vue') },
  { path: '/withdraw', name: 'withdraw', component: () => import('../views/me/withdraw.vue') },
  { path: '/depositHistory', name: 'depositHistory', component: () => import('../views/me/deposit.vue') },
  { path: '/orderHistory', name: 'orderHistory', component: () => import('../views/me/order.vue') },
  { path: '/rewardHistory', name: 'rewardHistory', component: () => import('../views/me/reward.vue') },
  { path: '/groupReport', name: 'groupReport', component: () => import('../views/me/groupReport.vue') },
  { path: '/bankInfo', name: 'bankInfo', component: () => import('../views/me/bankInfo.vue') },
  { path: '/address', name: 'address', component: () => import('../views/me/address.vue') },
  { path: '/orderdetail', name: 'orderdetail', component: () => import('../views/index/orderdetail.vue') },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router