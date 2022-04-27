<template>
  <el-card class="box-card">
    <el-table :data="classroomArrange"
              style="width: 100%"
              stripe
              align="center"
              pager="page">

      <el-table-column
        prop="classroom"
        label="教室"
        width="200"
        style="text-align: center">
        <template v-slot="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="200">
        <template v-slot="scope">
          <span v-show="!(classroomEdit&&(scope.$index===editingIndex))">{{scope.row.state}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="最大人数"
        width="200">
        <template v-slot="scope">
          <span v-show="!(classroomEdit&&(scope.$index===editingIndex))">{{scope.row.capacity}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
          <template v-slot="scope">
            <el-button v-if="scope.row.state === 'on'" size="mini" type="warning" @click="disable(scope.row,scope.$index)">禁用</el-button>
            <el-button v-if="scope.row.state === 'off'" size="mini" type="success" @click="enable(scope.row,scope.$index)">启用</el-button>
          </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  name: 'classroomArrange',
  data () {
    return {
      classroomArrange: [],
      editingRow: {},
      editingIndex: '',
      classroomEdit: false
    }
  },
  methods: {
    handleClose () {
      location.reload()
    },
    getClassroom () {
      this.$axios.get('/api/admin/Classroom/getAllClassroom')
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.classroomArrange = res.data.data
          } else {
            this.$message({
              type: 'error',
              message: res.data.msg
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    disable (row, index) {
      this.$axios.get('/api/admin/Classroom/closeClassroomState', {params: {
        Name: row.name
      }}).then(res => {
        if (res.data.code === 0) {
          this.classroomArrange[index].state = 'off'
          this.$message({
            type: 'success',
            message: '禁用成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        }
      })
    },
    enable (row, index) {
      this.$axios.get('/api/admin/Classroom/openClassroomState', {params: {
        Name: row.name
      }}).then(res => {
        if (res.data.code === 0) {
          this.classroomArrange[index].state = 'on'
          this.$message({
            type: 'success',
            message: '启用成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
