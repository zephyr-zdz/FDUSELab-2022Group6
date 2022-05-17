<template>
  <el-card class="box-card">
  <el-table :data="lessonApplicationCheck"
            style="width: 100%"
            pager="page"
            stripe>
    <el-table-column
      prop="lessonName"
      label="课程名称"
      width="100">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.coursename }}</span>
      </template></el-table-column>
    <el-table-column
      prop="lessonNumber"
      label="课程编号"
      width="80">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.coursenum }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="school"
      label="开课院系"
      width="80">
      <template v-slot="scope">
        <span>{{ scope.row.institute.name }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="jobnum"
      label="教师姓名"
      width="80">
      <template v-slot="scope">
        <span>{{ scope.row.teacher.name }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="hours"
      label="学时"
      width="50">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.coursehour }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="points"
      label="学分"
      width="50">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.credit }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="intro"
      label="课程介绍">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.intro }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="time"
      label="上课时间"
      width="120">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.schedule }}</span>
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
        <span>{{ scope.row.courseApplication.capacity }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="capacity"
      label="申请操作"
      width="80">
      <template v-slot="scope">
        <span>{{ scope.row.courseApplication.applytype }}</span>
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
  name: 'lessonApplicationCheck',
  data () {
    return {
      lessonApplicationCheck: [],
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
    //     this.lessonApplicationCheck[index].jobnum = response.data.data
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
        this.lessonApplicationCheck[index].courseApplication,
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
        this.lessonApplicationCheck[index].courseApplication,
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
      this.$axios.get('/api/admin/course/pending')
        .then(response => {
          if (response.data.code === 0) {
            this.lessonApplicationCheck = response.data.data
            // this.$message.success(response.data.msg)
          } else {
            this.$message.error(response.data.msg)
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
      // this.getName()
    }
    // getName () {
    //   console.log('name')
    //   var length = this.lessonApplicationCheck.length
    //   for (var index = 0; index < length; index++) {
    //     (function (index) {
    //       this.$axios.get('/api/admin/institute/getNameById', {params: {id: this.lessonApplicationCheck[index].instituteid}}).then(response => {
    //         console.log(response.data.data + ':' + index)
    //         this.$set(this.lessonApplicationCheck[index], 'institute', response.data.data)
    //       // this.lessonApplicationCheck[index].institute = response.data.data
    //       })
    //       this.$axios.get('/api/admin/course/getTeacherJobNumById', {params: {Id: this.lessonApplicationCheck[index].teacherid}}).then(response => {
    //         this.$set(this.lessonApplicationCheck[index], 'jobnum', response.data.data)
    //       })
    //     })(index)
    //   }
    // }
  }
}
</script>

<style scoped>

</style>
