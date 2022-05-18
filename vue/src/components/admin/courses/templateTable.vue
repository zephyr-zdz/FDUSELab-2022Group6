<template>
  <el-card class="box-card">
    <el-dialog
      title="新增课程模板"
      top="5vh"
      width="50%"
      :append-to-body="true"
      v-if="this.addTemplateVisible===true"
      :visible.sync="addTemplateVisible"
      :before-close="handleClose">
      <add-template></add-template>
    </el-dialog>

<!--    <el-dialog-->
<!--      title="删除课程模板"-->
<!--      top="5vh"-->
<!--      width="50%"-->
<!--      :append-to-body="true"-->
<!--      v-if="this.deleteTemplateVisible===true"-->
<!--      :visible.sync="deleteTemplateVisible"-->
<!--      :before-close="handleClose">-->
<!--      <delete-template></delete-template>-->
<!--    </el-dialog>-->
    <el-button :disabled="templateEdit" type="success" size="small" @click="addSchool()">新增课程模板</el-button>
<!--    <el-button :disabled="templateEdit" type="danger" size="small" @click="deleteSchool()">删除课程模板</el-button>-->
    <el-table :data="templateTable"
              style="width: 100%"
              stripe
              align="center"
              pager="page">

      <el-table-column
        prop="coursenum"
        label="课程序号"
        width="200"
        style="text-align: center">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.coursenum"
            v-show="(templateEdit&&(scope.$index===editingIndex))"
            size="small">
          </el-input>
          <span v-show="!(templateEdit&&(scope.$index===editingIndex))">{{scope.row.coursenum}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="200">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.name"
            v-show="(templateEdit&&(scope.$index===editingIndex))"
            size="small">
          </el-input>
          <span v-show="!(templateEdit&&(scope.$index===editingIndex))">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150"
      >
        <template v-slot="scope">
          <el-button v-if="!(templateEdit&&(scope.$index===editingIndex))" size="mini" type="warning" @click="editRow(scope.row,scope.$index)">修改</el-button>
          <el-button v-if="!(templateEdit&&(scope.$index===editingIndex))" size="mini" type="danger" @click="deleteRow(scope.row,scope.$index)">删除</el-button>
          <el-button v-if="(templateEdit&&(scope.$index===editingIndex))" size="mini" type="success" @click="confirmRow(scope.row,scope.$index)">确定</el-button>
          <el-button v-if="(templateEdit&&(scope.$index===editingIndex))" size="mini" type="info" @click="cancelRow(scope.row,scope.$index)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import AddTemplate from './addTemplate'
import DeleteTemplate from './deleteTemplate'

export default {
  name: 'templateTable',
  data () {
    return {
      templateTable: [],
      editingRow: {},
      editingIndex: '',
      templateEdit: false,
      addTemplateVisible: false,
      deleteTemplateVisible: false
    }
  },
  methods: {
    handleClose () {
      this.addTemplateVisible = false
      this.deleteTemplateVisible = false
      this.getTemplate()
    },
    getTemplate () {
      // TODO：接口名可能有错
      this.$axios.get('/api/admin/course-template')
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.templateTable = res.data.data
          } else {
            this.$message.error('获取失败')
          }
        })
    },
    addTemplate () {
      // this.$refs.addMajor.getSchoolOptions()
      this.getTemplate()
      this.addSchoolVisible = true
    },

    deleteTemplate () {
      // this.$refs.addMajor.getSchoolOptions()
      this.getTemplate()
      this.deleteSchoolVisible = true
    },

    editRow (row, index) {
      console.log(row)
      console.log(index)
      console.log(this.Table)
      this.getTemplate()
      this.editingRow = Object.assign({}, this.templateTable[index])
      this.editingIndex = index
      this.templateEdit = true
      console.log('editing')
      console.log(this.editingRow)
    },
    deleteRow (row, index) {
      // axios
      // TODO:接口名可能有误
      this.$axios.delete('/api/admin/course-template', {params: {major: row.name}})
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.templateTable.splice(index, 1)
            this.$message.success('删除成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      console.log('delete')
      // 发送
    },
    confirmRow (row, index) {
      this.templateEdit = false
      console.log('confirm')
      // 发送
      console.log(this.templateTable[index])
      // TODO:接口名可能有误
      this.$axios.put('/api/admin/course-template', this.templateTable[index])
        .then(res => {
          console.log('res ok')
          console.log(res.data)
          if (res.data.code === 0) {
            this.$message.success('修改成功')
            this.editingIndex = -1
            this.templateEdit = false
            this.getTemplate()
          } else {
            this.$message.error(res.data.msg)
            console.log(this.editingRow)
            this.editingIndex = -1
            this.templateEdit = false
            this.getTemplate()
          }
        })
    },
    cancelRow (row, index) {
      console.log(row)
      console.log(this.editingRow)
      this.editingIndex = -1
      this.templateEdit = false
      this.getTemplate()
    }
  },
  components: {
    'add-template': AddTemplate,
    'delete-template': DeleteTemplate
  }
}
</script>

<style scoped>

</style>
