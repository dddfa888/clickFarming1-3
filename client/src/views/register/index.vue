<template>
    <div class="register-container">
        <div class="language-selector">
            <span class="language">语言</span>
            <span class="country"></span>
        </div>

        <div class="register-form">
            <van-form @submit="onSubmit">
                <van-cell-group inset>
                    <van-field v-model="form.username" name="用户名" label="用户名" placeholder="请输入用户名" left-icon="user-o"
                        :rules="[{ required: true, message: '请填写用户名' }]" />
                    <van-field v-model="form.phone" name="手机号" label="手机号" placeholder="请输入手机号" left-icon="phone-o"
                        :rules="[
                            { required: true, message: '请填写手机号' },
                            { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确' }
                        ]" />
                    <van-field v-model="form.password" type="password" name="密码" label="密码" placeholder="请输入密码"
                        left-icon="lock" :rules="[{ required: true, message: '请填写密码' }]" />
                    <van-field v-model="form.confirmPassword" type="password" name="确认密码" label="确认密码"
                        placeholder="请再次输入密码" left-icon="lock" :rules="[
                            { required: true, message: '请确认密码' },
                            { validator: validateConfirmPassword, message: '两次密码不一致' }
                        ]" />
                    <van-field v-model="form.referralCode" name="推荐码" label="推荐码" placeholder="请输入推荐码（选填）"
                        left-icon="friends-o" />
                </van-cell-group>
                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        注册
                    </van-button>
                </div>
            </van-form>

            <div class="register-footer">
                <span>登录</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive } from 'vue'

const form = reactive({
    username: '',
    phone: '',
    password: '',
    confirmPassword: '',
    referralCode: '',
})

// 自定义校验：确认密码是否一致
function validateConfirmPassword() {
    return form.password === form.confirmPassword
}

function onSubmit(values) {
    console.log('注册提交成功：', values)
    // 这里可以调用注册接口
}
</script>

<style scoped>
.register-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    background: url('../../assets/img/login-bg.png') no-repeat center center;
    background-size: cover;
    min-height: 100vh;
    width: 100%;
    padding: 20px;
    box-sizing: border-box;
}

.language-selector {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 14px;
    color: #fff;
}

.register-form {
    /* background-color: rgba(255, 255, 255, 0.95); */
    padding: 30px 20px;
    border-radius: 8px;
    width: 100%;
    max-width: 400px;
    /* box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1); */
}

@media (min-width: 768px) {
    .register-container
    {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .register-form {
        padding: 40px;
        max-width: 400px;
       
    }

    .language-selector {
        font-size: 16px;
    }
}

.register-footer {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 20px;
    font-size: 14px;
    color: #fff;
    cursor: pointer;
}
</style>