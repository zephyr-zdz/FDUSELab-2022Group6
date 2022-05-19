<template>
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
        <span class="elements">{{ choseableMajor() }}</span>
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
        <span class="calendar">{{calendar()}}</span>
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
</template>

<script>
export default {
  name: 'lessonInfo',
  data () {
    return {
      course: {}
    }
  },
  mounted () {
    this.getMajor()
    this.getClassrooms()
  },
  methods: {
    type () {
      if (this.course.course.ispublic === 'Y') {
        return '通识课程'
      } else if (this.course.majorListOfCourse.length === 1) {
        return '专业课程'
      } else return '向部分专业课程'
    },
    choseableMajor () {
      if (this.course.ispublic === 'Y') {
        return '通识课程'
      } else {
        return this.course.majorListOfCourse
      }
    },
    calendar () {
      var time
      for (var i = 0; i < this.course.calendarList.length; i++) {
        time += this.course.calendarList.day
        time += ','
        time += this.course.calendarList.number
        time += ' '
      }
      return time
    }
  }
}
</script>

<style scoped>

</style>
