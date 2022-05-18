<template>
  <body id="post">
  <el-form  label-position="left" label-width="100px">
    <h3 class="reg_title">新增课程模板</h3>

    <el-form-item label="课程序号" prop="coursenum">
      <el-input v-model="templateForm.coursenum"></el-input>
    </el-form-item>

    <el-form-item label="课程名称" prop="name">
      <el-input v-model="templateForm.name"></el-input>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">提交</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
export default {
  name: 'addTemplate',
  data () {
    return {
      templateForm: [{
        coursenum: '',
        name: ''
      }]
    }
  },
  methods: {
    submit () {
      var param = {
        coursenum: this.templateForm.coursenum,
        name: this.templateForm.name
      }
      console.log(param)
      this.$axios.post('/api/admin/course-template', param).then(res => {
        if (res.data.code === 0) {
          this.$message({
            type: 'success',
            message: '添加成功'
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
