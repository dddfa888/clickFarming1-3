import { createWebHashHistory, createRouter } from 'vue-router'

const routes = [
    { path: '/', name: 'Home', component: ()=> import('../views/index/index.vue') },
  { path: '/login', name: 'login', component: ()=> import('../views/login/index.vue') },
  { path: '/register', name: 'register', component: ()=> import('../views/register/index.vue') },
  { path: '/market', name: 'market', component: ()=> import('../components/HelloWorld.vue') },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router