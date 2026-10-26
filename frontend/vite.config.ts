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
        manualChunks(id: string) {
          if (!id.includes('node_modules')) return undefined
          const p = id.replace(/\\/g, '/')
          if (p.includes('/element-plus/') || p.includes('/@element-plus/')) return 'vendor-element'
          if (p.includes('/@vue/') || p.includes('/vue-router/') || p.includes('/pinia/')) return 'vendor-vue'
          return 'vendor'
        },
      },
    },
  },
})
