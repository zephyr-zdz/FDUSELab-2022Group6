<template>
<div>
    <el-upload style="text-align: center;margin-top: 100px"
      class="csvInput"
      drag
      action="/api/admin/teacher-course/file"
      accept=".csv"
      :on-success="handleResponse">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将课程信息文件拖到此处，或<em>点击选择文件</em></div>
      <div class="el-upload__tip" slot="tip">只能上传csv文件</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: 'csvInputLesson',
  methods: {
    handleResponse (res) {
      console.log(res)
      if (res.data === '失败序号: ') {
        this.$message({
          message: '批量上传成功！',
          type: 'success'
        })
      } else {
        this.$message({
          message: '批量上传未完全成功！' + res.data,
          type: 'error'
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
