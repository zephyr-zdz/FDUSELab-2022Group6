<template>
  <el-card class="box-card">
    <el-table :data="checkStudentApplication"
              style="width: 100%"
              pager="page"
              stripe>

      <el-table-column
        prop="semester"
        label="开课学期"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseVO.course.semester }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="lessonName"
        label="课程名称"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseVO.courseTemplate.coursename }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="lessonNumber"
        label="课程编号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseVO.courseTemplate.coursenum }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="name"
        label="学生姓名"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.student.name }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="stunum"
        label="学号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.student.stunum }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="reason"
        label="申请理由"
        >
        <template v-slot="scope">
          <span>{{ scope.row.studentCourseApplication.explanation }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="operation"
        label="操作"
        width="160">
        <template v-slot="scope">
          <el-button size="mini" type="success" @click="handleApproval('true' ,scope.$index)">通过</el-button>
          <el-button size="mini" type="danger" @click="handleApproval('false' ,scope.$index)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  name: 'checkStudentApplication',
  data () {
    return {
      checkStudentApplication: [],
      info: []
    }
  },
  methods: {
    handleApproval (attitude, index) {
      if (attitude === 'true') {
        this.approved(index)
      } else {
        this.rejected(index)
      }
    },
    approved (index) {
      this.$axios.post('/api/admin/student-course/approve',
        this.checkStudentApplication[index].studentCourseApplication,
        {params: { attitude: true }})
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: '审批成功',
              type: 'success'
            })
            this.getApplication()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
            this.getApplication()
          }
        })
        .catch(err => {
          this.$message({
            message: err,
            type: 'error'
          })
          this.getApplication()
        })
    },
    rejected (index) {
      this.$axios.post('/api/admin/student-course/approve',
        this.checkStudentApplication[index].studentCourseApplication,
        {params: { attitude: false }})
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: '审批成功',
              type: 'success'
            })
            this.getApplication()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
            this.getApplication()
          }
        })
    },
    getApplication () {
      this.$axios.get('/api/admin/student-course/student-application-list/pending')
        .then(response => {
          if (response.data.code === 0) {
            this.checkStudentApplication = response.data.data
            // this.$message.success(response.data.msg)
          } else {
            this.$message.error(response.data.msg)
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    }

  }
}
</script>

<style scoped>

</style>
