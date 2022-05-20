<template>
  <el-card class="box-card">

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
          <span>{{ isPublic(scope.row.course.ispublic) }}</span>
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
          <el-button size="mini" @click="submit(scope.$index)">退课</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>

export default {
  name: 'studentLessonChosen',
  data () {
    return {
      lessonTable: [],
      id: 0
    }
  },
  mounted () {
    this.getStudent()
    this.getLessons()
  },
  methods: {
    isPublic (YN) {
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
      this.getStudent()
      console.log(this.major)
      this.$axios.get('/api/student/course/selected', {params: {stunum: this.$store.getters.username}}).then(res => {
        this.lessonTable = res.data.data
      })
    },
    submit (index) {
      this.$axios.post('/api/student/course/delete', null,
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
  }

}
</script>

<style scoped>

</style>
