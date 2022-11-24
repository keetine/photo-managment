// router.js
import { createRouter,createWebHistory } from "vue-router";
import Layout from "@/views/layout/index.vue";

const routes = [
    {
        path: "/",
        name: "Layout",
        component: Layout,
        children: [
            {
                path: "/",
                name: "Home",
                component: () => import("@/views/pages/HomePage.vue"),
                meta: {
                    title: "首页"
                }
            },
            {
                path: "/photos",
                name: "Photos",
                component: () => import("@/views/pages/PhotosPage.vue"),
                meta: {
                    title: "相册"
                }
            },
            {
                path: "/self",
                name: "Self",
                component: () => import("@/views/pages/SelfPage.vue"),
                meta: {
                    title: "账户"
                }
            },
            {
                path: "/upload",
                name: "Upload",
                component: () => import("@/views/pages/UploadFilePage.vue"),
                meta: {
                    title: "上传"
                }
            },
            {
                path: "/category",
                name: "Category",
                component: () => import("@/views/pages/CategoryPage.vue"),
                meta: {
                    title: "分类"
                }
            },
            {
                path: "/login",
                name: "Login",
                component: () => import("@/views/pages/LoginPage.vue"),
                meta: {
                    title: "登录"
                }
            },
            {
                path: "/register",
                name: "Register",
                component: () => import("@/views/pages/RegisterPage.vue"),
            }
        ]
    },
];

const router = createRouter({
    // 地址栏不显示#
    history: createWebHistory(),
    routes
});

export default router;

