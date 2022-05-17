<template>
  <div>
    <nav-menu></nav-menu>
    <h1>404 Not Found</h1>
    <h2>页面无法访问</h2>
    <el-button type="primary" onclick="window.location.href='/'" >
      返回主页
    </el-button>
  </div>
</template>

<script>
import NavMenu from '../common/NavMenu'
export default {
  name: '404',
  components: {NavMenu}
}
</script>

<style scoped>
</style>
