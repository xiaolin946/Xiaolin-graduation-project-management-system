<template>
  <el-scrollbar style="user-select:none;">
    <el-menu router>
      <el-menu-item index="/Student/main"><template #title><el-icon><ElementPlus /></el-icon>后台系统首页</template></el-menu-item>
      <el-menu-item index="/Student/student_dt"><template #title><el-icon><Promotion /></el-icon>定题管理</template></el-menu-item>
      <el-menu-item index="/Student/student_news"><template #title><el-icon><BellFilled /></el-icon>消息箱</template></el-menu-item>
      <el-menu-item index="/Student/student_upload"><template #title><el-icon><UploadFilled /></el-icon>上传论文</template></el-menu-item>
      <el-sub-menu index="5">
        <template #title><el-icon><Tools /></el-icon>设置</template>
        <el-menu-item index="5-1">待开发~</el-menu-item>
        <el-menu-item index="5-2">待开发~</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-scrollbar>
</template>

<script setup>
import {Promotion,Tools, UploadFilled, BellFilled, ElementPlus} from '@element-plus/icons-vue'


</script>

<style scoped>

</style>