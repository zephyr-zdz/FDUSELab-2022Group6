<template>
  <el-dialog
    title="课程学生名单"
    top="5vh"
    width="50%"
    :append-to-body="true"
    :visible.sync="dialogVisible"
    :before-close="handleClose">
    <el-table :data="chosenStudentTable"
              style="width: 100%"
              stripe
              pager="page">
      <el-table-column
        label="学号"
        width="200">
        <template v-slot="scope">
          <span>{{ scope.row.student.stunum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="姓名"
        width="200">
        <template v-slot="scope">
          <span>{{ scope.row.student.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="专业"
        >
        <template v-slot="scope">
          <span>{{ scope.row.student.major }}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
export default {
  name: 'teacherCheckChosenStudentList',
  data () {
    return {
      courseid: 0, // default
      dialogVisible: false,
      chosenStudentTable: []
    }
  },
  methods: {
    handleClose () {
      this.dialogVisible = false
      this.$nextTick(() => {
        this.$emit('afterDialog')
        this.dialogVisible = false
      })
    },
    getChosenStudentList () {
      console.log(this.courseid)
      this.$axios.get('/api/teacher/course/student-list', {
        params: {
          courseid: this.courseid
        }
      })
        .then(response => {
          this.chosenStudentTable = response.data.data
          console.log(this.chosenStudentTable)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>

</style>
