import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

const backendProxy = {
  '/api': {
    target: 'http://127.0.0.1:8080',
    changeOrigin: true,
    rewrite: (path: string) => path.replace(/^\/api/, ''),
  },
}

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // Element Plus 按需自动引入：模板里的 el-* 组件与 v-loading 指令在此解析
    Components({
      dts: 'src/types/components.d.ts',
      dirs: [],
      resolvers: [ElementPlusResolver({ importStyle: 'css' })],
    }),
  ],
  server: {
    host: '127.0.0.1',
    port: 5173,
    proxy: backendProxy,
  },
  preview: {
    host: '127.0.0.1',
    port: 4173,
    proxy: backendProxy,
  },
  build: {
    rollupOptions: {
      output: {
        // Vite 8 (Rolldown) 不再支持 manualChunks 函数写法，改用 advancedChunks 分组
        advancedChunks: {
          groups: [
            { name: 'vendor-vue', test: /node_modules[\\/](vue|vue-router|pinia|@vue)[\\/]/ },
            { name: 'vendor-element', test: /node_modules[\\/](element-plus|@element-plus)[\\/]/ },
            { name: 'vendor', test: /node_modules[\\/]/ },
          ],
        },
      },
    },
  },
})
