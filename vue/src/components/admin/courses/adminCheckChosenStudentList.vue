<template>
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
</template>

<script>
export default {
  name: 'adminCheckChosenStudentList',
  data () {
    return {
      courseid: 0,
      chosenStudentTable: []
    }
  },
  mounted () {
    this.getChosenStudentList()
  },
  methods: {
    getChosenStudentList () {
      this.$axios.get('/api/admin/student-course/student-list', {
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
