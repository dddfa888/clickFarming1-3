import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import Vant from 'vant'
import 'vant/lib/index.css'
import router from './router'
// 持久化
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)
const pinia = createPinia()
// 注册持久化插件
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.use(Vant)
app.mount('#app')
