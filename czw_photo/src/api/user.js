import request from "@/utils/request";
// /user/register
// 注册
export function Register(data) {
    return request({
        url: "/user/register",
        method: "post",
        data
    });
}

// 登录
export function Login(data) {
    return request({
        url: "/user/login",
        method: "post",
        data,
        headers: {
            "Content-Type": "application/json",
        },
    });
}
// 获取用户信息
export function GetUserInfo(username) {
    return request({
        url: "/user/info/"+username,
        method: "get"
    });
}

// 修改密码
export function ChangePassword(oldPassword,newPassword) {
    return request({
        url: "/user/changePassword",
        method: "put",
        params: {
            oldPassword,
            newPassword
        }
    });
}
// 修改昵称
export function ChangeNickname(nickname) {
    return request({
        url: "/user/updateNickname",
        method: "put",
        params: {
            nickname
        }
    });
}

// 修改头像
export function UploadAvatar(data) {
    return request({
        url: "/user/updateAvatar",
        method: "put",
        data,
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
}