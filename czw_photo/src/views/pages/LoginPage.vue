<template>
    <div class="container">
        <div class="form">
            <div class="title">
                登录
            </div>
            <div class="input">
                <input type="text" placeholder="请输入用户名" v-model="username">
            </div>
            <div class="input">
                <input type="password" placeholder="请输入密码" v-model="password">
            </div>
            <div class="button" @click="login">
                登录
            </div>
            <div class="goRegister">
                <span>还没有账户？</span><router-link to="/register" class="link">去注册</router-link>
            </div>
        </div>
    </div>
</template>
<script>
import { Login } from '@/api/user'
export default {
    name: 'LoginPage',
    data() {
        return {
            username: '',
            password: ''
        }
    },
    mounted() {
    },
    methods:{
        login(){
            // 表单验证
            if (this.username === '') {
                this.$message({
                    message: '请输入用户名',
                    type: 'error'
                })
                return
            }
            if (this.password === '') {
                this.$message({
                    message: '请输入密码',
                    type: 'error'
                })
                return
            }
            // 登录
            Login({
                username: this.username,
                password: this.password
            }).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    })
                    this.$store.commit('set_isLogin', true)
                    localStorage.setItem('token', res.dataSet.token)
                    this.$store.commit('set_userInfo', {
                        username: this.username
                    })
                    this.$router.push({ name: 'Self' })
                }
            },err=>{
                let { data } = err.response
                this.$message({
                    message: data.message,
                    type: 'error'
                })
            })
        }
    }
}
</script>
<style scoped>
.container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    background-image: linear-gradient(65deg,  #ab6ee8, rgb(235, 110, 110));
}
.form {
    width: 80%;
    background: white;
    border-radius: 10px;
}
.title {
    height: 40px;
    line-height: 40px;
    text-align: left;
    font-size: 1.6rem;
    padding: 10px;  
}
.input {
    width: 80%;
    margin: 0 auto;
    height: 40px;
    line-height: 40px;
    text-align: left;
    font-size: 1.2rem;
    padding: 10px;  
}
.input input {
    border: none;
    border-bottom: 1px solid rgb(190, 190, 190);
    outline: none;
    box-sizing: border-box;
    width: 100%;
    padding: 0 10px;
    height: 40px;
    font-size: 16px;
}
.input input:focus {
    border-bottom: 1px solid rgb(135, 11, 185);
}
.button {
    width:72%;
    margin: 20px auto;
    text-align: center;
    font-size: 1.2rem;
    padding: 10px;  
    background: rgb(135, 11, 185);
    color: white;
    border-radius: 5px;
    cursor: pointer;
    transition-duration: .4s;
}
.button:active {
    background: rgb(135, 11, 185);
    opacity: 0.8;
    transition-duration: .4s;
}
.goRegister{
    width: 80%;
    margin: 0 auto;
    line-height: 40px;
    text-align: right;
    font-size: 1.2rem;
    padding: 10px;  
    font-size: 14px;
}
.link{
    color: rgb(135, 11, 185);
    cursor: pointer;
    text-decoration: none;

}
.link a{
    text-decoration: none;
}
</style>