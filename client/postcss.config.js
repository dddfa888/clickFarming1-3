// postcss.config.js
export default {
    plugins: {
        'postcss-px-to-viewport': {
            viewportWidth: 375, // 你的设计稿宽度，如 Figma 是 375px
            unitPrecision: 5,
            viewportUnit: 'vw',
            selectorBlackList: ['.ignore'], // 排除某些类
            minPixelValue: 1,
            mediaQuery: false,
        },
    },
};
