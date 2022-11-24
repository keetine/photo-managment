const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy: {
      '/api': {
        target: 'http://localhost:8888/api/',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    },
    host: '0.0.0.0',
    port: 80,
  }
})
