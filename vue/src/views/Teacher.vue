<template>
  <el-container class="layout-container" style="height: 100vh; width: 100%; ">
    <el-container>
      <el-aside style="height: 100%; width: 20%; ">
        <Teacher_aside />
      </el-aside>
      <el-container>
        <el-header style="height: 8%; width: 100%; text-align: right;">
          <Header />
        </el-header>
        <el-main style="height: 100%; width: 100%;">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </el-container>

</template>

<script setup>
import Teacher_aside from '/src/components/Teacher_aside.vue'
import Header from '/src/components/Header.vue'



</script>

<style scoped>

</style>
