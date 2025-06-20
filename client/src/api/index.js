import request from "../utils/index"

// 登录
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data
    })
}

// 注册
export function register(data) {
    return request({
        url: '/api/register',
        method: 'post',
        data
    })
}

