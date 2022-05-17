/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'

import AppIndex from '@/components/home/AppIndex'
import Home from '@/components/home/Home'

import CommonLogin from '@/components/login/commonLogin'
import AdminLogin from '@/components/login/adminLogin'

import Register from '@/components/admin/users/register'
import Adm from '@/components/admin/admin'

import User from '@/components/user/user'
import ChangePassword from '@/components/user/changePassword'

Vue.use(Router)

export const constantRouterMap = [
  {path: '/', redirect: '/home'},
  {
    path: '/home',
    name: 'Home',
    component: Home,
    // home页面并不需要被访问
    children: [
      {
        path: '/',
        name: 'AppIndex',
        component: AppIndex,
      }
    ]
  },
  { path: '/404', name:'404', component: () => import('@/components/home/404'), hidden: true },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/login/Login'),
    children: [
      {
        path: '',
        name: 'login',
        component: () => import('@/components/login/index')
      },
      {
        path: 'commonLogin',
        name: 'commonLogin',
        component: CommonLogin
      },
      {
        path: 'adminLogin',
        name: 'adminLogin',
        component: AdminLogin
      }
    ]
  },
  {
    path: '/user',
    name: 'user',
    component: User,
    meta: {
      requireAuth: true,
      roles: ['admin','teacher','student']
    },
    children: [
      {
        path: 'teacher',
        name: 'teacher',
        component: () => import('@/components/user/teacher/teacher'),
        meta: {
          requireAuth: true,
          roles: ['admin','teacher'],
          toPath: '/login'
        },
      },
      {
        path: 'student',
        name: 'student',
        component: () => import('@/components/user/student/student'),
        meta: {
          requireAuth: true,
          roles: ['admin','student'],
          toPath: '/login'
        },
      }
    ]
  },
  {
    path: '/changePassword',
    name: 'changePassword',
    component: ChangePassword,
    meta: {
      requireAuth: true,
      roles: ['admin','teacher','student'],
      toPath: '/login'
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: Adm,
    meta: {
      requireAuth: true,
      roles: ['admin'],
      toPath: '/login'
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
    meta: {
      requireAuth: true,
      roles: ['admin'],
      toPath: '/login'
    }
  },
  // 在这之上添加新路由，保证notFound跳转404在最后
  {
    path: '*',
    redirect: {path: '/404'}
  }
]

export default new Router({
  mode: "history",
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

// export default new Router({
//   mode: "history",
//   routes: [
//     {
//       path: '/',
//       name: 'AppIndex',
//       component: AppIndex
//     },
//     {
//       path: '/404',
//       name: '404',
//       component: () => import('@/components/home/404')
//     },
//     {
//       path: '/user',
//       name: 'user',
//       component: User,
//       meta: {
//         requireAuth: true,
//         roles: ['admin','teacher','student']
//       }
//     },
//     {
//       path: '/changePassword',
//       name: 'changePassword',
//       component: ChangePassword,
//       meta: {
//         requireAuth: true,
//         roles: ['admin','teacher','student']
//       }
//     },
//     {
//       path: '/admin',
//       name: 'admin',
//       component: Adm,
//       meta: {
//         requireAuth: true,
//         roles: ['admin']
//       }
//     },
//     {
//       path: '/commonLogin',
//       name: 'commonLogin',
//       component: CommonLogin
//     },
//     {
//       path: '/adminLogin',
//       name: 'adminLogin',
//       component: AdminLogin
//     },
//     {
//       path: '/register',
//       name: 'register',
//       component: Register,
//       meta: {
//         requireAuth: true,
//         roles: ['admin']
//       }
//     },
//     {
//       path: '/index',
//       name: 'AppIndex',
//       component: AppIndex,
//       meta: {
//         requireAuth: true,
//         roles: ['admin','teacher','student']
//       }
//     },
//     {
//       path: '/teacher',
//       name: 'teacher',
//       component: Teacher
//     },
//     // 在这之上添加新路由，保证notfound跳转404在最后
//     {
//       path: '*',
//       redirect: {path: '/404'}
//     }
//   ]
// })
