import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Message from './utils/components/Message'
import store from './store'
// import vconsole from 'vconsole'

// new vconsole()

router.beforeEach((to, from, next) => {
    document.title = to.meta.title
    // 检查是否已经登录
    if (to.name !== 'Login' && !store.state.isLogin && to.name !== 'Register') {
        next({ name: 'Login' })
    } else {

        next()
    }
})

const app = createApp(App)

app.use(store)
app.use(router)

app.config.globalProperties.$message = Message

app.mount('#app')
