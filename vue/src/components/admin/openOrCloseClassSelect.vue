<template>
  <div>
    <h1 class="time">现在的时间是 {{date}}</h1>
    <h2 class="status" >{{checkStatus()}}</h2>
    <h2 class="time">现在的学期是 {{semester}}</h2>
    <div style="text-align: center">
      <el-button v-if="semesterState === 'off'" @click="beginSemester()" type="success">开始学期</el-button>
      <el-button v-if="firstStatus === 'on'" @click="firstClose()" type="danger">关闭一轮选课</el-button>
      <el-button v-if="firstStatus === 'off'" @click="firstOpen()" type="success">开放一轮选课</el-button>
      <el-button v-if="secondStatus === 'on'" @click="secondClose()" type="danger">关闭二轮选课</el-button>
      <el-button v-if="secondStatus === 'off'" @click="secondOpen()" type="success">开放二轮选课</el-button>
      <el-button v-if="semesterState === 'on'" @click="endSemester()" type="danger">结束学期</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'openOrCloseClassSelect',
  data: function () {
    return {
      date: new Date(),
      semester: '2021-2022春',
      semesterState: 'off',
      firstStatus: 'off',
      secondStatus: 'off'
    }
  },
  created () {
  },
  mounted () {
    this.getStats()
    let that = this
    this.timer = setInterval(function () {
      that.date = new Date().toLocaleString()
    })
  },
  beforeDestroy: function () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  methods: {
    checkStatus () {
      if (this.firstStatus === 'off') {
        if (this.secondStatus === 'off') {
          return '选课已关闭'
        } else return '二轮选课已开启'
      } else return '一轮选课已开启'
    },
    getStats () {
      this.$axios.get('/api/admin/admin/valid').then(res => {
        if (res.data.code === 0) {
          this.$store.commit('setRound', res.data.data)
          if (res.data.data === 'first') {
            this.firstStatus = 'on'
            this.secondStatus = 'off'
          } else if (res.data.data === 'second') {
            this.secondStatus = 'on'
            this.firstStatus = 'off'
          } else {
            this.firstStatus = 'off'
            this.secondStatus = 'off'
          }
        }
      })
      this.$axios.get('/api/admin/admin/semester/state').then(res => {
        if (res.data.code === 0) {
          if (res.data.data === 'on') {
            this.semesterState = 'on'
          } else {
            this.semesterState = 'off'
          }
        }
      })
      this.$axios.get('/api/admin/admin/semester').then(res => {
        if (res.data.code === 0) {
          this.semester = res.data.data
          this.$store.commit('setSemester', res.data.data)
        }
      })
    },
    firstOpen () {
      if (this.secondStatus === 'on') {
        this.$alert('请先关闭第二轮选课')
      } else {
        this.$axios.post('/api/admin/admin/open/first').then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: '开放一轮选课成功',
              type: 'success'
            })
            this.firstStatus = 'on'
            this.$store.commit('setRound', 'first')
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
        })
      }
    },
    firstClose () {
      this.$axios.post('/api/admin/admin/close/first').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '关闭一轮选课成功',
            type: 'success'
          })
          this.firstStatus = 'off'
          this.$store.commit('setRound', 'off')
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          })
        }
      })
    },
    secondOpen () {
      if (this.firstStatus === 'on') {
        this.$alert('请先关闭第一轮选课')
      } else {
        this.$axios.post('/api/admin/admin/open/second').then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: '开放二轮选课成功',
              type: 'success'
            })
            this.secondStatus = 'on'
            this.$store.commit('setRound', 'second')
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
        })
      }
    },
    secondClose () {
      this.$axios.post('/api/admin/admin/close/second').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '关闭一轮选课成功',
            type: 'success'
          })
          this.secondStatus = 'off'
          this.$store.commit('setRound', 'off')
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          })
        }
      })
    },
    beginSemester () {
      this.$axios.post('/api/admin/admin/semester/begin').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '开放选课成功',
            type: 'success'
          })
          this.semesterState = 'on'
          this.getStats()
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          })
        }
      })
    },
    endSemester () {
      this.$axios.post('/api/admin/admin/semester/end').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '关闭选课成功',
            type: 'success'
          })
          this.semesterState = 'off'
          this.getStats()
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
  font-size: 50px;
}
h2 {
  text-align: center;
  font-size: 30px;
}
</style>
