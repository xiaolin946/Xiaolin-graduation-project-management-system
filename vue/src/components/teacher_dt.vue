<template>
  <h3 style="margin-left: 40%; margin-bottom: 10px">课题申请</h3>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <el-table :data="statusData.tableData" style="height:80%; width: 100%; margin-bottom: 5px">
    <el-table-column fixed prop="id" label="申请ID" width="70" />
    <el-table-column prop="stuuname" label="学生学号" width="130" />
    <el-table-column prop="stuname" label="学生姓名" width="120" />
    <el-table-column prop="sex" label="学生性别" width="80" />
    <el-table-column prop="age" label="学生年龄" width="80" />
    <el-table-column prop="tid" label="课题编号" width="80" />
    <el-table-column prop="tname" label="课题名称" width="150" />
    <el-table-column prop="ps" label="申请状态" width="120" />
    <el-table-column fixed="right" label="数据操作" width="180">
      <template #default="scope">
        <el-popconfirm
            width="10"
            confirm-button-text="Yes"
            cancel-button-text="No"
            :hide-icon="true"
            title="确定同意该学生的申请吗?"
            @confirm="dt_t(scope.row)"
            @cancel=""
        >
          <template #reference>
            <el-button link type="primary" size="small">同意申请</el-button>
          </template>
        </el-popconfirm>
        <el-popconfirm
            width="10"
            confirm-button-text="Yes"
            cancel-button-text="No"
            :hide-icon="true"
            title="确定拒绝该学生的申请吗?"
            @confirm="dt_f(scope.row)"
            @cancel=""
        >
          <template #reference>
            <el-button link type="primary" size="small">拒绝申请</el-button>
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
const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}

const statusData = reactive({
  tableData: [],
})

const fresh = () =>{
  request.get("/Dt/dt_tfindPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      teaname: user.username
    }
  }).then(res => {
    statusData.tableData = res.data.data
    total.value = res.data.total
  })
}
fresh()
const refresh = () => {
  fresh()
  ElNotification({
    title: '刷新成功',
    type: 'success',
    message: "已刷新课题数据表"
  })
}

const dt_t = (row) => {
  request.get("/Dt/sdt_ps",{
    params: {
      stuuname: row.stuuname
    }
  }).then(res => {
    if(res.data===0){
      request.put("/Dt/dt_t", row).then(res => {
        if(res.code === "200" && res.data === true){
          ElNotification({
            title: '课题申请通过成功',
            type: 'success',
            message: "已通过申请"
          })
          fresh()
        }else{
          ElNotification({
            title: '课题申请通过失败',
            type: 'error',
            message: "服务器异常"
          })
        }
      })
    }else{
      ElNotification({
        title: '课题申请通过失败',
        type: 'error',
        message: "该学生定题已完成，请刷新列表"
      })
    }
  })
}
const dt_f = (row) => {
  request.get("/Dt/sdt_ps",{
    params: {
      stuuname: row.stuuname
    }
  }).then(res => {
    if (res.data === 0) {
      request.put("/Dt/dt_f", row).then(res => {
        if(res.code === "200" && res.data === true){
          ElNotification({
            title: '课题申请拒绝成功',
            type: 'success',
            message: "已拒绝申请"
          })
          fresh()
        }else{
          ElNotification({
            title: '课题申请拒绝失败',
            type: 'error',
            message: "服务器异常"
          })
        }
      })
    }else{
      ElNotification({
        title: '课题申请拒绝失败',
        type: 'error',
        message: "该学生定题已完成，请刷新列表"
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