<template>
    <div class="container" v-show="isShow">
        <div class="box">
            <div class="close" @click="close">
                ×
            </div>
            <div class="title">
                修改密码
            </div>
            <div class="content">
                <div class="item">
                    <span>原密码</span>
                    <input type="password" v-model="oldPassword" placeholder="请输入原密码">
                </div>
                <div class="item">
                    <span>新密码</span>
                    <input type="password" v-model="newPassword" placeholder="请输入新密码">
                </div>
                <div class="item">
                    <span>确认密码</span>
                    <input type="password" v-model="confirmPassword" placeholder="请再次输入新密码">
                </div>
                <div class="item">
                    <button class="submit" @click="submit">提交</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { ChangePassword } from '@/api/user'
export default {
    name: 'ChangePasswordBox',
    props: {
        isShow: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            oldPassword:'',
            newPassword:'',
            confirmPassword:''
        }
    },
    methods:{
        close() {
            this.$emit('close')
        },
        submit() {
            // 表单验证
            if (this.oldPassword === '') {
                this.$message({
                    message: '请输入原密码',
                    type: 'error'
                })
                return
            }
            if (this.newPassword === '') {
                this.$message({
                    message: '请输入新密码',
                    type: 'error'
                })
                return
            }
            if (this.confirmPassword === '') {
                this.$message({
                    message: '请再次输入新密码',
                    type: 'error'
                })
                return
            }
            if (this.newPassword !== this.confirmPassword) {
                this.$message({
                    message: '两次输入的密码不一致',
                    type: 'error'
                })
                return
            }
            // 提交
            ChangePassword(this.oldPassword, this.newPassword).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '修改成功',
                        type: 'success'
                    })
                    this.close()
                }
            },
            err => {
                let {data} = err.response
                this.$message({
                    message: data.message,
                    type: 'error'
                })
            })
            this.oldPassword = ''
            this.newPassword = ''
            this.confirmPassword = ''
            this.$emit('submit')
        }
    },
    mounted() {

    }
}
</script>
<style scoped>
.container{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.5);
    z-index: 999;
}
.box{
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 80%;
    height: 300px;
    background-color: #fff;
    border-radius: 10px;
    padding: 20px;
}
.close{
    position: absolute;
    top: -36px;
    right: 0;
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 26px;
    cursor: pointer;
    border-radius: 50%;
    border: 1px solid #fff;
    color: #fff;
}
.title{
    text-align: left;
    font-size: 18px;
    color: #333;
    margin-bottom: 20px;
}
.content{
    width: 100%;
    height: 100%;
}
.item{
    width: 100%;
    height: 40px;
    line-height: 40px;
    margin-bottom: 20px;
    display: flex;
}
.item span{
    display: inline-block;
    width: 80px;
    text-align: right;
    padding-right: 10px;
    font-size: 14px;
    color: #333;
}
.item input{
    display: inline-block;
    width: 200px;
    height: 100%;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 0 10px;
    font-size: 14px;
    color: #333;
    transition-duration: .4s;
}
.item input:focus{
    outline: none;
    box-shadow: violet 0 1px 2px 0px;
    transition-duration: .2s;
}
button{
    width: 100%;
    height: 100%;
    border: none;
    border-radius: 5px;
    background-color: rgb(139, 45, 221);
    color: #fff;
    font-size: 14px;
    margin-top: 20px;
}
</style>