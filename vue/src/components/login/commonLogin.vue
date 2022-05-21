<template>
  <body id="poster">
    <el-form class="login-container" :model="loginForm" :rules="rules" ref="loginForm">
      <h2 class="login_title">系统登录</h2>
      <el-form-item label="用户类型" prop="type">
        <el-radio-group v-model="loginForm.type">
          <el-radio label="teacher">教师</el-radio>
          <el-radio label="student">学生</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="工号" prop="number" v-if="loginForm.type === 'teacher'">
        <el-input type="text" v-model="loginForm.number" oninput="value=value.replace(/[^\d]/g,'')"
                  auto-complete="off" placeholder="8位，22开头" maxlength="8"></el-input>
      </el-form-item>

      <el-form-item label="学号" prop="number" v-if="loginForm.type === 'student'">
        <el-input type="text" v-model="loginForm.number" oninput="value=value.replace(/[^\d]/g,'')"
                  auto-complete="off" placeholder="6位，22开头" maxlength="6"></el-input>
      </el-form-item>

      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="密码" @keyup.enter.native="login"></el-input>
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
        type: 'teacher',
        number: '',
        password: ''
      },
      rules: {
        type: [
          { required: true, message: '请选择用户类型', trigger: 'change' }
        ],
        number: [
          { required: true, message: '请输入工号', trigger: 'blur' },
          { pattern: /[2][2][0-9]{4,6}$/, message: '长度在 6-8 个字符', trigger: 'blur' }
        ]
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      var param = new FormData()
      // console.log(this.loginForm.chara)
      param.append('type', this.loginForm.type)
      param.append('username', this.loginForm.number)
      param.append('password', this.loginForm.password)
      this.$axios
        .post('/api/user/login', param)
        .then(successResponse => {
          if (successResponse.data.data === 'true') {
            this.$store.commit('login', this.loginForm)
            this.$router.replace('/user')
          } else if (successResponse.data.data === 'first') {
            this.$store.commit('login', this.loginForm)
            this.$alert('第一次登陆，请改密码！', '系统提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push('/changePassword')
              }
            })
            // var toPath = this.$route.query.redirect
            // this.$router.push({path: '/user/changePassword'})
          } else {
            this.$alert(successResponse.data.msg, '系统提示', {
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
