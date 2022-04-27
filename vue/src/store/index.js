import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      role: 'none',
      username: 'none'
    },
    isLogin: false
  },
  getters: {
    role: state => state.user.role,
    username: state => state.user.username
  },
  mutations: {
    login (state, user) {
      state.user.role = (user.type === undefined) ? user.role : user.type
      state.user.username = (user.username === undefined) ? user.number : user.username
      state.isLogin = true
    },
    logout (state) {
      state.user = {
        role: 'none',
        username: 'none'
      }
      state.isLogin = false
    }
  },
  actions: {
    // login (context) {
    //   context.commit('login')
    // },
    logout (context) {
      context.commit('logout')
    }
  }
})
