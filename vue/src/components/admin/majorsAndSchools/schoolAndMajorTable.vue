<template>
  <el-card class="box-card">
    <el-dialog
      title="新增学院"
      top="5vh"
      width="50%"
      :append-to-body="true"
      v-if="this.addSchoolVisible===true"
      :visible.sync="addSchoolVisible"
      :before-close="handleClose">
      <add-school></add-school>
    </el-dialog>
    <el-dialog
      title="新增专业"
      top="5vh"
      width="50%"
      :append-to-body="true"
      v-if="this.addMajorVisible===true"
      :visible.sync="addMajorVisible"
      :before-close="handleClose">
      <add-major label="addMajor"
            id="addMajor"
            ref="addMajor"></add-major>
    </el-dialog>
    <el-dialog
      title="删除学院"
      top="5vh"
      width="50%"
      :append-to-body="true"
      v-if="this.deleteSchoolVisible===true"
      :visible.sync="deleteSchoolVisible"
      :before-close="handleClose">
      <delete-school></delete-school>
    </el-dialog>
    <el-button :disabled="schoolAndMajorEdit" type="success" size="small" @click="addSchool()">新增学院</el-button>
    <el-button :disabled="schoolAndMajorEdit" type="success" size="small" @click="addMajor()">新增专业</el-button>
    <el-button :disabled="schoolAndMajorEdit" type="danger" size="small" @click="deleteSchool()">删除学院</el-button>
      <el-table :data="schoolAndMajorTable"
                style="width: 100%"
                stripe
                align="center"
                pager="page">

        <el-table-column
          prop="school"
          label="学院"
          width="200"
          style="text-align: center">
          <template v-slot="scope">
            <el-input
              v-model="scope.row.institute"
              v-show="(schoolAndMajorEdit&&(scope.$index===editingIndex))"
              size="small">
            </el-input>
            <span v-show="!(schoolAndMajorEdit&&(scope.$index===editingIndex))">{{scope.row.institute}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="major"
          label="专业"
          width="200">
          <template v-slot="scope">
            <el-input
              v-model="scope.row.name"
              v-show="(schoolAndMajorEdit&&(scope.$index===editingIndex))"
              size="small">
            </el-input>
            <span v-show="!(schoolAndMajorEdit&&(scope.$index===editingIndex))">{{scope.row.name}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="operation"
          label="操作"
          width="150"
        >
          <template v-slot="scope">
            <el-button v-if="!(schoolAndMajorEdit&&(scope.$index===editingIndex))" size="mini" type="warning" @click="editRow(scope.row,scope.$index)">修改</el-button>
            <el-button v-if="!(schoolAndMajorEdit&&(scope.$index===editingIndex))" size="mini" type="danger" @click="deleteRow(scope.row,scope.$index)">删除</el-button>
            <el-button v-if="(schoolAndMajorEdit&&(scope.$index===editingIndex))" size="mini" type="success" @click="confirmRow(scope.row,scope.$index)">确定</el-button>
            <el-button v-if="(schoolAndMajorEdit&&(scope.$index===editingIndex))" size="mini" type="info" @click="cancelRow(scope.row,scope.$index)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
  </el-card>
</template>

<script>
import AddSchool from './addSchool'
import AddMajor from './addMajor'
import DeleteSchool from './deleteSchool'

export default {
  name: 'schoolAndMajorTable',
  data () {
    return {
      schoolAndMajorTable: [],
      editingRow: {},
      editingIndex: '',
      schoolAndMajorEdit: false,
      addSchoolVisible: false,
      addMajorVisible: false,
      deleteSchoolVisible: false
    }
  },
  methods: {
    handleClose () {
      this.addSchoolVisible = false
      this.addMajorVisible = false
      this.deleteSchoolVisible = false
      this.getSchoolAndMajor()
    },
    getSchoolAndMajor () {
      this.$axios.get('/api/admin/major/all')
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.schoolAndMajorTable = res.data.data
          } else {
            this.$message.error('获取失败')
          }
        })
    },
    addSchool () {
      // this.$refs.addMajor.getSchoolOptions()
      this.getSchoolAndMajor()
      this.addSchoolVisible = true
    },
    addMajor () {
      // this.$refs.addMajor.getSchoolOptions()
      this.getSchoolAndMajor()
      this.addMajorVisible = true
    },
    deleteSchool () {
      // this.$refs.addMajor.getSchoolOptions()
      this.getSchoolAndMajor()
      this.deleteSchoolVisible = true
    },
    editRow (row, index) {
      console.log(row)
      console.log(index)
      console.log(this.schoolAndMajorTable)
      this.getSchoolAndMajor()
      this.editingRow = Object.assign({}, this.schoolAndMajorTable[index])
      this.editingIndex = index
      this.schoolAndMajorEdit = true
      console.log('editing')
      console.log(this.editingRow)
    },
    deleteRow (row, index) {
      // axios
      this.$axios.delete('/api/admin/major', {params: {majorId: row.id}})
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.schoolAndMajorTable.splice(index, 1)
            this.$message.success('删除成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      console.log('delete')
      // 发送
    },
    confirmRow (row, index) {
      this.schoolAndMajorEdit = false
      console.log('confirm')
      // 发送
      console.log(this.schoolAndMajorTable[index])
      this.$axios.put('/api/admin/major', this.schoolAndMajorTable[index])
        .then(res => {
          console.log('res ok')
          console.log(res.data)
          if (res.data.code === 0) {
            this.$message.success('修改成功')
            this.editingIndex = -1
            this.schoolAndMajorEdit = false
            this.getSchoolAndMajor()
          } else {
            this.$message.error(res.data.msg)
            console.log(this.editingRow)
            this.editingIndex = -1
            this.schoolAndMajorEdit = false
            this.getSchoolAndMajor()
          }
        })
    },
    cancelRow (row, index) {
      console.log(row)
      console.log(this.editingRow)
      this.editingIndex = -1
      this.schoolAndMajorEdit = false
      this.getSchoolAndMajor()
    }
  },
  components: {
    'add-school': AddSchool,
    'add-major': AddMajor,
    'delete-school': DeleteSchool
  }
}
</script>

<style scoped>

</style>
