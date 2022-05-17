<template>
    <el-form class="lesson-info-container" :model="lessonInfoForm" :rules="rules" ref="lessonInfoForm">

      <div>
        <h2>开课学期</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.semester}}</span>
        <el-form-item prop="semester" v-if="lessonEdit === true" >
          <el-input placeholder="请输入开课学期" v-model="lessonInfoForm.semester" style="width: 40%">
          </el-input>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程名称</h2>
        <span class="elements">{{lessonInfo.name}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程编号</h2>
        <span class="elements">{{lessonInfo.coursenum}}</span>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程类型</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.type}}</span>
        <el-form-item prop="type" v-if="lessonEdit === true">
          <el-select placeholder="请选择类型" v-model="lessonInfoForm.type">
            <el-option
              v-for="item in typeOptions"
              :key="item.name"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>可选专业</h2>
        <span class="elements" v-if="lessonInfo.type === '通识课程'">通识课程</span>
        <span class="elements" v-if="lessonEdit === false && lessonInfo.type !== '通识课程'">{{lessonInfo.major}}</span>
        <el-form-item prop="major" v-if="lessonInfoForm.type === '2' ">
          <el-select placeholder="请选择专业" multiple v-model="lessonInfoForm.majorMulti">
            <el-option
              v-for="item in majorOptions"
              :key="item.name"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="major" v-if="lessonInfoForm.type === '3' ">
          <el-select placeholder="请选择专业" v-model="lessonInfoForm.majorSingle">
            <el-option
              v-for="item in majorOptions"
              :key="item.name"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>学时</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.coursehour}}</span>
        <el-form-item prop="coursehour" v-if="lessonEdit === true">
          <el-input-number v-model="lessonInfoForm.coursehour" :min="1"></el-input-number>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>学分</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.credit}}</span>
        <el-form-item prop="credit" v-if="lessonEdit === true">
          <el-input-number v-model="lessonInfoForm.credit" :min="0.5" :step="0.5"></el-input-number>
        </el-form-item>
      </div>
      <el-divider></el-divider>
      <div>
        <h2>任课教师</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.teacherName}}</span>
      </div>
      <el-divider></el-divider>
      <div>
        <h2>课程介绍</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.intro}}</span>
        <el-form-item prop="intro" v-if="lessonEdit === true">
          <el-input v-model="lessonInfoForm.intro"
                    placeholder="请输入课程名称"></el-input>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>上课时间</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.calendarList}}</span>
        <el-form-item prop="time" v-if="lessonEdit === true">
          <el-input v-model="lessonInfoForm.time" placeholder="请输入上课时间" style="width: 40%"></el-input>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>上课地点</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.classroomName}}</span>
        <el-form-item prop="place" v-if="lessonEdit === true">
          <el-select placeholder="请选择上课地点" v-model="lessonInfoForm.place">
            <el-option
              v-for="item in classroomOptions"
              :key="item.name"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-divider></el-divider>

      <div>
        <h2>课程容量</h2>
        <span class="elements" v-if="lessonEdit === false">{{lessonInfo.capacity}}</span>
        <el-form-item prop="capacity" v-if="lessonEdit === true">
          <el-input-number v-model="lessonInfoForm.capacity" :min="0"></el-input-number>
        </el-form-item>
      </div>
      <el-divider></el-divider>

    <el-button type="warning" v-if="lessonEdit === false" @click="changeLesson()">修改</el-button>
    <el-button type="success" v-if="lessonEdit === true" @click="confirmLesson()">确定</el-button>
      <el-button type="info" v-if="lessonEdit === true" @click="cancel()">取消</el-button>
    <el-button type="danger" v-if="lessonEdit === false" @click="deleteLesson()">删除</el-button>
    </el-form>
</template>

<script>
export default {
  name: 'lessonInfo',
  data () {
    return {
      majorOptions: [],
      classroomOptions: [],
      typeOptions: [{
        id: '1',
        name: '通识课程'
      }, {
        id: '2',
        name: '面向部分专业课程'
      }, {
        id: '3',
        name: '专业课程'
      }],
      lessonInfo: {
        semester: '2021-2022春',
        name: '有机化学',
        coursenum: 'CHEM110011.01',
        type: '通识课程',
        major: '化学系',
        coursehour: '4',
        credit: '3',
        teacherName: '孙兴文',
        intro: '有机不会qwertyuijhgfdsdghj',
        calendarList: '1,1',
        classroomName: 'H3101',
        capacity: '100'
      },
      lessonEdit: false,
      school: '',
      application: {},
      editingCalendar: '',
      rowSchedule: '',
      lessonInfoForm: {
        semester: '',
        name: '有机化学',
        coursenum: 'CHEM110011.01',
        type: '',
        majorSingle: '',
        majorMulti: [],
        coursehour: '4',
        credit: '3',
        teacherName: '孙兴文',
        intro: '有机不会qwertyuijhgfdsdghj',
        calendarList: '',
        classroomName: '',
        capacity: ''
      },
      rules: {
        semester: [
          {required: true, message: '请输入开课学期', trigger: 'blur'},
          {pattern: /[0-9]{4}[-][0-9]{4}[\u4e00-\u9fa5]$/, message: '请输入正确的开课学期，如：2021-2022春', trigger: 'blur'}
        ],
        type: [
          { required: true, message: '请输入课程类型', trigger: 'blur' }
        ],
        major: [
          { required: true, message: '请选择学院', trigger: 'blur' }
        ],
        coursehour: [
          { required: true, message: '请选择学时', trigger: 'blur' }
        ],
        credit: [
          { required: true, message: '请选择学分', trigger: 'blur' }
        ],
        intro: [
        ],
        time: [
          { required: true, message: '请输入上课时间', trigger: 'blur' }
        ],
        place: [
          { required: true, message: '请选择上课地点', trigger: 'blur' }
        ],
        capacity: [
          { required: true, message: '请输入最大可选人数', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getMajor()
    this.getClassrooms()
  },
  methods: {
    changeLesson () {
      this.lessonEdit = true
    },
    confirmLesson () {
      this.lessonEdit = false
      // TODO: 上传数据
    },
    deleteLesson () {
      // TODO： 删除
    },
    cancel () {
      this.lessonEdit = false
    },
    getMajor () {
      this.$axios.get('/api/admin/major/all')
        .then(response => {
          console.log(response.data)
          this.majorOptions = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getClassrooms () {
      this.$axios.get('/api/admin/Classroom/getOpenClassroom')
        .then(response => {
          if (response.data.code === 0) {
            console.log(response.data)
            this.classroomOptions = response.data.data
          } else {
            this.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>

</style>
