<template>
  <el-form label-position="left" :model="applicationForm" :rules="rules" ref="applicationForm" label-width="100px">
    <el-form-item label="学号" prop="stunum">
      <el-input v-model="applicationForm.stuNum" disabled></el-input>
    </el-form-item>

    <el-form-item label="姓名" prop="name">
      <el-input v-model="applicationForm.name" disabled></el-input>
    </el-form-item>

    <el-form-item label="开课学期" prop="semester">
      <el-input v-model="applicationForm.semester" disabled></el-input>
    </el-form-item>

    <el-form-item label="课程编号" prop="lessonNum">
      <el-input v-model="applicationForm.lessonNum" disabled></el-input>
    </el-form-item>

    <el-form-item label="姓名" prop="lessonName">
      <el-input v-model="applicationForm.lessonName" disabled></el-input>
    </el-form-item>

    <el-form-item label="申请理由" prop="reason">
      <el-input v-model="applicationForm.reason" placeholder="请输入申请理由"></el-input>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">申请</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'studentLessonApplication',
  data () {
    const username = this.$store.getters.username
    return {
      applicationForm: {
        stuNum: username,
        name: '',
        semester: '',
        lessonNum: '',
        lessonName: '',
        reason: ''
      },
      rules: {
        reason: [
          {required: true, message: '请输入申请理由', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submit () {

    }
  }
}
</script>

<style scoped>

</style>
