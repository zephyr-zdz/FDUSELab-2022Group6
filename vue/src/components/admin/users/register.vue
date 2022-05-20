<template>
  <body id="post">
    <el-form class="reg-container" label-position="left" :model="regForm" :rules="rules" ref="regForm" label-width="100px">
      <h3 class="reg_title">用户注册</h3>

      <el-form-item label="用户类型" prop="type">
        <el-radio-group v-model="regForm.type">
          <el-radio label="teacher">教师</el-radio>
          <el-radio label="student">学生</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="学院" prop="school">
        <el-select placeholder="请选择学院" v-model="regForm.school" @change="getMajorOptions(regForm.school)">
          <el-option
            v-for="item in schoolOptions"
            :key="item.name"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="专业" prop="major">
        <el-select placeholder="请选择专业" v-model="regForm.major">
          <el-option
            v-for="item in this.majorOptions"
            :key="item.name"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="学号" prop="number" v-show="regForm.type === 'student'">
        <el-input v-model="regForm.number" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="6位，22开头" maxlength="6"></el-input>
      </el-form-item>

      <el-form-item label="工号" prop="number" v-show="regForm.type === 'teacher'">
        <el-input v-model="regForm.number" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="8位，22开头" maxlength="8"></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="regForm.name"
                  placeholder="英文或汉字"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="identity">
        <el-input v-model="regForm.identity" oninput="value=value.replace(/[^\dX]/g,'')"
                  placeholder="满足身份证号的规则（18位）" maxlength="18"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="regForm.mobile" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="11位纯数字，1开头" maxlength="11"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="regForm.email"
                  placeholder="用户标识符+ @ + 域名"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="reg(regForm)">注册</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      regForm: {
        type: '',
        name: '',
        number: '',
        identity: '',
        mobile: '',
        email: '',
        school: '',
        major: ''
      },
      schoolOptions: [],
      majorOptions: [],
      rules: {
        type: [
          { required: true, message: '请选择用户类型', trigger: 'change' }
        ],
        school: [
          { required: true, message: '请选择学院', trigger: 'change' }
        ],
        major: [
          { required: true, message: '请选择专业', trigger: 'change' }
        ],
        number: [
          { required: true, message: '请输入号码', trigger: 'blur' },
          { pattern: /[2][2][0-9]{4,6}$/, message: '22开头，长度在 6-8 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { pattern: /^[\u4E00-\u9FA5A-Za-z]+$/, message: '英文或汉字', trigger: 'blur' }
        ],
        identity: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { pattern: /[0-9]{18}|[0-9]{17}[xX]$/, message: '长度为 18 个数字', trigger: 'blur' }
        ],
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
  mounted () {
    console.log('mounted!\n')
    this.getSchool()
  },
  methods: {
    validEmail: function (email) {
      var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    getSchool () {
      this.$axios.get('/api/admin/institute/all')
        .then(response => {
          console.log(response.data)
          this.schoolOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getMajorOptions (school) {
      this.getSchool()
      if (!school) {
        this.majorOptions = []
      }
      console.log(school)
      console.log('111')
      this.regForm.major = ''
      this.$axios.get('/api/admin/major/all-by-institute', {params: {institute: school}})
        .then(response => {
          console.log(response.data)
          console.log(response.data.data)
          console.log('return')
          this.majorOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
          this.majorOptions = []
        })
    },
    reg (formname) {
      this.$refs.regForm.validate((valid) => {
        if (valid) {
          const postPath = '/api/admin/' + this.regForm.type
          var user = this.regForm
          var data = {
            name: user.name,
            identity: user.identity,
            tel: user.mobile,
            email: user.email,
            institute: user.school,
            major: user.major
          }
          if (this.regForm.type === 'student') {
            data.stunum = user.number
          }
          if (this.regForm.type === 'teacher') {
            data.jobnum = user.number
          }
          this.$axios
            .post(postPath, data)
            .then(res => {
              console.log(res)
              if (res.data.code === 0) {
                // this.$router.push('http://localhost:80/index')
                // var path = this.$route.query.redirect
                // this.$router.replace({path: path === '/' || path === undefined ? '/reg' : path})
                this.$alert('注册成功', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$refs.regForm.resetFields()
                  }
                })
              } else if (res.data.code === 1) {
                // alert('User or Info Exists！')
                this.$alert(res.data.msg, '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$refs.regForm.resetFields()
                  }
                })
              } else {
                // alert('Null')
                this.$alert('注册失败', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$refs.regForm.resetFields()
                  }
                })
              }
            })
            .catch(failResponse => {
            })
        }
      })
    }
  }
}
</script>

<style>
  /* .reg-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 5px auto;
    width: 350px auto;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .reg_title {
    margin: 10px auto;
    text-align: center;
    color: #505458;
  } */
  #post {
    height: auto;
    width: auto;
    position: relative;
    margin: 0;
    /* background-color: #F7F8FC; */
    overflow: hidden;
    /* background: url("~@/assets/background.png"); */
    /*background-position: top;*/
    /* background-size: auto; */
  }
  body{
    margin: 0px;
  }
  #reg-tip {
    font: 8;
    color:#ffffff46;
  }
</style>
