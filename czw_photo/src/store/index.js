// store.js
import { createStore } from "vuex";

export default createStore({
    state: {
        layout_active: "Home",
        nav_show: true,
        isLogin: false,
        userInfo: {},
        token: "",
    },
    getters: {
        layout_active: state => state.layout_active,
        nav_show: state => state.nav_show,
        isLogin: state => state.isLogin,
        userInfo: state => state.userInfo,
        token: state => state.token,
    },
    mutations: {
        set_layout_active(state, payload) {
            state.layout_active = payload;
        },
        set_nav_show(state, payload) {
            state.nav_show = payload;
        },
        set_isLogin(state, payload) {
            state.isLogin = payload;
        },
        set_userInfo(state, payload) {
            state.userInfo = payload;
        },
        set_token(state, payload) {
            state.token = payload;
        },
        // 清空数据
        clear_data(state) {
            state.layout_active = "Home";
            state.nav_show = true;
            state.isLogin = false;
            state.userInfo = {};
        }
        
    }
});