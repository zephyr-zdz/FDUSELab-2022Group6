<template>
  <div>
    <h1 class="time">现在的时间是 {{date}}</h1>
    <h2 class="status" v-if="firstStatus === 'off' && secondStatus === 'off'">选课已关闭</h2>
    <h2 class="status" v-if="firstStatus === 'on'">一轮选课已开启</h2>
    <h2 class="status" v-if="secondStatus === 'on'">二轮选课已开启</h2>
    <div style="text-align: center">
      <el-button v-if="firstStatus === 'on'" @click="firstClose()" type="danger">关闭一轮选课</el-button>
      <el-button v-if="firstStatus === 'off'" @click="firstOpen()" type="success">开放一轮选课</el-button>
      <el-button v-if="secondStatus === 'on'" @click="secondClose()" type="danger">关闭二轮选课</el-button>
      <el-button v-if="secondStatus === 'off'" @click="secondOpen()" type="success">开放二轮选课</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'openOrCloseClassSelect',
  data: function () {
    return {
      date: new Date(),
      firstStatus: 'on',
      secondStatus: 'off'
    }
  },
  created () {
  },
  mounted () {
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
      if (this.firstStatus === '一轮选课关闭') {
        if (this.secondStatus === '二轮选课关闭') {
          return '选课已关闭'
        } else return '二轮选课已开启'
      } else return '一轮选课已开启'
    },
    getStats () {
      // TODO: 改为两轮选课。
      this.$axios.get('/api/admin/admin/isValid').then(res => {
        if (res.data.code === 0) {
          if (res.data.data === 'on') {
            this.selectClassStatus = '开放'
          } else {
            this.selectClassStatus = '关闭'
          }
        }
      })
    },
    firstOpen () {
      this.firstStatus = 'on'
      this.secondStatus = 'off'
    },
    firstClose () {
      this.firstStatus = 'off'
    },
    secondOpen () {
      this.secondStatus = 'on'
      this.firstStatus = 'off'
    },
    secondClose () {
      this.secondStatus = 'off'
    }
    // open () {
    //   this.selectClassStatus = '开放'
    //   this.$axios.post('/api/admin/admin/open').then(res => {
    //     if (res.data.code === 0) {
    //       this.$message({
    //         message: '开放选课成功',
    //         type: 'success'
    //       })
    //       this.getStats()
    //     } else {
    //       this.$message({
    //         message: res.data.msg,
    //         type: 'error'
    //       })
    //     }
    //   })
    // },
    // close () {
    //   this.selectClassStatus = '关闭'
    //   this.$axios.post('/api/admin/admin/close').then(res => {
    //     if (res.data.code === 0) {
    //       this.$message({
    //         message: '关闭选课成功',
    //         type: 'success'
    //       })
    //       this.getStats()
    //     } else {
    //       this.$message({
    //         message: res.data.msg,
    //         type: 'error'
    //       })
    //     }
    //   })
    // }
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
