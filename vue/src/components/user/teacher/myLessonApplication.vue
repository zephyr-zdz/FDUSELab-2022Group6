<template>
  <el-card class="box-card">
  <el-table :data="myLessonApplicationList"
            style="width: 100%"
            pager="page"
            stripe>
    <el-table-column
      prop="lessonName"
      label="课程名称"
      width="100">
      <template v-slot="scope">
        <span>{{templateList.find(item=>item.id === scope.row.teacherCourseApplication.coursetemplateid).name }}</span>
      </template></el-table-column>
    <el-table-column
      prop="lessonNumber"
      label="课程编号"
      width="80">
      <template v-slot="scope">
        <span>{{ templateList.find(item=>item.id === scope.row.teacherCourseApplication.coursetemplateid).coursenum }}</span>
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
        prop="school"
        label="课程类型"
        width="100">
        <template v-slot="scope">
          <span>{{ isPubilc(scope.row.teacherCourseApplication.ispublic) }}</span>
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
        <span>{{ scope.row.teacherCourseApplication.coursehour }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="points"
      label="学分"
      width="50">
      <template v-slot="scope">
        <span>{{ scope.row.teacherCourseApplication.credit }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="intro"
      label="课程介绍">
      <template v-slot="scope">
        <span>{{ scope.row.teacherCourseApplication.intro }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="time"
      label="上课时间"
      width="120">
      <template v-slot="scope">
        <span>{{ scope.row.teacherCourseApplication.schedule }}</span>
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
        <span>{{ scope.row.teacherCourseApplication.capacity }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="capacity"
      label="申请操作"
      width="80">
      <template v-slot="scope">
        <span>{{ applytype2Chinese(scope.row.teacherCourseApplication.applytype) }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="capacity"
      label="申请结果"
      width="80">
      <template v-slot="scope">
        <span>{{ result2Chinese(scope.row.teacherCourseApplication.result) }}</span>
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
      teacherid: 0,
      jobnum: temp,
      myLessonApplicationList: [],
      templateList: []
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
    isPubilc (YN) {
      if (YN === 'Y') {
        return '通识课程'
      } else {
        return '专业课程'
      }
    },
    result2Chinese (result) {
      switch (result) {
        case 'approve':
          return '通过'
        case 'reject':
          return '拒绝'
        default:
          return '审核中'
      }
    },
    applytype2Chinese (result) {
      switch (result) {
        case 'insert':
          return '新增课程'
        case 'update':
          return '更新课程'
        default:
          return '删除课程'
      }
    },
    getApplication () {
      this.$axios.get('/api/teacher/application/all', {params: {teacherid: this.teacherid}})
        .then(response => {
          if (response.data.code === 0) {
            this.myLessonApplicationList = response.data.data
            // this.$message.success(response.data.msg)
          } else {
            this.$message.error(response.data.msg)
          }
        })
      this.$axios.get('/api/admin/course-template/all')
        .then(response => {
          if (response.data.code === 0) {
            this.templateList = response.data.data
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
