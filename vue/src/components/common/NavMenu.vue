<template>
  <el-menu
    :default-active="this.$route.path"
    router
    mode="horizontal"
    background-color="white"
    text-color="#303133"
    active-text-color="#409EFF"
    style="width:100%;">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <!-- <a href="#nowhere" style="color: #222;float: right;padding: 20px;">更多功能</a> -->
    <!-- <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i> -->
    <span style="position: absolute;padding-top: 20px;right: 45%;font-size: 20px;font-weight: bold">复旦大学教务系统</span>
  </el-menu>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    var theNavList = [
      {name: '/home', navItem: '首页'}/* ,
      {name: '/404', navItem: '404'} */
    ]
    var theroleList = {
      admin: {name: '/admin', navItem: '管理员主页'},
      teacher: {name: '/user/teacher', navItem: '教师主页'},
      student: {name: '/user/student', navItem: '学生主页'}
    }
    if (this.$store.getters.role !== 'none') {
      theNavList.push(theroleList[this.$store.getters.role])
    } else {
      theNavList = [{name: '/home', navItem: '首页'}]
      theNavList.push({name: '/login', navItem: '登录'})
    }
    return {navList: theNavList}
  }
}
</script>

<style scoped>
  a{
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }
</style>
