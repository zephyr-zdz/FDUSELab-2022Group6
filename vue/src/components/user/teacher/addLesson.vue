<template>
  <el-dialog
    title="新增课程"
    top="5vh"
    width="50%"
    :append-to-body="true"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    @open="show()">

    <el-form class="reg-container" label-position="left" :model="addLesson" :rules="rules" ref="addLessonForm" label-width="100px">
      <el-form-item label="开课学期" prop="semester">
        <el-input v-model="addLesson.semester" placeholder="请输入开课学期" disabled></el-input>
      </el-form-item>

      <el-form-item label="课程名称" prop="coursetemplateid">
        <el-select placeholder="请选择课程名称" v-model="addLesson.coursetemplateid">
          <el-option
            v-for="item in templateOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="课程类型" prop="type">
        <el-select placeholder="请选择类型" v-model="addLesson.type">
          <el-option
            v-for="item in typeOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="可选专业" prop="major" v-if="addLesson.type === 2 ">
        <el-select placeholder="请选择专业" multiple v-model="addLesson.majorMulti">
          <el-option
            v-for="item in majorOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="可选专业" prop="major" v-if="addLesson.type === 3 ">
        <el-select placeholder="请选择专业" v-model="addLesson.majorSingle">
          <el-option
            v-for="item in majorOptions"
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
        <el-input v-model="addLesson.jobnum" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="8位，22开头" maxlength="8" disabled></el-input>
      </el-form-item>

      <el-form-item label="课程介绍" prop="intro">
        <el-input
          placeholder='请填写课程简介'
          v-model="addLesson.intro">
        </el-input>
      </el-form-item>

      <el-form-item label="上课时间" prop="schedule">
        <el-input v-model="addLesson.schedule" placeholder="请输入上课时间"></el-input>
      </el-form-item>

      <el-form-item label="上课地点" prop="classroomid">
        <el-select placeholder="请选择上课地点" v-model="addLesson.classroomid">
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
        <el-button type="success" style="width: 30%;border: none" @click="submit">增加</el-button>
        <el-button type="danger" style="width: 30%;border: none" @click="cleanForm">清空表单</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  name: 'addLesson',
  data () {
    var semester = this.$store.getters.semester
    return {
      dialogVisible: false,
      res: {id: -1},
      majoridlist: '',
      addLesson: {
        semester: semester,
        jobnum: this.$store.getters.username,
        classroomid: '',
        name: '',
        number: '',
        type: '',
        majorSingle: '',
        majorMulti: [],
        coursehour: '',
        credit: '',
        intro: '',
        schedule: '',
        capacity: '',
        coursetemplateid: ''
      },
      templateOptions: [],
      classroomOptions: [],
      typeOptions: [{
        id: 1,
        name: '通识课程'
      }, {
        id: 2,
        name: '面向部分专业课程'
      }, {
        id: 3,
        name: '专业课程'
      }],
      rules: {
        semester: [
          {required: true, message: '请输入开课学期', trigger: 'blur'},
          {pattern: /[0-9]{4}[-][0-9]{4}[\u4e00-\u9fa5]$/, message: '请输入正确的开课学期，如：2021-2022春', trigger: 'blur'}
        ],
        coursetemplateid: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '请输入课程编号', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入课程类型', trigger: 'blur' }
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
        schedule: [
          { required: true, message: '请输入上课时间', trigger: 'blur' }
        ],
        classroomid: [
          { required: true, message: '请选择上课地点', trigger: 'blur' }
        ],
        capacity: [
          { required: true, message: '请输入最大可选人数', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getTemplates()
    this.getClassrooms()
    this.getMajors()
  },
  methods: {
    handleClose () {
      this.$nextTick(() => {
        this.$emit('afterAddLesson')
        this.dialogVisible = false
      })
    },
    cleanForm () {
      this.$refs.addLessonForm.resetFields()
    },
    getTeacherId () {
      this.$axios.get('/api/admin/teacher-course/id-by-jobnum/', {params: {JobNum: this.addLesson.jobnum}})
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
    getMajors () {
      this.$axios.get('/api/admin/major/all')
        .then(response => {
          console.log(response.data)
          this.majorOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getClassrooms () {
      this.$axios.get('/api/admin/classroom/open')
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
    getTemplates () {
      this.$axios.get('/api/admin/course-template/all')
        .then(response => {
          if (response.data.code === 0) {
            console.log(response.data)
            this.templateOptions = response.data.data
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
      this.getTeacherId()
      this.$refs.addLessonForm.validate((valid) => {
        if ((valid) && (this.res.id !== -1)) {
          switch (this.addLesson.type) {
            case 1:
              this.majoridlist = this.addLesson.majorSingle
              break
            case 2:
              this.majoridlist = ''
              for (let i = 0; i < this.addLesson.majorMulti.length; i++) {
                this.majoridlist += this.addLesson.majorMulti[i] + ','
              }
              break
            case 3:
              console.log(this.addLesson.majorSingle)
              this.majoridlist = this.addLesson.majorSingle
              break
            default:
              break
          }
          var application = {
            semester: this.addLesson.semester,
            coursehour: this.addLesson.coursehour,
            credit: this.addLesson.credit,
            teacherid: this.res.id,
            instituteid: this.addLesson.institute,
            intro: this.addLesson.intro,
            schedule: this.addLesson.schedule,
            classroomid: this.addLesson.classroomid,
            capacity: this.addLesson.capacity,
            coursetemplateid: this.addLesson.coursetemplateid,
            applytype: 'insert',
            result: 'pending',
            precourseid: -1,
            majoridlist: this.majoridlist,
            ispublic: this.addLesson.type === 1 ? 'Y' : 'N',
            applytime: new Date().getTime()
          }
          console.log('post')
          this.$axios.post('/api/teacher/application/apply', application).then(res => {
            console.log(res.data)
            if (res.data.code === 0) {
              this.$message({
                message: '申请成功',
                type: 'success'
              })
              this.cleanForm()
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
