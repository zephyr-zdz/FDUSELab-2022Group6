<template>
  <el-dialog
  title="选课申请"
  top="5vh"
  width="50%"
  :append-to-body="true"
  :visible.sync="dialogVisible"
  :before-close="handleClose">
    <el-form label-position="left" :model="applicationForm" :rules="rules" ref="applicationForm" label-width="100px">
      <el-form-item label="学号" prop="stunum">
        <el-input v-model="applicationForm.stuNum" disabled></el-input>
      </el-form-item>

      <el-form-item label="开课学期" prop="semester">
        <el-input v-model="applicationForm.semester" disabled></el-input>
      </el-form-item>

      <el-form-item label="课程编号" prop="lessonNum">
        <el-input v-model="applicationForm.lessonNum" disabled></el-input>
      </el-form-item>

      <el-form-item label="课程名称" prop="lessonName">
        <el-input v-model="applicationForm.lessonName" disabled></el-input>
      </el-form-item>

      <el-form-item label="申请理由" prop="reason">
        <el-input type="textarea" v-model="applicationForm.reason" placeholder="请输入申请理由"></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="submit">申请</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  name: 'studentLessonApplication',
  data () {
    const username = this.$store.getters.username
    return {
      course: {},
      id: 0,
      dialogVisible: false,
      applicationForm: {
        stuNum: username,
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
    handleClose () {
      this.dialogVisible = false
      this.$nextTick(() => {
        this.$emit('afterApplication')
        this.dialogVisible = false
      })
    },
    course2Form () {
      this.applicationForm.lessonName = this.course.courseTemplate.name
      this.applicationForm.semester = this.course.course.semester
      this.applicationForm.lessonNum = this.course.courseTemplate.coursenum
    },
    submit () {
      this.$axios.post('/api/student/application/submit',
        {
          studentid: this.id,
          courseid: this.course.course.id,
          explanation: this.applicationForm.reason,
          result: 'pending'
        }
      )
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: res.data.data,
              type: 'success'
            })
            this.getLessons()
          } else {
            this.$message({
              message: res.data.data,
              type: 'error'
            })
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
