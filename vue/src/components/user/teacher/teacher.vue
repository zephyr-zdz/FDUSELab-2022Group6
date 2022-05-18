<template>
  <div>
    <nav-menu></nav-menu>
    <el-container style="border: 1px solid #fff">
      <el-aside width="200px">
        <el-menu style="border: white">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-edit"></i>基本信息</template>
              <el-menu-item @click="handleChange('teacherInfoMaintain')">个人信息维护</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>课务</template>
              <el-menu-item @click="handleChange('lessonTable')">课程信息维护</el-menu-item>
              <el-menu-item @click="handleChange('myLessonApplication')">我的课程申请</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="background: white" height="1">
          <div class="welcome" style="margin-top: 10px">
          <span>
            欢迎你！工号 {{username}} 的老师
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
          <teacher-info-maintain
            label='teacherInfoMaintain'
            v-show="showRecord === 'teacherInfoMaintain'"
            id="teacherInfoMaintain"
            ref="teacherInfoMaintain"></teacher-info-maintain>
          <my-lesson-application
            label="myLessonApplication"
            v-show="showRecord === 'myLessonApplication'"
            id="myLessonApplication"
            ref="myLessonApplication"></my-lesson-application>
          <lesson-table
            label="lessonTable"
            v-show="showRecord === 'lessonTable'"
            id="lessonTable"
            ref="lessonTable"></lesson-table>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import NavMenu from '../../common/NavMenu'
import TeacherInfoMaintain from './teacherInfoMaintain'
import MyLessonApplication from './myLessonApplication'
import LessonTable from './lessonTable'

export default {
  data () {
    var temp = this.$store.getters.username
    return {
      username: temp,
      name: 'wzy',
      identity: '330902200011209219',
      mobile: '11111111111',
      email: '123@qq.com',
      showRecord: ''
    }
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
      this.$refs.teacherInfoMaintain.getInfo()
    },
    showApplication () {
      this.$refs.myLessonApplication.getApplication()
    },
    showLesson () {
      this.$refs.lessonTable.getTeacher()
      this.$refs.lessonTable.getLessons()
    },
    handleChange (value) {
      switch (value) {
        case 'teacherInfoMaintain' :
          this.showInfo()
          this.showRecord = value
          break
        case 'myLessonApplication' :
          this.showApplication()
          this.showRecord = value
          break
        case 'lessonTable' :
          this.showLesson()
          this.showRecord = value
          break
        default :
          this.showRecord = ''
          break
      }
    }
  },
  components: {
    'nav-menu': NavMenu,
    'teacher-info-maintain': TeacherInfoMaintain,
    'my-lesson-application': MyLessonApplication,
    'lesson-table': LessonTable
  },
  computed: {
    getUsername () {
      return this.$store.getters.username
    }
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
