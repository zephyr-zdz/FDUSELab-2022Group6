<template>
  <el-card class="box-card">
    <student-lesson-application ref="studentLessonApplication" @afterApplication="handleClose"></student-lesson-application>
    <el-table :data="lessonTable"
              style="width: 100%"
              pager="page">
      <el-table-column
        prop="semester"
        label="开课学期"
        width="150">
        <template v-slot="scope">
          <span>{{ scope.row.course.semester}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.courseTemplate.name }}</span>
        </template>
        </el-table-column>
      <el-table-column
        prop="number"
        label="课程编号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseTemplate.coursenum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        label="课程类型"
        width="80">
        <template v-slot="scope">
          <span>{{ isPubilc(scope.row.course.ispublic) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="hours"
        label="学时"
        width="50">
        <template v-slot="scope">
          <span>{{ scope.row.course.coursehour }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="points"
        label="学分"
        width="50">
        <template v-slot="scope">
          <span>{{ scope.row.course.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="teacher"
        label="任课教师"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.teacher.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="intro"
        label="课程介绍">
        <template v-slot="scope">
          <span>{{ scope.row.course.intro }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="time"
        label="上课时间"
        width="120">
        <template v-slot="scope">
          <span>{{ calendar(scope.row.calendarList) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="place"
        label="上课地点"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.classroom.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="capacity"
        label="课程容量"
        width="80">
        <template v-slot="scope">
          <span>{{scope.row.course.currentcount}}/{{ scope.row.course.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
        <template v-slot="scope">
          <el-button size="mini" v-if="isFull(scope.$index) !== true" @click="submit(scope.$index)">选课</el-button>
          <el-button size="mini" v-if="isFull(scope.$index) === true" @click="showApplication(scope.$index)">
            选课申请
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import StudentLessonApplication from './studentLessonApplication'
export default {
  name: 'studentCheckLesson',
  data () {
    return {
      lessonTable: [],
      major: '',
      id: 0
    }
  },
  mounted () {
    this.getStudent()
    this.getLessons()
  },
  methods: {
    isFull (index) {
      return this.lessonTable[index].course.capacity === this.lessonTable[index].course.currentcount
    },
    showApplication (index) {
      this.$refs.studentLessonApplication.dialogVisible = true
      this.$refs.studentLessonApplication.course = this.lessonTable[index]
      console.log(this.lessonTable[index])
      this.$refs.studentLessonApplication.course2Form()
      this.$refs.studentLessonApplication.id = this.id
    },
    handleClose () {
      this.getLessons()
    },
    isPubilc (YN) {
      if (YN === 'Y') {
        return '通识课程'
      } else {
        return '专业课程'
      }
    },
    calendar (calendarList) {
      var schedule = ''
      for (var i = 0; i < calendarList.length; i++) {
        schedule += (calendarList[i].day + ',' + calendarList[i].number)
        schedule += ' '
      }
      return schedule
    },
    getStudent () {
      var num = this.$store.getters.username
      this.$axios.get('/api/student/student', {params: {stunum: num}}).then(res => {
        this.major = res.data.data.major
        this.id = res.data.data.id
      })
    },
    getLessons () {
      console.log(this.major)
      this.$axios.get('/api/student/course/major', {params: {major: this.major}}).then(res => {
        this.lessonTable = res.data.data
      })
    },
    submit (index) {
      this.$axios.post('/api/student/course/choose', null,
        {params: {
          courseid: this.lessonTable[index].course.id,
          studentid: this.id
        }}
      )
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: res.data.msg,
              type: 'success'
            })
            this.getLessons()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
        })
    }
  },
  components: {
    'student-lesson-application': StudentLessonApplication
  }
}
</script>

<style scoped>

</style>
