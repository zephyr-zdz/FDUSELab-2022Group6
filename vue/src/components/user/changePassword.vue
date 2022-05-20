<template>
  <body id="poster">
    <el-form class="pswchg-container" :model="Form" ref="Form">
      <h2 class="pswchg_title">更改密码</h2>

      <el-form-item>
        <el-input type="username" v-model="Form.username"
                  auto-complete="off" placeholder="用户名" maxlength="8" disabled></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input type="password" v-model="Form.password"
                  auto-complete="off" placeholder="老密码"></el-input>
      </el-form-item>

      <el-form-item prop="new_psw">
        <el-input type="newpassword" v-model="Form.new_psw"
                  auto-complete="off" placeholder="新密码"></el-input>
      </el-form-item>

      <el-form-item prop="check">
        <el-input type="check" v-model="Form.check"
                  auto-complete="off" placeholder="确认密码"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="isValidPassword">确定修改</el-button>
      </el-form-item>
    </el-form>
    </body>
</template>

<script>
/* eslint-disable */
export default {
  name: 'changePassword',
  data () {
    // 获取getters中的role
    const temp = this.$store.getters.username
    console.log(temp)
    return {
      Form: {
        username: temp,
        password: '',
        check: '',
        new_psw: ''
      },
      responseResult: []
    }
  },
  methods: {
    isValidPassword(){
      var password = this.Form.new_psw
      var confirmPassword = this.Form.check
      var num = false,eng = false,special = false
      if(password.length<6)
      {
        this.$alert("密码必须在6~32位")
        return
      }
      for (var i = 0;i < password.length; i++)
      {
        if (password[i] >= '0' && password[i]<='9') num = true
        else if(password[i]>='a'&&password[i]<='z'||password[i]>='A'&&password[i]<='Z') eng=true
        else if(password[i]==='-'||password[i]==='_') special=true
        else
        {
            this.$alert("密码只能包含数字、字母或-_")
            return
        }
      }
      if(num+eng+special<2)
      {
        this.$alert("密码至少需要包含字母，数字或者-_中的两种")
        return
      }
      if(password!==confirmPassword)
      {
        this.$alert("两次输入的密码不匹配！")
        return
      }
      else
      {
        console.log('change')
        this.changePassword()
      }
    },
    changePassword () {
      var role = this.$store.getters.role
      var path = '/api/'+role+'/'+role+'/'+'change-password'
      var param = new FormData()
      console.log(path)
      param.append('username', this.Form.username)
      param.append('oldpwd', this.Form.password)
      param.append('newpwd', this.Form.new_psw)
      if (this.Form.new_psw === this.Form.check) {
        this.$axios
          .put(path, param)
          .then(successResponse => {
            console.log(successResponse.data)
            if (successResponse.data['data'] === 'true') {
              console.log(successResponse.data['data'])
              this.$alert('密码修改成功！','系统提示：', {
                type: 'success',
                confirmButtonText: '确定'
              })
              var toPath = this.$route.query.redirect
              this.$router.replace({path: '/login/commonLogin', query: {redirect: toPath}})
              console.log('replace')
            } else {
              this.$alert('密码与用户名不一致！','系统提示：', {
                type: 'error',
                confirmButtonText: '确定'
              })
              this.$refs.Form.resetFields()
            }
          })
          .catch(failResponse => {
          })
      } else {
        this.$alert('两次输入密码不一致！','系统提示：', {
          type: 'error',
          confirmButtonText: '确定'
        })
        this.$refs.Form.resetFields()
      }
    }
  }
}
</script>

<style>
  .pswchg-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .pswchg_title {
    margin: auto;
    text-align: center;
    color: #505458;
  }
  #poster {
    height: 100%;
    width: 100%;
    position: fixed;
    margin: 0;
    background-color: #ffffff00;
    overflow: hidden;
  }
  body{
    margin: 0px;
  }
</style>
