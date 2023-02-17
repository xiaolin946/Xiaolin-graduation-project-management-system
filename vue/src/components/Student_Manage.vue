<template>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button style="margin-right: 0.4%" type="primary" round @click="insert_user">添加新学生</el-button>
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <el-dialog
      v-model="centerDialogVisible"
      title="学生信息"
      width="30%"
      align-center
  >
    <el-form
        :model="statusData.formData"
        :rules="statusData.formrules"
        ref="ruleFormRef"
        :label-position="'right'"
        label-width="100px"
        style="max-width: 460px"
    >
      <el-form-item style="margin-bottom: 4vh; width: 40vh" label="身份" prop="status">
        <el-select v-model="statusData.formData.status" placeholder="选择身份">
          <el-option label="Student" value="Student"/>
        </el-select>
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
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="iou">确认</el-button>
      </span>
    </template>
  </el-dialog>

  <el-table :data="statusData.tableData" style="height:72%; width: 100%; margin-bottom: 5px">
    <el-table-column fixed prop="id" label="ID" width="60" />
    <el-table-column prop="status" label="身份" width="120" />
    <el-table-column prop="username" label="用户名" width="150" />
    <el-table-column prop="name" label="姓名" width="80" />
    <el-table-column prop="sex" label="性别" width="60" />
    <el-table-column prop="age" label="年龄" width="60" />
    <el-table-column prop="register_date" label="注册时间" width="400" />
    <el-table-column fixed="right" label="数据操作" width="160">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="update_user(scope.row)">更新数据</el-button>
        <el-popconfirm
            width="10"
            confirm-button-text="Yes"
            cancel-button-text="No"
            :hide-icon="true"
            title="确定删除该用户吗?"
            @confirm="delete_user(scope.row)"
            @cancel=""
        >
          <template #reference>
            <el-button link type="primary" size="small">删除数据</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>
  <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[1, 3, 5, 10]"
      :small="false"
      :disabled="false"
      :background="false"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />

</template>

<script setup>
import {getCurrentInstance, reactive, ref} from "vue";
import {Refresh} from "@element-plus/icons-vue";
import {ElNotification} from "element-plus";
import request from "../request.js";
const {proxy} = getCurrentInstance();


const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const statusData = reactive({
  tableData: [],
  formData: {},
  formrules: {
    status: [
      {required: true, message: '请选择身份', trigger: 'blur'}
    ],
    username: [
      {required: true, message: '请输入用户名', trigger: 'blur'},
      {min: 5, message: '用户名最少为5位', trigger: 'blur'}
    ],
    name: [
      {required: true, message: '请输入姓名', trigger: 'blur'},
      {min: 2, message: '姓名最少为2位', trigger: 'blur'}
    ],
    sex: [
      {required: true, message: '请选择性别', trigger: 'blur'}
    ],
    age: [
      {required: true, message: '请输入年龄', trigger: 'blur'}
    ]
  },
})

const fresh = () =>{
  request.get("/user/findPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      status: 'Student'
    }
  }).then(res => {
    statusData.tableData = res.data.data
    total.value = res.data.total
  })
}
fresh()

const centerDialogVisible = ref(false)
const insert_user = () => {
  centerDialogVisible.value = true
  statusData.formData = {}
}
const update_user = (row) => {
  centerDialogVisible.value = true
  statusData.formData = JSON.parse(JSON.stringify(row))
}
const iou = () => {
  centerDialogVisible.value = false
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      if (statusData.formData.id == null) {
        statusData.formData.password = "123456"
        request.post('/user/insert', statusData.formData).then(res => {
          if (res.code === "200" && res.data === true) {
            ElNotification({
              title: '添加用户成功',
              type: 'success',
              message: "已添加 " + statusData.formData.status + " 用户: " + statusData.formData.username
            })
            fresh()
          }else{
            ElNotification({
              title: '添加用户失败',
              type: 'error',
              message: "数据格式有误或服务器异常"
            })
          }
        })
      }else{
        request.put('/user/update',statusData.formData).then(res => {
          if(res.code === "200" && res.data === true){
            ElNotification({
              title: '更新用户数据成功',
              type: 'success',
              message: statusData.formData.status + " 用户: " + statusData.formData.username +" 数据已更新"
            })
            fresh()
          }else{
            ElNotification({
              title: '更新用户数据失败',
              type: 'error',
              message: "数据格式有误或服务器异常"
            })
          }
        })
      }
    }else{
      ElNotification({
        title: '操作数据失败',
        type: 'error',
        message: "数据格式有误或服务器异常"
      })
    }
  })
}
const refresh = () => {
  fresh()
  ElNotification({
    title: '刷新成功',
    type: 'success',
    message: "已刷新用户数据表"
  })
}
const delete_user = (row) => {
  request.delete('/user/delete', {
    params: {
      status: row.status,
      username: row.username
    }
  }).then(res => {
    if (res.code === "200" && res.data === true) {
      ElNotification({
        title: '删除用户成功',
        type: 'success',
        message: row.status + " 用户: " + row.username + " 数据已删除"
      })
      fresh()
    } else {
      ElNotification({
        title: '删除用户失败',
        type: 'error',
        message: "权限不足或服务器异常"
      })
    }
  })
}

const handleSizeChange = (val) => {
  pageSize.value = val
  fresh()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fresh()
}

</script>


<style>

</style>