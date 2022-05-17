<template>
  <el-card class="box-card">
    <el-dialog
      title="选课申请"
      top="5vh"
      width="50%"
      :append-to-body="true"
      :visible.sync="applicationVisible"
      :before-close="handleClose">
      <student-lesson-application></student-lesson-application>
    </el-dialog>
    <el-table :data="lessonTable"
              style="width: 100%"
              pager="page">
      <el-table-column
        prop="semester"
        label="开课学期"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.course.semester}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.course.name }}</span>
        </template>
        </el-table-column>
      <el-table-column
        prop="number"
        label="课程编号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.course.coursenum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        label="课程类型"
        width="80">
        <template v-slot="scope">
          <span>{{ scope.row.course.type }}</span>
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
          <span>{{scope.row.course.chosen}}/{{ scope.row.course.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
        <el-button size="mini">选课</el-button>
      </el-table-column>
    </el-table>
    <el-button size="mini" v-if="isFull() === true" @click="showApplication()">
      选课申请
    </el-button>
<!--    TODO：到时候把按钮放到行内，要给选课按钮加v-if-->
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
      applicationVisible: false
    }
  },
  mounted () {
    this.getLessons()
  },
  methods: {
    isFull () {
      return true
    },
    showApplication () {
      this.applicationVisible = true
    },
    handleClose () {
      this.applicationVisible = false
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
      })
    },
    getLessons () {
      this.getStudent()
      console.log(this.major)
      this.$axios.get('/api/student/course', {params: {major: this.major}}).then(res => {
        this.lessonTable = res.data.data
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
