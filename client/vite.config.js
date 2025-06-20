import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    host: '127.0.0.1', // 或写成 'localhost'
    port: 8080,         // 自定义端口为 8080
    open: true,          // 自动打开浏览器（可选）
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080/', // 
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, '')
        // rewrite: (path) => path.replace(/^\/api/, '') // 重写路径，移除'/api',
      }
    }
  }
})
