<template>
  <el-card class="box-card">
    <add-lesson ref="addLesson" @afterAddLesson="handleClose"></add-lesson>
    <edit-lesson ref="editLesson" @afterAddLesson="handleClose"></edit-lesson>
    <lesson-info ref="lessonInfo" @afterLessonInfo="handleClose"></lesson-info>
    <chosen-student-list ref="lessonStudentList"></chosen-student-list>

    <el-button :disabled="lessonEdit" type="success" size="small" @click="addRow()">新建课程</el-button>

    <el-table :data="lessonTable"
              style="width: 100%"
              stripe
              pager="page">
      <el-table-column
        prop="semester"
        label="开课学期"
        width="150">
        <template v-slot="scope">
          <span>{{ scope.row.course.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称">
        <template v-slot="scope">
          <span>{{ scope.row.courseTemplate.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="number"
        label="课程编号"
        width="100">
        <template v-slot="scope">
          <span>{{ scope.row.courseTemplate.coursenum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="school"
        label="课程类型"
        width="100">
        <template v-slot="scope">
          <span>{{ isPubilc(scope.row.course.ispublic) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="school"
        label="开课院系"
        width="100">
        <template v-slot="scope">
          <span>{{ scope.row.instituteOfTeacher.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="hours"
        label="学时"
        width="60">
        <template v-slot="scope">
          <span>{{ scope.row.course.coursehour }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="points"
        label="学分"
        width="60">
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
        prop="time"
        label="上课时间">
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
        label="已选人数"
        width="100">
        <template v-slot="scope">
          <span>{{ scope.row.course.currentcount }}/{{ scope.row.course.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="180"
       >
        <template v-slot="scope">
          <el-button size="mini" type="text" @click="showInfo()">修改</el-button>
          <el-button size="mini" type="text" @click="showInfo()">详细</el-button>
          <el-button size="mini" type="text" @click="showChosenStudent(scope.$index)">已选学生名单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import AddLesson from './addLesson'
import EditLesson from './editLesson'
import LessonInfo from './lessonInfo'
import AdminCheckChosenStudentList from './adminCheckChosenStudentList'

export default {
  name: 'lessonTable',
  data () {
    return {
      lessonTable: []
    }
  },
  methods: {
    handleClose () {
      this.getLessons()
    },
    showEditLesson (index) {
      this.$refs.editLesson.dialogVisible = true
      this.$refs.editLesson.course = this.lessonTable[index].course
    },
    showInfo (index) {
      this.$refs.lessonInfo.dialogVisible = true
      this.$refs.lessonInfo.course = this.lessonTable[index].course
    },
    showChosenStudent (index) {
      this.$refs.lessonStudentList.dialogVisible = true
      this.$refs.lessonStudentList.courseid = this.lessonTable[index].course.id
      this.$nextTick(() => {
        this.$refs.lessonStudentList.getChosenStudentList()
      })
      console.log(this.lessonTable[index].course.id)
    },
    isPubilc (YN) {
      if (YN === 'Y') {
        return '通用修读'
      } else {
        return '专业修读'
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
    getLessons () {
      this.$axios.get('/api/admin/teacher-course/all').then(res => {
        this.lessonTable = res.data.data
      })
    }
  },
  components: {
    'add-lesson': AddLesson,
    'edit-lesson': EditLesson,
    'lesson-info': LessonInfo,
    'chosen-student-list': AdminCheckChosenStudentList
  }
}

</script>

<style scoped>

</style>
