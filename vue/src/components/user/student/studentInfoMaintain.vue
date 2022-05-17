<template>
  <div id="info">
    <h1>用户信息</h1>
    <div>
      <h2>学院与专业</h2>
      <span class="elements">{{student.institute}}</span>
      <span class="elements">{{student.major}}</span>
    </div>
    <el-divider></el-divider>
    <div>
      <h2>学号</h2>
      <span class="elements">{{student.stunum}}</span>
    </div>
    <el-divider></el-divider>
    <div>
      <h2>姓名</h2>
      <span class="elements">{{student.name}}</span>
    </div>
    <el-divider></el-divider>
    <div>
      <h2>身份证号</h2>
      <span class="elements">{{student.identity}}</span>
    </div>
    <el-divider></el-divider>

    <div>
      <el-form class="reg-container" :model="regForm" :rules="rules" ref="regForm">
        <h2>手机号</h2>
        <span class="elements" id="mobile" v-if="mobileEdit === false">{{student.tel}}</span>
        <el-form-item prop="mobile" v-if="mobileEdit === true">
          <el-input v-model="regForm.mobile"
                    oninput="value=value.replace(/[^\d]/g,'')"
                    placeholder="11位纯数字，1开头"
                    maxlength="11"
                    style="width: 20%"></el-input>
          <el-button @click="changeMobile" size="mini" style="margin-left: 100px" v-if="mobileEdit === true">确定</el-button>
        </el-form-item>
        <el-button @click="changeMobile" size="mini" style="margin-left: 100px" v-if="mobileEdit === false">修改</el-button>
    <el-divider></el-divider>

      <h2>邮箱</h2>
      <span class="elements" id="email" v-if="emailEdit === false">{{student.email}}</span>
        <el-form-item prop="email" v-if="emailEdit === true">
          <el-input v-model="regForm.email"
                    placeholder="用户标识符+ @ + 域名"
                    v-if="emailEdit === true"
                    style="width: 20%"></el-input>
          <el-button @click="changeEmail" size="mini" style="margin-left: 100px" v-if="emailEdit === true">确定</el-button>
        </el-form-item>
      <el-button @click="changeEmail" size="mini" style="margin-left: 100px" v-if="emailEdit === false">修改</el-button>

    </el-form>
    </div>
    <el-divider></el-divider>
    <br>
    <el-button @click="submit">提交</el-button>
    <el-button @click="changePassword">修改密码</el-button>
  </div>
</template>

<script>
export default {
  data () {
    var temp = this.$store.getters.username
    return {
      student: {
        id: '',
        stunum: temp,
        name: '',
        identity: '',
        institute: '',
        major: '',
        email: '',
        tel: ''
      },
      mobileEdit: false,
      emailEdit: false,
      regForm: {
        mobile: '',
        email: ''
      },
      rules: {
        mobile: [
          {
            pattern: /^(1[0-9][0-9])\d{8}$/,
            message: '手机号码格式不正确！',
            trigger: 'blur'
          }
        ],
        email: [
          { pattern: /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '格式为用户标识符+ @ + 域名', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    changeMobile () {
      this.$refs.regForm.validate((valid) => {
        if (valid) {
          this.mobileEdit = !this.mobileEdit
          this.student.tel = this.regForm.mobile
        }
      })
    },
    changeEmail () {
      this.$refs.regForm.validate((valid) => {
        if (valid) {
          this.emailEdit = !this.emailEdit
          this.student.email = this.regForm.email
        }
      })
    },
    submit () {
      this.$axios.put('/api/student/student', this.student)
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              type: 'success',
              message: '修改提交成功！'
            })
            this.getInfo()
          } else {
            this.$message({
              type: 'error',
              message: 'res.data.msg'
            })
            this.getInfo()
          }
        })
    },
    changePassword () {
      this.$router.push('/changePassword')
    },
    getInfo () {
      this.$axios.get('/api/student/student', {params: {stunum: this.student.stunum}})
        .then(response => {
          if (response.data.code === 0) {
            console.log(response.data.data)
            this.student = response.data.data
            this.regForm.email = this.student.email
            this.regForm.mobile = this.student.tel
          } else {
            this.$alert(response.data.msg, '系统提示', {confirmButtonText: '确定'})
          }
        })
    }
  }
}

</script>

<style scoped>
h1{
  font-size: 25px;
}
h2{
  font-size: 20px;
}
.elements{
  font-size: 15px;
}
</style>
