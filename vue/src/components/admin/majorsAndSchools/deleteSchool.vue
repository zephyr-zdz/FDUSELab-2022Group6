<template>
  <body id="post">
  <el-form  label-position="left" ref="regForm" label-width="100px">
    <h3 class="reg_title">删除学院</h3>

    <el-form-item label="学院" prop="school">
      <el-select placeholder="请选择学院" v-model="deleteSchool.id">
        <el-option
          v-for="item in schoolOptions"
          :key="item.name"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="danger" style="width: 100%;background: #505458;border: none" @click="confirmDelete">删除</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
export default {
  name: 'deleteSchool',
  data () {
    return {
      schoolOptions: [],
      deleteSchool: {
        id: ''
      }
    }
  },
  mounted () {
    this.getSchoolOptions()
    console.log(this.schoolOptions)
    console.log('true')
  },
  methods: {
    getSchoolOptions () {
      this.$axios.get('/api/admin/institute/all').then(res => {
        this.schoolOptions = res.data.data
      })
    },
    confirmDelete () {
      this.$axios.delete('/api/admin/institute', {params: {instituteid: this.deleteSchool.id}})
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.getSchoolOptions()
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
