<template>
  <body id="post">
    <el-form  label-position="left" ref="regForm" label-width="100px">
      <h3 class="reg_title">新增学院</h3>

      <el-form-item label="学院名称" prop="schoolName">
        <el-input v-model="schoolForm.name"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'addSchool',
  data () {
    return {
      schoolForm: {
        name: ''
      }
    }
  },
  methods: {
    submit () {
      this.$axios.post('/api/admin/institute', this.schoolForm).then(res => {
        if (res.data.code === 0) {
          this.$message({
            type: 'success',
            message: '添加成功'
          })
          this.schoolForm.name = ''
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
          this.schoolForm.name = ''
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
