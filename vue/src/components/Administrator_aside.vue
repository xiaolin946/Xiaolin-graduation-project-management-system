<template>
  <el-scrollbar style="user-select:none;">
      <el-menu
        :default-openeds="['2', '3']"
        router
      >
        <el-menu-item index="/Administrator/main"><template #title><el-icon><ElementPlus /></el-icon>后台系统首页</template></el-menu-item>
        <el-sub-menu index="2">
          <template #title><el-icon><UserFilled /></el-icon>用户管理</template>
          <el-menu-item index="/Administrator/admin_manage">管理员管理</el-menu-item>
          <el-menu-item index="/Administrator/teacher_manage">教师管理</el-menu-item>
          <el-menu-item index="/Administrator/student_manage">学生管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title><el-icon><Histogram /></el-icon>课题情况</template>
          <el-menu-item index="/Administrator/admin_kt">课题信息</el-menu-item>
          <el-menu-item index="/Administrator/admin_dt">定题情况</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/Administrator/admin_news"><template #title><el-icon><BellFilled /></el-icon>消息管理</template></el-menu-item>
        <el-sub-menu index="5">
          <template #title><el-icon><Tools /></el-icon>设置</template>
          <el-menu-item index="#">待开发~</el-menu-item>
          <el-menu-item index="#">待开发~</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
</template>

<script setup>
import {Histogram,Tools, UserFilled, BellFilled, ElementPlus} from '@element-plus/icons-vue'



</script>

<style scoped>

</style>