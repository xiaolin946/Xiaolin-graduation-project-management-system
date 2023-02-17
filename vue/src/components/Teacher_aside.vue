<template>
  <el-scrollbar style="user-select:none;">
    <el-menu
      :default-openeds="['4']"
      router
    >
      <el-menu-item index="/Teacher/main"><template #title><el-icon><ElementPlus /></el-icon>后台系统首页</template></el-menu-item>
      <el-menu-item index="/Teacher/teacher_kt"><template #title><el-icon><Histogram /></el-icon>课题管理</template></el-menu-item>
      <el-menu-item index="/Teacher/teacher_dt"><template #title><el-icon><Promotion /></el-icon>定题申请</template></el-menu-item>
      <el-sub-menu index="4">
        <template #title><el-icon><BellFilled /></el-icon>公告管理</template>
        <el-menu-item index="/Teacher/teacher_news">消息箱</el-menu-item>
        <el-menu-item index="/Teacher/teacher_gnews">消息管理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="5">
        <template #title><el-icon><Tools /></el-icon>设置</template>
        <el-menu-item index="5-1">待开发~</el-menu-item>
        <el-menu-item index="5-2">待开发~</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-scrollbar>
</template>

<script setup>
import {Histogram,Tools, Promotion, BellFilled, ElementPlus} from '@element-plus/icons-vue'

</script>

<style scoped>

</style>