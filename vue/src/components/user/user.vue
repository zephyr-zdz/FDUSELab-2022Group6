<template>
  <div>
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      role: this.$store.getters.role
    }
  },
  mounted () {
    console.log('mounted' + this.role)
    this.$router.replace({name: this.role})
  }
}
</script>
