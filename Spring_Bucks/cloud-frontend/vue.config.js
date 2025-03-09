module.exports = {
    devServer: {
        proxy: {
            '/customer-service': {
                target: 'http://localhost:80', // 确保这里是80端口
                changeOrigin: true,
                pathRewrite: { '^/customer-service': '/customer-service' },
                logLevel: 'debug' // 可选，调试用
            },
            '/waiter-service': {
                target: 'http://localhost:80', // 确保这里是80端口
                changeOrigin: true,
                pathRewrite: { '^/waiter-service': '/waiter-service' },
                logLevel: 'debug' // 可选，调试用
            },
            '/api': {
                target: 'http://localhost:80',
                changeOrigin: true,
                pathRewrite: { '^/chat-service': '/chat-service' },
                logLevel: 'debug' // 可选，调试用
            }
        }
    }
}; 