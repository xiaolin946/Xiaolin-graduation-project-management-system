<template>
  <h2 style="margin-left: 130px; margin-bottom: 20px">个人信息</h2>
  <el-form
      :model="statusData.formData"
      :rules="statusData.formrules"
      ref="ruleFormRef"
      :label-position="'right'"
      label-width="100px"
      style="max-width: 460px"
  >
    <el-form-item style="width: 40vh" label="身份" prop="status">
      <el-input v-model="statusData.formData.status" disabled/>
    </el-form-item>
    <el-form-item style="width: 40vh" label="用户名" prop="username">
      <el-input v-model="statusData.formData.username" maxlength="15"/>
    </el-form-item>
    <el-form-item style="width: 40vh" label="姓名" prop="name">
      <el-input v-model="statusData.formData.name" maxlength="15"/>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-radio-group v-model="statusData.formData.sex">
        <el-radio label="男" />
        <el-radio label="女" />
        <el-radio label="保密" />
      </el-radio-group>
    </el-form-item>
    <el-form-item style="width: 40vh" label="年龄" prop="age">
      <el-input v-model="statusData.formData.age" maxlength="3"/>
    </el-form-item>
  </el-form>
  <el-button style="margin-left: 130px" type="primary" @click="update">更新信息</el-button>
</template>

<script setup>
import {getCurrentInstance, reactive} from "vue";
import {ElNotification} from "element-plus";
import request from "../request.js";
const {proxy} = getCurrentInstance();

const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
const statusData = reactive({
  formData: {},
  formrules: {
    username: [
      {required: true, message: '请输入用户名', trigger: 'blur'},
      {min: 5, message: '用户名最少为5位', trigger: 'blur'}
    ],
    name: [
      {required: true, message: '请输入姓名', trigger: 'blur'},
      {min: 2, message: '姓名最少为2位', trigger: 'blur'}
    ],
    sex: [
      {required: true, message: '请选择性别', trigger: 'blur'},
    ],
    age: [
      {required: true, message: '请输入年龄', trigger: 'blur'},
    ],
  },
})

const fresh = () =>{
  request.get("/user/myxx", {
    params: {
      status: user.status,
      username: user.username
    }
  }).then(res => {
    statusData.formData = res.data
  })
}
fresh()

const update = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      request.put('/user/update', statusData.formData).then(res => {
        if (res.code === "200" && res.data === true) {
          ElNotification({
            title: '更新用户数据成功',
            type: 'success',
            message: "个人数据已更新"
          })
          fresh()
        } else {
          ElNotification({
            title: '更新用户数据失败',
            type: 'error',
            message: "数据格式有误或服务器异常"
          })
        }
      })
    } else {
      ElNotification({
        title: '操作数据失败',
        type: 'error',
        message: "数据格式有误"
      })
    }
  })
}
</script>

<style>

</style>