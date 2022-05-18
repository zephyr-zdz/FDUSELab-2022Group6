<template>
  <el-card class="box-card">
    <el-dialog
      title="学生注册"
      top="5vh"
      width="50%"
      :append-to-body="true"
      :visible.sync="dialogVisible"
      :before-close="handleClose">
      <reg></reg>
    </el-dialog>
    <el-button :disabled="studentEdit" type="success" size="small" @click="addRow()">增加</el-button>
    <el-table :data="studentTable"
              style="width: 100%"
              stripe
              pager="page">
      <el-table-column label="状态" width="105">
        <template v-slot="scope">
          <el-select v-model="scope.row.status" v-show="(studentEdit&&(scope.$index===editingIndex))" placeholder="请选择" size="mini">
            <el-option
              v-for="(item) in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{ statusName(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="学院"
        width="80">
        <template v-slot="scope">
          <el-select v-model="scope.row.institute"
          v-show="(studentEdit&&(scope.$index===editingIndex))"
          placeholder="请选择"
          size="mini"
          @change="getMajorOptions(scope.row.institute)">
            <el-option
              v-for="item in schoolOptions"
              :key="item.name"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.institute}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="专业"
        width="80">
        <template v-slot="scope">
          <el-select v-model="scope.row.major" v-show="(studentEdit&&(scope.$index===editingIndex))" placeholder="请选择" size="mini">
            <el-option
              v-for="item in majorOptions"
              :key="item.name"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.major}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="学号"
        width="100">
        <template v-slot="scope">
          <span>{{scope.row.stunum}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="100">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.name"
            v-show="(studentEdit&&(scope.$index===editingIndex))"
            placeholder="英文或汉字"
            size="small">
          </el-input>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="identity"
        label="身份证号"
        width="200">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.identity"
            v-show="(studentEdit&&(scope.$index===editingIndex))"
            oninput="value=value.replace(/[^\dX]/g,'')"
            placeholder="满足身份证号的规则（18位）"
            maxlength="18">
          </el-input>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.identity}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="tel"
        label="手机"
        width="180">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.tel"
            v-show="(studentEdit&&(scope.$index===editingIndex))"
            oninput="value=value.replace(/[^\d]/g,'')"
            placeholder="11位纯数字，1开头" maxlength="11">
          </el-input>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.tel}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.email"
            v-show="(studentEdit&&(scope.$index===editingIndex))"
            placeholder="用户标识符+ @ + 域名"
            size="small">
          </el-input>
          <span v-show="!(studentEdit&&(scope.$index===editingIndex))">{{scope.row.email}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
          <template v-slot="scope">
            <el-button v-if="!(studentEdit&&(scope.$index===editingIndex))" size="mini" type="warning" @click="editRow(scope.row,scope.$index)">修改</el-button>
            <el-button v-if="!(studentEdit&&(scope.$index===editingIndex))" size="mini" type="danger" @click="deleteRow(scope.row,scope.$index)">删除</el-button>
            <el-button v-if="(studentEdit&&(scope.$index===editingIndex))" size="mini" type="success" @click="confirmRow(scope.row,scope.$index)">确定</el-button>
            <el-button v-if="(studentEdit&&(scope.$index===editingIndex))" size="mini" type="info" @click="cancelRow(scope.row,scope.$index)">取消</el-button>
          </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import Register from '../register'
export default {
  name: 'studentTable',
  data () {
    return {
      statusOptions: [
        {
          value: 'Y',
          label: '在读'
        }, {
          value: 'N',
          label: '休学'
        }],
      majorOptions: [],
      schoolOptions: [],
      studentTable: [],
      editingRow: {},
      editingIndex: '',
      studentEdit: false,
      dialogVisible: false
    }
  },
  methods: {
    handleClose () {
      this.dialogVisible = false
      this.getStudents()
    },
    getSchool () {
      this.$axios.get('/api/admin/institute/all')
        .then(response => {
          console.log(response.data)
          this.schoolOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getMajorOptions (school) {
      if (!school) {
        this.majorOptions = []
      }
      console.log(school)
      console.log('111')
      this.$axios.get('/api/admin/major/all-by-institute', {params: {institute: school}})
        .then(response => {
          console.log(response.data)
          console.log(response.data.data)
          console.log('return')
          this.majorOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
          this.majorOptions = []
        })
    },
    statusName: function (status) {
      if (status === 'Y') {
        return '在读'
      } else if (status === 'N') {
        return '休学'
      }
    },
    addRow () {
      this.dialogVisible = true
    },
    getStudents () {
      this.$axios.get('/api/admin/student/all')
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.studentTable = res.data.data
            // this.$message.success('获取成功')
          } else {
            this.$message.error('获取失败')
          }
        })
    },
    isValid (row, index) {
      var usernamePattern = /^[2][2][0-9]{4}$/
      var namePattern = /^[\u4E00-\u9FA5A-Za-z]+$/
      var identityPattern = /^[0-9]{18}|[0-9]{17}[xX]$/
      var telPattern = /^(1[0-9][0-9])\d{8}$/
      var emailPattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/

      if (this.studentTable[index].status === '') {
        this.$alert('状态不应为空')
        return false
      } else if (this.studentTable[index].institute === '') {
        this.$alert('学院不应为空')
        return false
      } else if (this.studentTable[index].major === '') {
        this.$alert('专业不应为空')
        return false
      } else if (!usernamePattern.test(this.studentTable[index].stunum)) {
        this.$alert('学号不应为空')
        return false
      } else if (!namePattern.test(this.studentTable[index].name)) {
        this.$alert('姓名应为汉字或字母')
        return false
      } else if (!identityPattern.test(this.studentTable[index].identity)) {
        this.$alert('身份证号应为18位数字')
        return false
      } else if ((this.studentTable[index].tel !== 'NA' && !telPattern.test(this.studentTable[index].tel))) {
        this.$alert('手机号应为11位1开头数字')
        return false
      } else if ((this.studentTable[index].email !== 'NA' && !emailPattern.test(this.studentTable[index].email))) {
        this.$alert('邮箱不符合正确格式')
        return false
      }
      return true
    },
    editRow (row, index) {
      console.log(row)
      console.log(index)
      console.log(this.studentTable)
      this.getSchool()
      this.getMajorOptions(row.institute)
      this.editingRow = Object.assign({}, this.studentTable[index])
      this.editingIndex = index
      this.studentEdit = true
      console.log('editing')
      console.log(this.editingRow)
    },
    deleteRow (row, index) {
      this.$axios.delete('/api/admin/student', {params: {studentId: row.id}})
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.studentTable.splice(index, 1)
            this.$message.success('删除成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      console.log('delete')
    },
    confirmRow (row, index) {
      if (!this.isValid(row, index)) {
        return
      }
      console.log('confirm')
      this.$axios.put('/api/admin/student', this.studentTable[index])
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.$message.success('修改成功')
            this.editingIndex = -1
            this.studentEdit = false
            this.getStudents()
          } else {
            this.$message.error(res.data.msg)
            console.log(this.editingRow)
            this.editingIndex = -1
            this.studentEdit = false
            this.getStudents()
          }
        })
    },
    cancelRow (row, index) {
      console.log(row)
      console.log(this.editingRow)
      this.studentTable[index] = Object.assign({}, this.editingRow)
      this.editingIndex = -1
      this.studentEdit = false
      console.log('cancel')
      console.log(row)
      this.getStudents()
    }
  },
  components: {'reg': Register}
}
</script>

<style scoped>

</style>
