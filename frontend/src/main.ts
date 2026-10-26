import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { provideGlobalConfig } from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
// 编程式调用的三个组件不经过模板解析，样式需手动引入
import 'element-plus/es/components/message/style/css'
import 'element-plus/es/components/message-box/style/css'
import 'element-plus/es/components/loading/style/css'
import './style.css'
import './demo.css'
import './role.css'
import './theme.css'
import App from './App.vue'
import router from './router'

const app = createApp(App)

// 等价于 app.use(ElementPlus, { locale: zhCn }) 的配置注入，但不注册任何组件
provideGlobalConfig({ locale: zhCn }, app, true)

app.use(createPinia()).use(router).mount('#app')
