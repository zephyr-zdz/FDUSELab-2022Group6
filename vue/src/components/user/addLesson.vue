<template>
  <body id="post">
    <el-form class="reg-container" label-position="left" :model="addLesson" :rules="rules" ref="addLesson" label-width="100px">
      <h3 class="reg_title">新增课程</h3>

      <el-form-item label="课程名称" prop="name">
        <el-input v-model="addLesson.name" placeholder="请输入课程名称"></el-input>
      </el-form-item>

      <el-form-item label="课程编号" prop="number">
        <el-input v-model="addLesson.number" placeholder="请输入课程编号"></el-input>
      </el-form-item>

      <el-form-item label="开课院系" prop="institute">
        <el-select placeholder="请选择学院" v-model="addLesson.institute">
          <el-option
            v-for="item in schoolOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="学时" prop="coursehour">
        <el-input-number v-model="addLesson.coursehour" :min="1"></el-input-number>
      </el-form-item>

      <el-form-item label="学分" prop="credit">
        <el-input-number v-model="addLesson.credit" :min="0.5" :step="0.5"></el-input-number>
      </el-form-item>

      <el-form-item label="教师工号" prop="jobnum">
        <el-input disabled v-model="addLesson.jobnum" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="8位，22开头" maxlength="8"></el-input>
      </el-form-item>

      <el-form-item label="课程介绍" prop="intro">
        <el-input
          placeholder='请填写课程简介'
          v-model="addLesson.intro">
        </el-input>
      </el-form-item>

      <el-form-item label="上课时间" prop="time">
        <el-input v-model="addLesson.time" placeholder="请输入上课时间"></el-input>
      </el-form-item>

      <el-form-item label="上课地点" prop="place">
        <el-select placeholder="请选择上课地点" v-model="addLesson.place">
          <el-option
            v-for="item in classroomOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="课程人数" prop="capacity">
        <el-input-number v-model="addLesson.capacity" :min="0"></el-input-number>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">增加</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'addLesson',
  data () {
    return {
      res: {id: -1},
      addLesson: {
        jobnum: this.$store.getters.username,
        classroomid: '',
        name: '',
        number: '',
        institute: '',
        coursehour: '',
        credit: '',
        intro: '',
        schedule: '',
        capacity: ''
      },
      schoolOptions: [],
      classroomOptions: [],
      rules: {
        name: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '请输入课程编号', trigger: 'blur' }
        ],
        institute: [
          { required: true, message: '请选择学院', trigger: 'blur' }
        ],
        coursehour: [
          { required: true, message: '请选择学时', trigger: 'blur' }
        ],
        credit: [
          { required: true, message: '请选择学分', trigger: 'blur' }
        ],
        jobnum: [
          { required: true, message: '请输入号码', trigger: 'blur' },
          { pattern: /[2][2][0-9]{4,6}$/, message: '22开头，长度在 6-8 个字符', trigger: 'blur' }
        ],
        intro: [
        ],
        time: [
          { required: true, message: '请输入上课时间', trigger: 'blur' }
        ],
        place: [
          { required: true, message: '请选择上课地点', trigger: 'blur' }
        ],
        capacity: [
          { required: true, message: '请输入最大可选人数', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    console.log('mounted')
    this.getSchool()
    this.getClassrooms()
    this.getTeacherId()
  },
  methods: {
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
    getTeacherId () {
      this.$axios.get('/api/admin/course/getTeacherIdByJobNum/', {params: {JobNum: this.addLesson.jobnum}})
        .then(response => {
          console.log(response.data.data)
          if (response.data.code === 0) {
            this.res.id = response.data.data
            console.log(this.res.id)
          } else {
            this.$message({
              message: response.data.msg,
              type: 'error'
            })
            this.res.id = -1
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    getClassrooms () {
      this.$axios.get('/api/admin/Classroom/getOpenClassroom')
        .then(response => {
          if (response.data.code === 0) {
            console.log(response.data)
            this.classroomOptions = response.data.data
          } else {
            this.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    submit () {
      this.$refs.addLesson.validate((valid) => {
        if ((valid) && (this.res.id !== -1)) {
          var application = {
            coursename: this.addLesson.name,
            coursenum: this.addLesson.number,
            coursehour: this.addLesson.coursehour,
            credit: this.addLesson.credit,
            teacherid: this.res.id,
            instituteid: this.addLesson.institute,
            intro: this.addLesson.intro,
            schedule: this.addLesson.time,
            classroomid: this.addLesson.place,
            capacity: this.addLesson.capacity,
            applytype: 'insert',
            result: 'pending',
            pre_courseId: -1,
            applytime: new Date().getTime()
          }
          console.log('post')
          this.$axios.post('/api/teacher/application/apply', application, {params: {pre_courseId: -1}}).then(res => {
            console.log(res.data)
            if (res.data.code === 0) {
              this.$message({
                message: '添加成功',
                type: 'success'
              })
            } else {
              this.$message({
                message: res.data.msg,
                type: 'error'
              })
            }
          })
        } else {
          this.$alert('请检查输入是否正确', '添加失败', {confirmButtonText: '确定'})
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
