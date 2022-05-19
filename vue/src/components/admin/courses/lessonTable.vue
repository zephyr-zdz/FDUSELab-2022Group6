<template>
  <el-card class="box-card">
    <add-lesson ref="addLesson" @afterAddLesson="handleClose"></add-lesson>
    <lesson-info ref="lessonInfo"></lesson-info>
    <chosen-student-list ref="lessonStudentList"></chosen-student-list>

    <el-button :disabled="lessonEdit" type="success" size="small" @click="addRow()">增加</el-button>
    <el-table :data="lessonTable"
              style="width: 100%"
              stripe
              pager="page">
      <el-table-column
        prop="semester"
        label="开课学期"
        width="150">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.course.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="80">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.courseTemplate.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="number"
        label="课程编号"
        width="150">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.courseTemplate.coursenum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="school"
        label="课程类型"
        width="100">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.instituteOfTeacher.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="hours"
        label="学时"
        width="80">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.course.coursehour }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="points"
        label="学分"
        width="80">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.course.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="teacher"
        label="任课教师"
        width="100">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.teacher.name }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="time"
        label="上课时间"
        width="100">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ calendar(scope.row.calendarList) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="place"
        label="上课地点"
        width="80">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.classroom.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="capacity"
        label="课程容量"
        width="150">
        <template v-slot="scope">
          <span v-show="!(lessonEdit&&(scope.$index===editingIndex))">{{ scope.row.course.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
       >
        <template v-slot="scope">
          <el-button size="mini" type="success" @click="showInfo()">查看详细信息</el-button>
          <el-button size="mini" type="info" @click="showChosenStudent(scope.$index)">查看已选学生名单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button size="mini" type="info" @click="showChosenStudent(scope.$index)">查看已选学生名单</el-button>
  </el-card>
</template>

<script>
import AddLesson from './addLesson'
import LessonInfo from './lessonInfo'
import AdminCheckChosenStudentList from './adminCheckChosenStudentList'

export default {
  name: 'lessonTable',
  data () {
    return {
      schoolOptions: [],
      classroomOptions: [],
      lessonTable: [],
      lessonEdit: false,
      dialogVisible: false,
      infoVisible: false,
      chosenStudentVisible: false,
      editingIndex: -1,
      school: '',
      application: {},
      teacherid: '',
      editingCalendar: '',
      rowSchedule: ''
    }
  },
  methods: {
    handleClose () {
      this.getLessons()
    },
    showInfo (index) {
      this.$refs.lessonInfo.infoVisible = true
      this.$refs.lessonInfo.course = this.lessonTable[index].course
    },
    showChosenStudent (index) {
      this.chosenStudentVisible = true
      this.$refs.lessonStudentList.courseid = this.lessonTable[index].course.id
    },
    isValid (row, index) {
      var coursehour = parseInt(this.lessonTable[index].course.coursehour)
      var credit = parseFloat(this.lessonTable[index].course.credit)
      var capacity = parseInt(this.lessonTable[index].course.capacity)

      if (!coursehour) {
        this.$alert('学时不应为0')
        return false
      } else if (!credit) {
        this.$alert('学分不应为0')
        return false
      } else if (credit % 0.5 !== 0) {
        this.$alert('学分应为0.5的倍数')
        return false
      } else if (!capacity) {
        this.$alert('课程容量不应为0')
        return false
      } else if (!this.lessonTable[index].course.name) {
        this.$alert('课程名称不应为空')
        return false
      } else if (!this.lessonTable[index].course.coursenum) {
        this.$alert('课程编号不应为空')
        return false
      } else if (!this.lessonTable[index].institute.id) {
        this.$alert('开课院系不应为空')
        return false
      } else if (!this.lessonTable[index].teacher.jobnum) {
        this.$alert('任课教师不应为空')
        return false
      } else if (!this.editingCalendar) {
        this.$alert('上课时间不应为空')
        return false
      } else if (!this.lessonTable[index].classroom.id) {
        this.$alert('上课地点不应为空')
        return false
      }
      return true
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
    getTeacherId (index) {
      this.$axios.get('/api/admin/teacher-course/id-by-jobnum', {params: {JobNum: this.lessonTable[index].teacher.jobnum}})
        .then(response => {
          console.log(response.data.data)
          if (response.data.code === 0) {
            this.lessonTable[index].teacher.id = response.data.data
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
          console.log('教师工号不存在')
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
    // 制作表单
    makeApplication (index, type) {
      if (type === 'update') { this.rowSchedule = this.editingCalendar }
      this.application = {
        coursename: this.lessonTable[index].course.name,
        coursenum: this.lessonTable[index].course.coursenum,
        coursehour: '' + this.lessonTable[index].course.coursehour,
        credit: '' + this.lessonTable[index].course.credit,
        teacherid: this.lessonTable[index].teacher.id,
        instituteid: this.lessonTable[index].institute.id,
        intro: this.lessonTable[index].course.intro,
        schedule: this.rowSchedule,
        classroomid: this.lessonTable[index].classroom.id,
        capacity: '' + this.lessonTable[index].course.capacity,
        applytype: type,
        pre_courseId: this.lessonTable[index].course.id,
        result: 'approve',
        applytime: new Date().getTime()
      }
      this.editingCalendar = ''
    },
    // 增，改开，改关，改取消，删
    addRow () {
      this.$refs.addLesson.getClassrooms()
      this.$refs.addLesson.dialogVisible = true
    },
    confirmRow (row, index) {
      this.makeApplication(index, 'update')
      this.$axios.post('/api/admin/teacher-course/modify', this.application)
        .then(response => {
          if (response.data.code === 0) {
            this.getLessons()
            this.$message({
              message: '修改成功',
              type: 'success'
            })
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
      this.getLessons()
      this.editingRow = -1
      this.lessonEdit = false
    },
    deleteRow (row, index) {
      this.calendar(row.calendarList)
      console.log(this.rowSchedule)
      console.log(row.calendarList)
      this.makeApplication(index, 'delete')
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('/api/admin/teacher-course/modify', this.application, {params: {pre_courseId: this.lessonTable[index].course.id}}).then(res => {
          if (res.data.code === 0) {
            this.getLessons()
            this.$message({
              message: '删除成功',
              type: 'success'
            })
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
        })
        // TODO: delete
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },
  components: {
    'add-lesson': AddLesson,
    'lesson-info': LessonInfo,
    'chosen-student-list': AdminCheckChosenStudentList
  }
}

</script>

<style scoped>

</style>
