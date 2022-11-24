<template>
    <div class="container">
        <div class="form">
            <div class="title">
                注册
            </div>
            <div class="input">
                <input type="text" placeholder="手机号/QQ号/邮箱" v-model="username">
            </div>
            <div class="input">
                <input type="password" placeholder="请输入密码" v-model="password">
            </div>
            <div class="input">
                <input type="password" placeholder="再次输入密码" v-model="confirmPassword">
            </div>
            <div class="button" @click="register">
                注册
            </div>
            <div class="goRegister">
                <div>
                    <span>注册即代表同意</span>
                    <router-link to="/login" class="link">《用户协议》</router-link>
                </div>
                <div>
                    <span>已有帐户？</span>
                    <router-link to="/login" class="link"> 去登录</router-link>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { Register } from '@/api/user'

// import { Register } from '@/api/user'
export default {
    name: 'LoginPage',
    data() {
        return {
            username: '',
            password: '',
            confirmPassword: ''
        }
    },
    mounted() {
    },
    methods: {
        register() {
            // this.$message({
            //     message: '登陆失败',
            //     type: 'error'
            // })
            // this.$store.commit('set_isLogin', true)
            // this.$router.push({ name: 'Self' })

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
            if (this.confirmPassword === '') {
                this.$message({
                    message: '请确认密码',
                    type: 'error'
                })
                return
            }
            if (this.password !== this.confirmPassword) {
                this.$message({
                    message: '两次密码不一致',
                    type: 'error'
                })
                return
            }
            Register({
                username: this.username,
                password: this.password
            }).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '注册成功',
                        type: 'success'
                    })
                    setTimeout(() => {
                        this.$router.push({ name: 'Login' })
                    }, 100)
                } else {
                    this.$message({
                        message: res.message,
                        type: 'error'
                    })
                }
            },
            err =>{
                let {data} = err.response
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
    background-image: linear-gradient(65deg, #ab6ee8, rgb(235, 110, 110));
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
    width: 72%;
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

.goRegister {
    width: 80%;
    margin: 0 auto;
    line-height: 30px;
    text-align: right;
    font-size: 1.2rem;
    padding: 10px;
    font-size: 14px;
}

.link {
    color: rgb(135, 11, 185);
    cursor: pointer;
    text-decoration: none;

}
</style>