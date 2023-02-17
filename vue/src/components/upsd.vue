<template>
  <h2 style="margin-left: 130px; margin-bottom: 20px">修改密码</h2>
  <el-form
      :model="statusData.formData"
      :rules="statusData.formrules"
      ref="ruleFormRef"
      :label-position="'right'"
      label-width="100px"
      style="max-width: 460px"
  >
    <el-form-item style="width: 40vh" label="旧密码" prop="password">
      <el-input v-model="statusData.formData.password"/>
    </el-form-item>
    <el-form-item style="width: 40vh" label="新密码" prop="new_password">
      <el-input type="password" v-model="statusData.formData.new_password" maxlength="18" show-password/>
    </el-form-item>
    <el-form-item style="width: 40vh" label="新密码" prop="new_password2">
      <el-input type="password" v-model="statusData.formData.new_password2" maxlength="18" show-password/>
    </el-form-item>
  </el-form>
  <el-button style="margin-left: 130px" type="primary" @click="uppsd">修改密码</el-button>
</template>

<script setup>
import {getCurrentInstance, reactive} from "vue";
import {ElNotification} from "element-plus";
import request from "../request.js";
import router from "../router/index.js";
const {proxy} = getCurrentInstance();

const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
const statusData = reactive({
  password: '',
  formData: {
    status: user.status,
    username: user.username,
  },
  formrules: {
    password: [
      {required: true, message: '请输入旧密码', trigger: 'blur'},
      {min: 5, message: '密码最少为5位', trigger: 'blur'}
    ],
    new_password: [
      {required: true, message: '请输入新密码', trigger: 'blur'},
      {min: 5, message: '密码最少为5位', trigger: 'blur'}
    ],
    new_password2: [
      {required: true, message: '再次输入新密码', trigger: 'blur'},
      {min: 5, message: '密码最少为5位', trigger: 'blur'}
    ],
  },
})

request.get("/user/mypsd", {
  params: {
    status: user.status,
    username: user.username
  }
}).then(res => {
  statusData.password = res.data
})


const uppsd = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid && statusData.formData.password === statusData.password && statusData.formData.new_password === statusData.formData.new_password2) {
      request.put('/user/uppsd', statusData.formData).then(res => {
        if (res.code === "200" && res.data === true) {
          ElNotification({
            title: '修改密码成功',
            type: 'success',
            message: "密码已更新"
          })
          localStorage.removeItem("user")
          router.push("/Login")
        } else {
          ElNotification({
            title: '修改密码失败',
            type: 'error',
            message: "密码格式有误或旧密码错误"
          })
        }
      })
    } else {
      ElNotification({
        title: '修改密码失败',
        type: 'error',
        message: "两次新密码不一致"
      })
    }
  })
}
</script>

<style>

</style>