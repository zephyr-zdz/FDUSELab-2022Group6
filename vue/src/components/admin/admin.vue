<template>
  <div>
    <nav-menu></nav-menu>
    <body>
      <el-container style="border: 0px solid #fff">
        <el-aside width="200px">
          <el-menu style="border: white">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-user"></i>人员信息维护</template>
              <el-menu-item @click="handleChange('studentTable')">学生信息维护</el-menu-item>
              <el-menu-item @click="handleChange('teacherTable')">教师信息维护</el-menu-item>
              <el-menu-item @click="handleChange('csvInput')">信息批量导入</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-setting"></i>院系信息维护</template>
              <el-menu-item @click="handleChange('schoolAndMajorTable')">学院/专业信息维护</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title"><i class="el-icon-menu"></i>教务维护</template>
              <el-menu-item @click="handleChange('lessonTable')">课程信息维护</el-menu-item>
              <el-menu-item @click="handleChange('templateTable')">课程模板维护</el-menu-item>
              <el-menu-item @click="handleChange('csvInputLesson')">批量导入课程</el-menu-item>
              <el-menu-item @click="handleChange('lessonApplicationCheck')">课程申请审核</el-menu-item>
              <el-menu-item @click="handleChange('lessonTimeArrange')">上课时间安排</el-menu-item>
              <el-menu-item @click="handleChange('classroomArrange')">上课教室安排</el-menu-item>
            </el-submenu>
            <el-submenu index="4">
              <template slot="title"><i class="el-icon-s-promotion"></i>选课事务</template>
              <el-menu-item @click="handleChange('openOrCloseClassSelect')">开放/关闭选课</el-menu-item>
              <el-menu-item @click="handleChange('checkStudentApplication')">学生选课申请</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-container>
          <el-header style="background: white" height="0px">
            <div class="welcome" style="margin-top: 10px">
            <span>
              欢迎你！管理员
            </span>
              <el-dropdown @command="handleCommand">
                <i class="el-icon-setting" style="margin-right: 10px"></i>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </el-header>

          <el-main
            style="margin-top: 20px"
            >
            <student-table
              id="studentTable"
              label="studentTable"
              v-show="showRecord === 'studentTable'"
              ref="studentTable"></student-table>
            <teacher-table
              id="teacherTable"
              label="teacherTable"
              v-show="showRecord === 'teacherTable'"
              ref="teacherTable"></teacher-table>
            <csv-input id="csvInput" label="csvInput" v-show="showRecord === 'csvInput'" ref="csvInput"></csv-input>
            <lesson-table id="lessonTable"
                          label="lessonTable"
                          v-show="showRecord === 'lessonTable'"
                          ref="lessonTable"></lesson-table>
            <csv-input-lesson id="csvInputLesson"
                            label="csvInputLesson"
                            v-show="showRecord === 'csvInputLesson'"
                            ref="csvInputLesson"></csv-input-lesson>
            <lesson-time-arrange id="lessonTimeArrange"
                                 label="lessonTimeArrange"
                                 v-show="showRecord === 'lessonTimeArrange'"
                                 ref="lessonTimeArrange"></lesson-time-arrange>
            <classroom-arrange id="classroomArrange"
                               label="classroomArrange"
                               v-show="showRecord === 'classroomArrange'"
                               ref="classroomArrange"></classroom-arrange>
            <open-or-close-class-select id="openOrCloseClassSelect"
                                        label="openOrCloseClassSelect"
                                        v-show="showRecord === 'openOrCloseClassSelect'"
                                        ref="openOrCloseClassSelect"></open-or-close-class-select>
            <lesson-application-check id="lessonApplicationCheck"
                                      label="lessonApplicationCheck"
                                      v-show="showRecord === 'lessonApplicationCheck'"
                                      ref="lessonApplicationCheck"></lesson-application-check>
            <school-and-major-table id="schoolAndMajorTable"
                                    label="schoolAndMajorTable"
                                    v-show="showRecord === 'schoolAndMajorTable'"
                                    ref="schoolAndMajorTable"></school-and-major-table>
            <check-student-application id="checkStudentApplication"
                                       label="checkStudentApplication"
                                       v-show="showRecord === 'checkStudentApplication'"
                                       ref="checkStudentApplication"></check-student-application>
            <template-table id="templateTable"
                            label="templateTable"
                            v-show="showRecord === 'templateTable'"
                            ref="templateTable"></template-table>
          </el-main>
        </el-container>
      </el-container>
    </body>
  </div>
</template>

<script>
import NavMenu from '../common/NavMenu'

import StudentTable from './users/students/studentTable'
import TeacherTable from './users/teachers/teacherTable'
import CsvInput from './users/csvInput'
import LessonTable from './courses/lessonTable'
import CsvInputLesson from './courses/csvInputLesson'
import LessonTimeArrange from './courses/lessonTimeArrange'
import ClassroomArrange from './courses/classroomArrange'
import OpenOrCloseClassSelect from './openOrCloseClassSelect'
import LessonApplicationCheck from './courses/lessonApplicationCheck'
import SchoolAndMajorTable from './majorsAndSchools/schoolAndMajorTable'
import CheckStudentApplication from './courses/checkStudentApplication'
import TemplateTable from './courses/templateTable'
import AddTemplate from './courses/addTemplate'

export default {
  data () {
    return {
      showRecord: ''
    }
  },
  methods: {
    handleCommand (command) {
      if (command === 'logout') {
        this.$alert('是否退出管理员身份？', '系统提示：', {
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
    showStudents () {
      this.$refs.studentTable.getStudents()
    },
    showTeachers () {
      this.$refs.teacherTable.getTeachers()
    },
    showLessons () {
      this.$refs.lessonTable.getClassrooms()
      this.$refs.lessonTable.getLessons()
      this.$refs.lessonTable.getSemesters()
    },
    showTemplate () {
      this.$refs.templateTable.getTemplate()
    },
    showLessonTime () {
      this.$refs.lessonTimeArrange.getLessonTime()
    },
    showClassroom () {
      this.$refs.classroomArrange.getClassroom()
    },
    showApplication () {
      this.$refs.lessonApplicationCheck.getApplication()
    },
    showSchoolAndMajor () {
      this.$refs.schoolAndMajorTable.getSchoolAndMajor()
    },
    handleChange (value) {
      switch (value) {
        case 'studentTable' :
          this.showStudents()
          this.showRecord = value
          break
        case 'teacherTable' :
          this.showTeachers()
          this.showRecord = value
          break
        case 'csvInput' :
          this.showRecord = value
          break
        case 'lessonTable' :
          this.showLessons()
          this.showRecord = value
          break
        case 'csvInputLesson' :
          this.showRecord = value
          break
        case 'lessonTimeArrange' :
          this.showLessonTime()
          this.showRecord = value
          break
        case 'classroomArrange' :
          this.showClassroom()
          this.showRecord = value
          break
        case 'openOrCloseClassSelect' :
          this.showRecord = value
          break
        case 'lessonApplicationCheck' :
          this.showApplication()
          this.showRecord = value
          break
        case 'schoolAndMajorTable' :
          this.showSchoolAndMajor()
          this.showRecord = value
          break
        case 'checkStudentApplication' :
          this.showRecord = value
          break
        case 'templateTable' :
          this.showTemplate()
          this.showRecord = value
          break
        default :
          this.showRecord = ''
          break
      }
    }
  },
  components: {
    AddTemplate,
    'student-table': StudentTable,
    'teacher-table': TeacherTable,
    'csv-input': CsvInput,
    'nav-menu': NavMenu,
    'lesson-table': LessonTable,
    'csv-input-lesson': CsvInputLesson,
    'lesson-time-arrange': LessonTimeArrange,
    'classroom-arrange': ClassroomArrange,
    'open-or-close-class-select': OpenOrCloseClassSelect,
    'lesson-application-check': LessonApplicationCheck,
    'school-and-major-table': SchoolAndMajorTable,
    'check-student-application': CheckStudentApplication,
    'template-table': TemplateTable
  }
}

</script>

<style scoped>
.welcome{
  text-align: right;
}
</style>
