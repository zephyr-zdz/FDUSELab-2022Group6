<template>
  <el-card class="box-card">
    <el-table :data="myLessonApplication"
              stripe
              style="width: 100%"
              pager="page">
      <el-table-column
        prop="name"
        label="申请操作类型"
        width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.applytype }}</span>
        </template>
        </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.coursename }}</span>
        </template>
        </el-table-column>
      <el-table-column
        prop="lessonNumber"
        label="课程编号"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.coursenum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="school"
        label="开课院系"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.instituteid }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="hours"
        label="学时"
        width="50">
        <template slot-scope="scope">
          <span>{{ scope.row.coursehour }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="points"
        label="学分"
        width="50">
        <template slot-scope="scope">
          <span>{{ scope.row.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="intro"
        label="课程介绍">
        <template slot-scope="scope">
          <span>{{ scope.row.intro }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="time"
        label="上课时间"
        width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.schedule }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="place"
        label="上课地点"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.classroomid }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="capacity"
        label="课程容量"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.result }}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  name: 'myLessonApplication',
  data () {
    var temp = this.$store.getters.username
    return {
      jobnum: temp,
      myLessonApplication: []
    }
  },
  methods: {
    getApplication () {
      console.log('getApplication')
      console.log(this.$store.getters.username)
      this.$axios.post('/api/teacher/application/getMyApplicationByJobNum', null, {params: {JobNum: this.jobnum}})
        .then(response => {
          if (response.data.code === 0) {
            this.myLessonApplication = response.data.data
            // this.$message.success(response.data.msg)
          } else {
            this.$message.error(response.data.msg)
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
