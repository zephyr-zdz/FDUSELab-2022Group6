<template>
  <el-table :data="chosenStudentTable"
            style="width: 100%"
            stripe
            pager="page">
    <el-table-column
      prop="stunum"
      label="学号"
      width="200">
      <template v-slot="scope">
        <span>{{ scope.row.stunum }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="200">
      <template v-slot="scope">
        <span>{{ scope.row.name }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="major"
      label="专业"
      >
      <template v-slot="scope">
        <span>{{ scope.row.major }}</span>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: 'teacherCheckChosenStudentList',
  data () {
    return {
      chosenStudentTable: []
    }
  }
}
</script>

<style scoped>

</style>
