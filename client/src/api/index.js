import request from "../utils/index"
const token = localStorage.getItem('token')

// 登录
export function login(data) {
    return request({
        url: '/api/login',
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

// 小组报告
export function getGroupReport() {
    return request({
        url: '/api/user/getUpToFourLevelInviters',
        method: 'get',
    })
}
// 提现
export function withdraw(data) {
    return request({
        url: '/api/withdraw/add',
        method: 'post',
        data
    })
}

// 获取个人的提现记录
export function getWithdrawRecord() {
    return request({
        url: '/api/withdraw/userList',
        method: 'get'
    })
}

// 获取个人账变记录
export function getDepositRecord() {
    return request({
        url: '/api/records/userList',
        method: 'get'
    })
}


// 获取用户个人信息
export function getUserInfo() {
    return request({
        url: '/api/user/userInfo',
        method: 'get',
    })
}

// 创建订单
export function createOrder(data) {
    return request({
        url: '/api/order/insertOrderByUser',
        method: 'post',
        data
    })
}

// 订单列表
export function getOrderList() {
    return request({
        url: '/api/order/listByUser',
        method: 'get',
    })
}

// 获取会员记录（包含当前的用户的等级）
export function getMemberRecord() {
    return request({
        url: '/api/grade/userList',
        method: 'get',
    })
}