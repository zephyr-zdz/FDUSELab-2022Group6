<template>
  <el-dialog
    title="课程详细信息"
    top="5vh"
    width="80%"
    :append-to-body="true"
    :visible.sync="dialogVisible"
    :before-close="handleClose">
    <el-form>
      <div>
        <h2>开课学期</h2>
        <span class="elements">{{course.course.semester}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程名称</h2>
        <span class="elements">{{course.courseTemplate.name}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程编号</h2>
        <span class="elements">{{course.courseTemplate.coursenum}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程类型</h2>
        <span class="elements">{{type()}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>可选专业</h2>
        <span class="elements">{{ choseableMajor(this.course.majorListOfCourse) }}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>学时</h2>
        <span class="elements">{{course.course.coursehour}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>学分</h2>
        <span>{{course.course.credit}}</span>
      </div>
      <el-divider></el-divider>
      <div>
        <h2>任课教师</h2>
        <span class="elements">{{course.teacher.name}}</span>
      </div>
      <el-divider></el-divider>
      <div>
        <h2>课程介绍</h2>
        <span>{{course.course.intro}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>上课时间</h2>
        <span class="calendar">{{calendar(this.course.calendarList)}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>上课地点</h2>
        <span class="classroom">{{course.classroom.name}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程容量</h2>
        <span class="elements">{{course.course.capacity}}</span>
      </div>
      <el-divider></el-divider>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  name: 'lessonInfo',
  data () {
    return {
      dialogVisible: false,
      course: {}
    }
  },
  methods: {
    handleClose () {
      this.$nextTick(() => {
        this.$emit('afterLessonInfo')
        this.dialogVisible = false
      })
    },
    type () {
      if (this.course.course.ispublic === 'Y') {
        return '通识课程'
      } else if (this.course.majorListOfCourse.length === 1) {
        return '专业课程'
      } else return '面向部分专业课程'
    },
    choseableMajor (majorListOfCourse) {
      if (this.course.course.ispublic === 'Y') {
        return '通识课程'
      } else {
        let majorList = ''
        majorListOfCourse.forEach(item => {
          majorList += item.name + ' '
        })
        return majorList
      }
    },
    calendar (calendarList) {
      var schedule = ''
      for (var i = 0; i < calendarList.length; i++) {
        schedule += (calendarList[i].day + ',' + calendarList[i].number)
        schedule += ' '
      }
      this.rowSchedule = schedule
      return schedule
    }
  }
}
</script>

<style scoped>

</style>
