import axios from "axios";

const request = axios.create({
    baseURL: "/api",
    timeout: 60000,
});

// 请求拦截器
request.interceptors.request.use(
    (config) => {
        config.headers['Authorization'] = localStorage.getItem("token")
        return config;
    },
    (error) => {
        // 对请求错误做些什么
        console.log("请求错误", error);
        return Promise.reject(error);
    }
);
// 响应拦截器
request.interceptors.response.use(
    (response) => {
        if (response.status === 200) {
            return response.data;
        }
        return response;
    },
    (error) => {
        // 获取错误状态码
        const { status } = error.response;
        if (status === 401) {
            alert("登录过期，请重新登录");
            localStorage.removeItem("token")
            window.location.href = "/login"
        }else if(status == 500){
            alert("服务器错误")
        }
        return Promise.reject(error);
    }
);

export default request;
