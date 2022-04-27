<template>
  <div>
    <h1 class="time">现在的时间是 {{date}}</h1>
    <h2 class="status">选课已{{selectClassStatus}}</h2>
    <div style="text-align: center">
      <el-button v-if="selectClassStatus === '开放'" @click="close()" type="danger">关闭选课</el-button>
      <el-button v-if="selectClassStatus === '关闭'" @click="open()" type="success">开放选课</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'openOrCloseClassSelect',
  data: function () {
    return {
      date: new Date(),
      selectClassStatus: '开放'
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
    getStats () {
      // TODO: 从数据库读取是否开放选课 路径要写path
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
    open () {
      this.selectClassStatus = '开放'
      this.$axios.post('/api/admin/admin/open').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '开放选课成功',
            type: 'success'
          })
          this.getStats()
        } else {
          this.$message({
            message: res.data.msg,
            type: 'error'
          })
        }
      })
    },
    close () {
      this.selectClassStatus = '关闭'
      this.$axios.post('/api/admin/admin/close').then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '关闭选课成功',
            type: 'success'
          })
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
