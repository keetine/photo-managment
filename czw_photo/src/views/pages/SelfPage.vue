<template>
    <div class="container">
        <ChangePassword :isShow="change_password" @close="change_password=false"/>
        <div class="header">
            <!--头像-->
            <div class="avatar">
                <img :src="userInfo.avatar">
            </div>
        </div>
        <div class="operation">
            <!--昵称-->
            <div class="op-item" @click="edit">
                <img src="@/assets/icons/user_name.svg">
                <input class="nickName" type="text" v-model="userInfo.nickName" @blur="updateNickname">
            </div>
            <!--更换头像-->
            <div class="op-item" @click="changeAvatar">
                <img src="@/assets/icons/change_avater.svg">
                <input type="file" id="change_avater" style="display: none;">
                <span>更换头像</span>
            </div>
            <!--更改密码-->
            <div class="op-item" @click="change_password=true">
                <img src="@/assets/icons/password.svg">
                <span>更改密码</span>
            </div>
            <!--退出登录-->
            <div class="op-item" @click="logout">
                <img src="@/assets/icons/logout.svg">
                <span>退出登录</span>
            </div>
        </div>
    </div>
</template>
<script>
import {GetUserInfo, ChangeNickname,UploadAvatar} from "@/api/user";
import ChangePassword from './components/ChangePasswordBox.vue'
export default {
    name: 'SelfPage',
    components: {
        ChangePassword
    },
    data() {
        return {
            userInfo: {
                avatar: require('@/assets/avatar.jpg'),
            },
            change_password: false
        }
    },
    methods: {
        logout() {
            this.$router.push({ name: 'Login' })
            /** 清除本地存储 */
            sessionStorage.removeItem('store')
            /** 清空store */
            this.$store.commit("clear_data")
        },
        changeAvatar() {
            let file = document.getElementById('change_avater')
            file.click()
            file.onchange = () => {
                let formData = new FormData()
                formData.append('file', file.files[0])
                // 上传头像
                UploadAvatar(formData).then(res => {
                    if (res.code === 200) {
                        this.$message({
                            message: '上传成功',
                            type: 'success'
                        })
                        this.getUserInfo()
                    }
                },err=>{
                    let { data } = err.response
                    this.$message({
                        message: data.message,
                        type: 'error'
                    })
                })
            }
        },
        getUserInfo(){
            let user = this.$store.state.userInfo
            GetUserInfo(user.username).then(res => {
                this.userInfo = res.dataSet
                // console.log(res.dataSet);
            })
        },
        updateNickname(){
            // 如果昵称为空，则不更新
            if (this.userInfo.nickName === '') {
                return
            }
            // 更新昵称
            ChangeNickname(this.userInfo.nickName).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '更新成功',
                        type: 'success'
                    })
                    this.$store.commit('set_userInfo', this.userInfo)
                }
            }, err => {
                let {data} = err.response
                this.$message({
                    message: data.message,
                    type: 'error'
                })
            })
        },

    },
    created() {
        this.getUserInfo()
    }
}
</script>
<style scoped>
.avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    margin: 0 auto;
    margin-top: -20px;
    z-index: 2;
}

.avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.header {
    height: 260px;
    width: 100%;
    text-align: center;
    background: url(@/assets/templates/image.jpg) no-repeat center;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
}

.header::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
}

.op-item {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding: 10px 20px;
    background-color: #fff;
    margin-top: 1px;
}

.op-item:first-child {
    border-radius: 10px 10px 0 0;
}

.op-item:nth-child(3) {
    border-radius: 0 0 10px 10px;
}

.op-item:last-child {
    border-bottom: none;
    color: #ff7171;
    margin-top: 10px;
    border-radius: 10px;
    background-color: #fff;
}

.op-item img {
    width: 24px;
    height: 24px;
    margin-right: 20px;
}

.operation {
    position: relative;
    top: -40px;
    width: 92%;
    margin: 0 auto;
    padding: 10px 0;
}

.desc {
    width: 100%;
    text-align: center;
    color: rgb(166, 166, 166);
    padding: 10px 0;
    font-size: 12px;
    margin-top: 130px;
}

.nickName {
    border: none;
    outline: none;
    background-color: #fff;
    font-size: 16px;
    color: #000;
    width: 100%;
}

.nickName:focus {
    border-bottom: 1px solid rgb(113, 44, 198);
    padding-bottom: 4px;
}
</style>