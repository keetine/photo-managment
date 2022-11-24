<template>
    <div class="container" v-show="isShow">
        <div class="item" :class="{ active: active === 'Home' }" @click="nav('Home')">
            <div class="icon">
                <img :src="active === 'Home' ? require('@/assets/icons/home_active.svg') : require('@/assets/icons/home.svg')"
                    alt="Home">
                <span>首页</span>
            </div>
        </div>
        <div class="item" :class="{ active: active === 'Photos' }" @click="nav('Photos')">
            <div class="icon">
                <img :src="active === 'Photos' ? require('@/assets/icons/photo_active.svg') : require('@/assets/icons/photo.svg')"
                    alt="Photo">
                <span>相册</span>
            </div>
        </div>
        <div class="item" :class="{ active: active === 'Self' }" @click="nav('Self')">
            <div class="icon">
                <img :src="active === 'Self' ? require('@/assets/icons/self_active.svg') : require('@/assets/icons/self.svg')"
                    alt="Self">
                <span>我的</span>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'LayoutNavigation',
    data() {
        return {
            active: 'Home',
            isShow: true
        }
    },
    methods: {
        nav(page) {
            this.active = page
            this.$store.commit('set_layout_active', page)
            this.$router.push({ name: page })
        },
        // 检查当前路由是否需要显示导航栏
        checkRoute() {
            const route = this.$route.name
            if (route !== 'Home' && route !== 'Photos' && route !== 'Self') {
                this.isShow = false
                this.$store.commit('set_nav_show', false)
            } else {
                this.isShow = true
                this.$store.commit('set_nav_show', true)
            }
        }
    },
    /** 每次刷新检查当前路由 */
    mounted() {
        this.checkRoute()
        this.active = this.$store.getters['layout_active']
    },
    watch: {
        /** 每次路由变化检查当前路由 */
        $route() {
            this.checkRoute()
            this.active = this.$route.name
        }
    }
}

</script>
<style scoped>
.hide {
    display: none;
}

.container {
    background-color: #f5f5f5;
    width: 100%;
    height: 64px;
    position: fixed;
    left: 0;
    bottom: 0;
    display: flex;
    justify-content: space-around;
    align-items: center;
    border-top: 1px solid #ddd;
    box-sizing: border-box;
    color: #999;
}

.icon {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.icon img {
    width: 26px;
    height: 26px;
}

.icon span {
    margin-top: 2px;
    font-size: .7rem;
}

.active {
    color: #7b00d8;
}
</style>