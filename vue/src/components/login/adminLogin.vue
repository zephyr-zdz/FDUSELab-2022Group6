<template>
  <body id="poster">
    <el-form class="login-container">
      <h2 class="login_title">后台登录</h2>

      <el-form-item>
        <el-input type="text" v-model="loginForm.username"
                  auto-complete="off" placeholder="管理员账号"></el-input>
      </el-form-item>

      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="管理员密码" @keyup.enter.native="login"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      loginForm: {
        role: 'admin',
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      var param = new FormData()
      console.log(this.$store.state)
      param.append('type', this.loginForm.role)
      param.append('username', this.loginForm.username)
      param.append('password', this.loginForm.password)
      console.log(param.get('username'))
      this.$axios
        .post('/api/user/login', param)
        .then(successResponse => {
          console.log(successResponse.data)
          if (successResponse.data['data'] === 'true') {
            console.log(successResponse.data)
            this.$store.commit('login', this.loginForm)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/admin' : path})
            console.log('replace')
          } else {
            this.$alert('用户名或密码错误', '系统提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.loginForm.password = ''
              }
            })
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>

<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: auto;
    text-align: center;
    color: #505458;
  }
</style>
