<template>
    <div>
        <LayoutMain :class="{'main_2':have_nav,'main_1':!have_nav}" />
        <LayoutNavigation />
    </div>
</template>
<script>
import LayoutNavigation from './components/LayoutNavigation.vue'
import LayoutMain from './components/LayoutMain.vue'
export default {
    name: 'MainLayot',
    components: {
        LayoutNavigation,
        LayoutMain
    },
    data() {
        return {
            have_nav: true
        }
    },
    methods: {
        checkRoute() {
            const route = this.$route.name
            if (route !== 'Home' && route !== 'Photos' && route !== 'Self') {
                this.have_nav = false
            } else {
                this.have_nav = true
            }
        }

    },
    watch: {
        $route() {
            this.checkRoute()
        }
    },
}
</script>
<style scoped>
.main_1 {
    height: 96vh;
    overflow-y: scroll;
}

.main_2 {
    height: calc(96vh - 64px);
    overflow-y: scroll;
}

@media screen and (max-width: 768px) {
    .main_1 {
        height: 100vh;
    }

    .main_2 {
        height: calc(100vh - 64px);
    }
}

/** 自定义scroll */
.main::-webkit-scrollbar {
    -webkit-box-shadow: inset 0 0 6px rgba(104, 120, 115, 0.3);
    border-radius: 0 10px 10px 0;
    background-color: #fff;
    width: 4px;
}

.main::-webkit-scrollbar-thumb {
    border-radius: 6px;
    -webkit-box-shadow: inset 0 0 6px rgba(91, 255, 167, 0.5);
    background-color: #bdbfc1;
}
</style>