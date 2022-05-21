<template>
  <el-dialog
    title="编辑课程"
    top="5vh"
    width="80%"
    :append-to-body="true"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    @open="course2Form()">
    <el-form class="reg-container" label-position="left" :model="editLesson" :rules="rules" ref="editLessonForm" label-width="100px">
      <el-form-item label="开课学期" prop="semester">
        <el-input v-model="editLesson.semester" placeholder="请输入开课学期" disabled></el-input>
      </el-form-item>
      <el-form-item label="课程名称" prop="coursetemplateid">
        <el-select placeholder="请选择课程名称" v-model="editLesson.coursetemplateid">
          <el-option
            v-for="item in templateOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="学时" prop="coursehour">
        <el-input-number v-model="editLesson.coursehour" :min="1"></el-input-number>
      </el-form-item>

      <el-form-item label="学分" prop="credit">
        <el-input-number v-model="editLesson.credit" :min="0.5" :step="0.5"></el-input-number>
      </el-form-item>

      <el-form-item label="教师工号" prop="jobnum">
        <el-input v-model="editLesson.jobnum" oninput="value=value.replace(/[^\d]/g,'')"
                  placeholder="8位，22开头" maxlength="8" @change="getTeacherId"></el-input>
      </el-form-item>

      <el-form-item label="课程介绍" prop="intro">
        <el-input
          placeholder='请填写课程简介'
          v-model="editLesson.intro">
        </el-input>
      </el-form-item>

      <el-form-item label="上课时间" prop="schedule">
        <el-input v-model="editLesson.schedule" placeholder="请输入上课时间"></el-input>
      </el-form-item>

      <el-form-item label="上课地点" prop="classroomid">
        <el-select placeholder="请选择上课地点" v-model="editLesson.classroomid">
          <el-option
            v-for="item in classroomOptions"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="课程人数" prop="capacity">
        <el-input-number v-model="editLesson.capacity" :min="0"></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align:center">
      <el-button type="success" @click="submit">提交</el-button>
      <el-button type="warning" @click="course2Form">恢复</el-button>
      <el-popconfirm title="确定永久删除该课程吗？" @confirm="deleteLesson" style="margin-left:10px">
        <el-button type="danger" slot="reference">删除课程</el-button>
      </el-popconfirm>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'editLesson',
  data () {
    var semester = this.$store.getters.semester
    return {
      course: [],
      dialogVisible: false,
      res: {id: -1},
      majoridlist: '',
      editLesson: {
        semester: semester,
        jobnum: '',
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
        // major: [
        //   { required: true, message: '请选择学院', trigger: 'blur' }
        // ],
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
    console.log('mounted')
    this.getTemplates()
    this.getClassrooms()
    this.getMajors()
  },
  methods: {
    course2Form () {
      console.log('transform')
      this.editLesson = {
        semester: this.course.course.semester,
        jobnum: this.course.teacher.jobnum,
        classroomid: this.course.classroom.id,
        name: this.course.courseTemplate.name,
        number: this.course.courseTemplate.coursenum,
        type: this.isPublic(this.course.majorListOfCourse.length),
        majorSingle: this.course.majorListOfCourse[0] === undefined ? '' : this.course.majorListOfCourse[0].id,
        majorMulti: this.course.majorListOfCourse.map(item => item.id),
        coursehour: this.course.course.coursehour,
        credit: this.course.course.credit,
        intro: this.course.course.intro,
        schedule: this.calendar(this.course.calendarList),
        capacity: this.course.course.capacity,
        coursetemplateid: this.course.courseTemplate.id
      }
    },
    handleClose () {
      this.$nextTick(() => {
        this.$emit('afterEditLesson')
        this.dialogVisible = false
      })
    },
    isPublic (length) {
      switch (length) {
        case 0:
          return 1
        case 1:
          return 3
        default:
          return 2
      }
    },
    calendar (calendarList) {
      var schedule = ''
      for (var i = 0; i < calendarList.length; i++) {
        schedule += (calendarList[i].day + ',' + calendarList[i].number)
        schedule += ' '
      }
      this.rowSchedule = schedule
      return schedule
    },
    getTeacherId () {
      this.$axios.get('/api/admin/teacher-course/id-by-jobnum/', {params: {JobNum: this.editLesson.jobnum}})
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
    deleteLesson () {
      var application = {
        semester: this.editLesson.semester,
        coursehour: this.editLesson.coursehour,
        credit: this.editLesson.credit,
        teacherid: this.res.id,
        instituteid: this.editLesson.institute,
        intro: this.editLesson.intro,
        schedule: this.editLesson.schedule,
        classroomid: this.editLesson.classroomid,
        capacity: this.editLesson.capacity,
        coursetemplateid: this.editLesson.coursetemplateid,
        applytype: 'delete',
        result: 'success',
        precourseid: this.course.course.id,
        majoridlist: this.majoridlist,
        ispublic: this.editLesson.type === 1 ? 'Y' : 'N',
        applytime: new Date().getTime()
      }
      this.$axios.post('/api/admin/teacher-course/modify', application).then(res => {
        console.log(res.data)
        if (res.data.code === 0) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.dialogVisible = false
          this.$emit('afterEditLesson')
        } else {
          this.$message({
            message: '删除失败',
            type: 'error'
          })
        }
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
      console.log('submit' + this.res.id)
      this.$refs.editLessonForm.validate((valid) => {
        if ((valid) && (this.res.id !== -1)) {
          switch (this.editLesson.type) {
            case 1:
              this.majoridlist = ''
              break
            case 2:
              this.majoridlist = this.editLesson.majorMulti.join(',')
              break
            case 3:
              console.log(this.editLesson.majorSingle)
              this.majoridlist = this.editLesson.majorSingle
              break
            default:
              break
          }
          var application = {
            semester: this.editLesson.semester,
            coursehour: this.editLesson.coursehour,
            credit: this.editLesson.credit,
            teacherid: this.res.id,
            instituteid: this.editLesson.institute,
            intro: this.editLesson.intro,
            schedule: this.editLesson.schedule,
            classroomid: this.editLesson.classroomid,
            capacity: this.editLesson.capacity,
            coursetemplateid: this.editLesson.coursetemplateid,
            applytype: 'update',
            result: 'success',
            precourseid: this.course.course.id,
            majoridlist: this.majoridlist,
            ispublic: this.editLesson.type === 1 ? 'Y' : 'N',
            applytime: new Date().getTime()
          }
          console.log('post')
          this.$axios.post('/api/admin/teacher-course/modify', application).then(res => {
            console.log(res.data)
            if (res.data.code === 0) {
              this.$message({
                message: '修改成功',
                type: 'success'
              })
            } else {
              this.$message({
                message: '修改失败，请检查输入课程是否有冲突',
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
