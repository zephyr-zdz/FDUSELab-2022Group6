<template>
  <body id="post">
    <el-form  label-position="left" ref="regForm" label-width="100px">
      <h3 class="reg_title">新增专业</h3>

      <el-form-item label="学院" prop="school">
        <el-select placeholder="请选择学院" v-model="majorForm.institute">
          <el-option
            v-for="item in schoolOptions"
            :key="item.name"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="专业名称" prop="majorName">
        <el-input v-model="majorForm.major"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'addMajor',
  data () {
    return {
      majorForm: [{
        institute: '',
        major: ''
      }],
      schoolOptions: []
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
    submit () {
      var param = {
        institute: this.majorForm.institute,
        name: this.majorForm.major
      }
      console.log(param)
      this.$axios.post('/api/admin/major', param).then(res => {
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
