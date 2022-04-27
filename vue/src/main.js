// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable */
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import axios from 'axios'

// 创建 axios 实例
const service = axios.create({
  baseURL: '',
  timeout: 20000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})
// 将请求数据转换成功 formdata 接收格式
// service.defaults.transformRequest = (data) => {
//   return stringify(data)
// }

axios.default.baseURL = '/api/'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

// Element-ui 引入
Vue.use(ElementUI)

// 全局路由权限钩子函数
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
NProgress.configure({ showSpinner: false })// NProgress Configuration

// permission judge function
function hasPermission (role, permissionRoles) {
  if (!role) return false
  if (role.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  console.log(role)
  return (permissionRoles.indexOf(role) >= 0)
}

// const whiteList = ['/login', '/authredirect']// no redirect whitelist

router.beforeEach((to, from, next) => {
  console.log(store.getters.role)
  NProgress.start() // start progress bar
  if (to.meta.requireAuth) {
    console.log(store.getters.role+'|'+to.meta.roles)
    if (hasPermission(store.getters.role, to.meta.roles)) {
      next()//
    } else {
      var toPath = to.meta.toPath
      next({path: toPath === undefined ? '/login' : toPath, replace: true, query: { redirect: to.fullPath }})
    }
  } else{
    next()
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
