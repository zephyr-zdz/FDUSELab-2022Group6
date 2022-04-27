<template>
  <el-card class="box-card">
    <el-dialog
      title="教师注册"
      top="5vh"
      width="50%"
      :append-to-body="true"
      :visible.sync="dialogVisible"
      :before-close="handleClose">
      <reg></reg>
    </el-dialog>
    <el-button :disabled="teacherEdit" type="success" size="small" @click="addRow()">增加</el-button>
    <el-table :data="teacherTable"
              style="width: 100%"
              stripe
              pager="page">
      <el-table-column label="状态" width="100">
        <template v-slot="scope">
          <el-select v-model="scope.row.status" v-show="(teacherEdit&&(scope.$index===editingIndex))" placeholder="请选择" size="mini">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{ statusName(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="学院"
        width="80">
        <template v-slot="scope">
          <el-select
            v-model="scope.row.institute"
            v-show="(teacherEdit&&(scope.$index===editingIndex))"
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
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.institute}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="专业"
        width="80">
        <template v-slot="scope">
          <el-select v-model="scope.row.major"
                     v-show="(teacherEdit&&(scope.$index===editingIndex))"
                     placeholder="请选择"
                     size="mini">
            <el-option
              v-for="item in majorOptions"
              :key="item.name"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.major}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="工号"
        width="100">
        <template v-slot="scope">
          <span>{{scope.row.jobnum}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="100">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.name"
            v-show="(teacherEdit&&(scope.$index===editingIndex))"
            placeholder="英文或汉字"
            size="small">
          </el-input>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="identity"
        label="身份证号"
        width="200">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.identity"
            v-show="(teacherEdit&&(scope.$index===editingIndex))"
            oninput="value=value.replace(/[^\dX]/g,'')"
            placeholder="满足身份证号的规则（18位）"
            maxlength="18">
          </el-input>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.identity}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="tel"
        label="手机"
        width="180">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.tel"
            v-show="(teacherEdit&&(scope.$index===editingIndex))"
            oninput="value=value.replace(/[^\d]/g,'')"
            placeholder="11位纯数字，1开头" maxlength="11">
          </el-input>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.tel}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱">
        <template v-slot="scope">
          <el-input
            v-model="scope.row.email"
            v-show="(teacherEdit&&(scope.$index===editingIndex))"
            placeholder="用户标识符+ @ + 域名"
            size="small">
          </el-input>
          <span v-show="!(teacherEdit&&(scope.$index===editingIndex))">{{scope.row.email}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
        <template v-slot="scope">
          <el-button v-if="!(teacherEdit&&(scope.$index===editingIndex))" size="mini" type="warning" @click="editRow(scope.row,scope.$index)">修改</el-button>
          <el-button v-if="!(teacherEdit&&(scope.$index===editingIndex))" size="mini" type="danger" @click="deleteRow(scope.row,scope.$index)">删除</el-button>
          <el-button v-if="(teacherEdit&&(scope.$index===editingIndex))" size="mini" type="success" @click="confirmRow(scope.row,scope.$index)">确定</el-button>
          <el-button v-if="(teacherEdit&&(scope.$index===editingIndex))" size="mini" type="info" @click="cancelRow(scope.row,scope.$index)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import Register from './register'
export default {
  name: 'teacherTable',
  data () {
    return {
      statusOptions: [
        {
          value: 'Y',
          label: '在职'
        }, {
          value: 'N',
          label: '离职/离休'
        }],
      majorOptions: [],
      schoolOptions: [],
      teacherTable: [],
      editingRow: {},
      editingIndex: '',
      teacherEdit: false,
      dialogVisible: false
    }
  },
  methods: {
    handleClose () {
      this.dialogVisible = false
      this.getTeachers()
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
      this.$axios.get('/api/admin/major/allbyinstitute', {params: {institute: school}})
        .then(response => {
          this.majorOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
          this.majorOptions = []
        })
    },
    statusName: function (status) {
      return this.statusOptions.find(item => item.value === status).label
    },
    addRow () {
      this.dialogVisible = true
    },
    getTeachers () {
      this.$axios.get('/api/admin/teacher/all')
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.teacherTable = res.data.data
            // this.$message.success('获取成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
    },
    isValid (row, index) {
      var usernamePattern = /^[2][2][0-9]{6}$/
      var namePattern = /^[\u4E00-\u9FA5A-Za-z]+$/
      var identityPattern = /^[0-9]{18}|[0-9]{17}[xX]$/
      var telPattern = /^(1[0-9][0-9])\d{8}$/
      var emailPattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/

      if (this.teacherTable[index].status === '') {
        this.$alert('状态不应为空')
        return false
      } else if (this.teacherTable[index].institute === '') {
        this.$alert('学院不应为空')
        return false
      } else if (this.teacherTable[index].major === '') {
        this.$alert('专业不应为空')
        return false
      } else if (!usernamePattern.test(this.teacherTable[index].jobnum)) {
        this.$alert('工号不应为空')
        return false
      } else if (!namePattern.test(this.teacherTable[index].name)) {
        this.$alert('姓名应为汉字或字母')
        return false
      } else if (!identityPattern.test(this.teacherTable[index].identity)) {
        this.$alert('身份证号应为18位数字')
        return false
      } else if ((this.teacherTable[index].tel !== 'NA' && !telPattern.test(this.teacherTable[index].tel))) {
        this.$alert('手机号应为11位1开头数字')
        return false
      } else if ((this.teacherTable[index].email !== 'NA' && !emailPattern.test(this.teacherTable[index].email))) {
        this.$alert('邮箱不符合正确格式')
        return false
      }
      return true
    },
    editRow (row, index) {
      console.log(row)
      console.log(index)
      console.log(this.teacherTable)
      this.getSchool()
      this.getMajorOptions(row.institute)
      this.editingRow = Object.assign({}, this.teacherTable[index])
      this.editingIndex = index
      this.teacherEdit = true
      console.log('editing')
      console.log(this.editingRow)
    },
    deleteRow (row, index) {
      this.$axios.delete('/api/admin/teacher', {params: {jobnum: row.jobnum}})
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.teacherTable.splice(index, 1)
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
      this.teacherEdit = false
      console.log('confirm')
      this.$axios.put('/api/admin/teacher', this.teacherTable[index])
        .then(res => {
          console.log(res.data)
          if (res.data.code === 0) {
            this.$message.success('修改成功')
            this.editingIndex = -1
            this.teacherEdit = false
          } else {
            this.$message.error(res.data.msg)
            console.log(this.editingRow)
            this.editingIndex = -1
            this.teacherEdit = false
            this.getTeachers()
          }
        })
    },
    cancelRow (row, index) {
      console.log(row)
      console.log(this.editingRow)
      this.teacherTable[index] = Object.assign({}, this.editingRow)
      this.editingIndex = -1
      this.teacherEdit = false
      console.log('cancel')
      console.log(row)
      this.getTeachers()
    }
  },
  components: {'reg': Register}
}
</script>

<style scoped>

</style>
