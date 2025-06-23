// src/i18n.js
import { createI18n } from 'vue-i18n'
import zh from './locales/zh';
import en from './locales/en';
import vi from './locales/vi';

const i18n = createI18n({
    legacy: false,
    globalInjection: true,
    locale: localStorage.getItem('locale') || 'zh', // 默认中文
    messages: {
        zh,
        en,
        vi,
    },
});

export default i18n;
