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
          <span>{{ scope.row.courseApplication.semester }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="lessonName"
        label="课程名称"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseApplication.coursename }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="lessonNumber"
        label="课程编号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseApplication.coursenum }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="name"
        label="学生姓名"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseApplication.name }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="stunum"
        label="学号"
        width="120">
        <template v-slot="scope">
          <span>{{ scope.row.courseApplication.stunum }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="reason"
        label="申请理由"
        >
        <template v-slot="scope">
          <span>{{ scope.row.courseApplication.reason }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="operation"
        label="操作"
        width="160">
        <template v-slot="scope">
          <el-button size="mini" type="success" @click="handleApproval('true' ,scope.$index)">通过</el-button>
          <el-button size="mini" type="danger" @click="handleApproval('false' ,scope.$index)">未通过</el-button>
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
    // getTeacherJobnumById (index, id) { // TODO: teacherid
    //   this.$axios.get('/api/admin/course/getTeacherJobNumById', {params: {Id: id}}).then(response => {
    //     this.checkStudentApplication[index].jobnum = response.data.data
    //   })
    // },
    // TODO: 教师名字
    // getTeacherNameById (id) {
    //   this.$axios.get('/api/admin/institute', {params: {instituteId: id}}).then(response => {
    //     return response.data.data
    //   })
    // },
    approved (index) {
      this.$axios.post('/api/admin/course/approve',
        this.checkStudentApplication[index].courseApplication,
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
      this.$axios.post('/api/admin/course/approve',
        this.checkStudentApplication[index].courseApplication,
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
    // TODO：改为获取学生申请
    getApplication () {
      this.$axios.get('/api/admin/course/pending')
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
