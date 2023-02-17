<template>
  <div>
    <div style="margin-top: 16px;">
      <el-dropdown>
              <span>
                <span v-text="user.username"></span>
                <el-icon style="margin-left: 8px;" size="15"><Setting/></el-icon>
              </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="myxx">个人信息</el-dropdown-item>
            <el-dropdown-item @click="upsd">修改密码</el-dropdown-item>
            <el-dropdown-item divided @click="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import {Setting} from '@element-plus/icons-vue'
import router from "../router/index.js";

const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}

const exit = () => {
  localStorage.removeItem("user")
  router.push("/Login")
}
const myxx = () => {
  router.push("/" + user.status + "/myxx")
}
const upsd = () => {
  router.push("/" + user.status + "/upsd")
}
</script>

<style scoped>

</style>