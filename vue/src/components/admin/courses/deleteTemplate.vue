<template>
  <body id="post">
  <el-form  label-position="left" label-width="100px">
    <h3 class="reg_title">删除课程模板</h3>

    <el-form-item label="课程模板" prop="template">
      <el-select placeholder="请选择课程模板" v-model="deleteTemplate.name">
        <el-option
          v-for="item in templateOptions"
          :key="item.name"
          :label="item.name"
          :value="item.name">
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
  name: 'deleteTemplate',
  data () {
    return {
      templateOptions: [],
      deleteTemplate: {
        name: ''
      }
    }
  },
  mounted () {
    this.getTemplate()
    console.log(this.templateOptions)
    console.log('true')
  },
  methods: {
    getTemplate () {
      this.$axios.get('/api/admin/course-template').then(res => {
        this.schoolOptions = res.data.data
      })
    },
    confirmDelete () {
      this.$axios.delete('/api/admin/course-template', {params: {name: this.deleteTemplate.name}})
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.getTemplate()
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
