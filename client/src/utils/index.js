// request.js
import axios from 'axios';
import router from '../router/index';
import { showDialog } from 'vant';

let isTokenExpired = false;
// const baseURL = "http://192.168.0.198:8089/"
const baseURL = "https://cfapi.khkjhkh.top/"
const request = axios.create({
    baseURL: import.meta.env.MODE === 'development'
        ? '/api'
        : 'https://cfapi.khkjhkh.top/',
    // baseURL: baseURL,
    timeout: 10000
})

//请求拦截器：每次请求前都从 localStorage 获取最新 token
request.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// 响应拦截器：统一处理 token 失效
request.interceptors.response.use(
    (response) => {
        const res = response.data;
        if ((res.code === 401 || res.message === 'token失效') && !isTokenExpired) {
            isTokenExpired = true;
            showDialog({
                title: '提示',
                message: '登录已过期，请重新登录',
            }).then(() => {
                localStorage.removeItem('token');
                router.replace({ path: '/login' });
                isTokenExpired = false;
            });
        }
        return res;
    },
    (error) => {
        if (error.response && error.response.status === 401 && !isTokenExpired) {
            isTokenExpired = true;
            showDialog({
                title: '提示',
                message: '登录已过期，请重新登录',
            }).then(() => {
                localStorage.removeItem('token');
                router.replace({ path: '/login' });
                isTokenExpired = false;
            });
        }
        return Promise.reject(error);
    }
);

export default request;
