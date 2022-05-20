<template>
  <el-card class="box-card">
    <el-table :data="lessonTimeArrange"
              style="width: 100%"
              stripe
              align="center"
              pager="page">
      <el-table-column
        prop="lessonNum"
        label="课表"
        width="200"
        style="text-align: center">
        <template v-slot="scope">
          <span>{{scope.row.num}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lessonTime"
        label="时间"
        width="400">
        <template v-slot="scope">
          <el-time-picker
            is-range
            format="HH:mm"
            value-format="HH:mm"
            v-model="editingLessonTime"
            range-separator="~"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围"
            v-show="(lessonTimeEdit&&(scope.$index===editingIndex))">
          </el-time-picker>
          <span v-show="!(lessonTimeEdit&&(scope.$index===editingIndex))">{{scope.row.starttime}}~{{scope.row.endtime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150"
        >
          <template v-slot="scope">
            <el-button v-if="!(lessonTimeEdit&&(scope.$index===editingIndex))" size="mini" type="warning" @click="editRow(scope.row,scope.$index)">修改</el-button>
            <el-button v-if="(lessonTimeEdit&&(scope.$index===editingIndex))" size="mini" type="success" @click="confirmRow(scope.row,scope.$index)">确定</el-button>
            <el-button v-if="(lessonTimeEdit&&(scope.$index===editingIndex))" size="mini" type="info" @click="cancelRow(scope.row,scope.$index)">取消</el-button>
          </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  name: 'lessonTimeArrange',
  data () {
    return {
      lessonTimeArrange: [],
      editingLessonTime: ['', ''],
      editingIndex: '',
      lessonTimeEdit: false
    }
  },
  methods: {
    getLessonTime () {
      this.$axios.get('/api/admin/time/')
        .then(res => {
          this.lessonTimeArrange = res.data.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    editRow (row, index) {
      this.editingIndex = index
      this.lessonTimeEdit = true
      this.editingLessonTime[0] = this.lessonTimeArrange[index].starttime
      this.editingLessonTime[1] = this.lessonTimeArrange[index].endtime
    },
    confirmRow (row, index) {
      if (index > 0 && this.lessonTimeArrange[index - 1].endtime !== '' && this.editingLessonTime[0] < this.lessonTimeArrange[index - 1].endtime) {
        this.$alert('上课时间不能早于前一节课的下课时间！')
        return
      } else if (index < 10 && this.lessonTimeArrange[index + 1].starttime !== '' && this.editingLessonTime[1] > this.lessonTimeArrange[index + 1].starttime) {
        this.$alert('下课时间不能早于后一节课的上课时间！')
        return
      }
      this.lessonTimeEdit = false
      console.log('confirm')
      // 发送
      this.lessonTimeArrange[index].starttime = this.editingLessonTime[0]
      this.lessonTimeArrange[index].endtime = this.editingLessonTime[1]
      this.$axios.put('/api/admin/time/', this.lessonTimeArrange[index]).then(res => {
        if (res.data.code === 0) {
          this.$alert('修改成功！')
        } else {
          this.$alert('修改失败！')
        }
      }).catch(err => {
        console.log(err)
      })
    },
    cancelRow (row, index) {
      this.editingIndex = -1
      this.lessonTimeEdit = false
    }
  }
}
</script>

<style>
table{
  text-align: center;
}
</style>
