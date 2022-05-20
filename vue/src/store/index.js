import { off } from 'node-notifier'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      role: 'none',
      username: 'none'
    },
    round: off,
    isLogin: false
  },
  getters: {
    round: state => state.round,
    role: state => state.user.role,
    username: state => state.user.username
  },
  mutations: {
    setRound (state, round) {
      state.round = round
    },
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
