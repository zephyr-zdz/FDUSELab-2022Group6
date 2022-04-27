<template>
  <body id="poster">
    <el-container class="main">
      <el-main>
        <h2>欢迎使用教务管理系统！</h2>
        <br>
        <el-button type="primary" onclick="window.location.href='/login/adminLogin'" >
            管理员登录
        </el-button>
        <el-button type="primary" onclick="window.location.href='/login/commonLogin'" >
            教师/学生登录
        </el-button>
        <br>
      </el-main>
    </el-container>
  </body>
</template>

<script>
export default {
  name: 'login'
}
</script>

<style scoped>
.main{
 text-align: center;
}
#poster {
    height: 100%;
    width: 100%;
    position: fixed;
    margin: 0;
    background-color: #ffffff00;
    overflow: hidden;
  }
  body{
    margin: 0px;
    /* background: url("~@/assets/background.png"); */
    /*background-position: top;*/
    background-size: auto;
    background-color: #ffffff;
  }
</style>
