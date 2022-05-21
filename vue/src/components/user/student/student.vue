<template>
  <div>
    <nav-menu></nav-menu>
    <el-container style="border: 1px solid #fff">
      <el-aside width="200px">
        <el-menu style="border: white">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-edit"></i>基本信息</template>
              <el-menu-item @click="handleChange('studentInfoMaintain')">个人信息维护</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>选课事务</template>
              <el-menu-item @click="handleChange('studentCheckLesson')">选课</el-menu-item>
              <el-menu-item @click="handleChange('studentLessonChosen')">已选课程</el-menu-item>
              <el-menu-item @click="handleChange('studentLessonLearned')">已修课程</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="background: white" height="1">
          <div class="welcome" style="margin-top: 10px">
          <span>
            欢迎你！学号 {{username}} 的同学
          </span>
            <el-dropdown @command="handleCommand">
              <i class="el-icon-setting" style="margin-right: 10px"></i>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <el-main>
          <student-info-maintain
            label='studentInfoMaintain'
            v-show="showRecord === 'studentInfoMaintain'"
            id="studentInfoMaintain"
            ref="studentInfoMaintain"></student-info-maintain>
          <student-check-lesson
            label="studentCheckLesson"
            v-show="showRecord === 'studentCheckLesson'"
            id="studentCheckLesson"
            ref="studentCheckLesson"></student-check-lesson>
          <student-lesson-chosen
            label="studentLessonChosen"
            v-show="showRecord === 'studentLessonChosen'"
            id="studentLessonChosen"
            ref="studentLessonChosen">
          </student-lesson-chosen>
          <student-lesson-learned
            label="studentLessonLearned"
            v-show="showRecord === 'studentLessonLearned'"
            id="studentLessonLearned"
            ref="studentLessonLearned">
          </student-lesson-learned>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import NavMenu from '../../common/NavMenu'
import StudentInfoMaintain from './studentInfoMaintain'
import StudentCheckLesson from './studentCheckLesson'
import StudentLessonChosen from './studentLessonChosen'
import StudentLessonLearned from './studentLessonLearned'

export default {
  data () {
    var temp = this.$store.getters.username
    return {
      username: temp,
      name: '',
      identity: '330902200011209219',
      mobile: '11111111111',
      email: '123@qq.com',
      showRecord: '',
      isValid: false
    }
  },
  mounted () {
    this.isChoose()
  },
  methods: {
    handleCommand (command) {
      if (command === 'logout') {
        this.$alert('是否退出登录？', '系统提示：', {
          confirmButtonText: '确定',
          type: 'warning',
          callback: res => {
            if (res === 'confirm') {
              this.$store.commit('logout')
              location.reload()// 刷新使得导航栏发生变化
            }
          }
        })
      }
    },
    showInfo () {
      this.$refs.studentInfoMaintain.getInfo()
    },
    showLesson () {
      this.$refs.studentCheckLesson.getStudent()
      this.$refs.studentCheckLesson.getLessons()
      this.$refs.studentCheckLesson.getClassrooms()
      this.$refs.studentCheckLesson.getSemesters()
    },
    showStudentLessonChosen () {
      this.$refs.studentLessonChosen.getStudent()
      this.$refs.studentLessonChosen.getLessons()
    },
    showStudentLessonLearned () {
      this.$refs.studentLessonLearned.getStudent()
      this.$refs.studentLessonLearned.getLessons()
    },
    isChoose () {
      var isChoose = false
      this.$axios.get('/api/student/course/valid').then(res => {
        if (res.data.code === 0) {
          this.$store.commit('setRound', res.data.data)
          if ((res.data.data === 'first') || (res.data.data === 'second')) {
            this.isValid = true
            return true
          } else {
            this.$message.warning('选课未开放！')
            return false
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: 'warning'
          })
        }
      })
      console.log(isChoose)
    },
    handleChange (value) {
      switch (value) {
        case 'studentInfoMaintain' :
          this.showInfo()
          this.showRecord = value
          break
        case 'studentCheckLesson' :
          if (this.isValid) {
            this.$message.success('选课已开放！')
            this.showRecord = value
            this.showLesson()
          }
          break
        case 'studentLessonChosen' :
          this.showStudentLessonChosen()
          this.showRecord = value
          break
        case 'studentLessonLearned' :
          this.showStudentLessonLearned()
          this.showRecord = value
          break
        default :
          this.showRecord = ''
          break
      }
    }
  },
  components: {
    'student-info-maintain': StudentInfoMaintain,
    'nav-menu': NavMenu,
    'student-check-lesson': StudentCheckLesson,
    'student-lesson-chosen': StudentLessonChosen,
    'student-lesson-learned': StudentLessonLearned
  }
}

</script>

<style scoped>
.welcome{
  text-align: right;
}
h1{
  font-size: 25px;
}
h2{
  font-size: 20px;
}
.elements{
  font-size: 15px;
  color: gray;
}
</style>
