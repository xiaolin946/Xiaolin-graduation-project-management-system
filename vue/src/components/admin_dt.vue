<template>
  <h1 style="text-align: left; margin-top: 20px; margin-bottom: 20px; font-size: 20px">已定题学生名单</h1>
  <el-table :data="statusData.tableData" style="height:43%; width: 100%; margin-bottom: 5px;">
    <el-table-column fixed prop="stuuname" label="学生学号" width="150" />
    <el-table-column prop="stuname" label="学生姓名" width="150" />
    <el-table-column prop="tid" label="课题编号" width="150" />
    <el-table-column prop="tname" label="课题名称" width="150" />
    <el-table-column prop="tt" label="题目名称" width="150" />
  </el-table>
  <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[1, 2]"
      :small="false"
      :disabled="false"
      :background="false"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />
  <h1 style="text-align: left; margin-top: 20px; margin-bottom: 20px; font-size: 20px">未定题学生名单</h1>
  <el-table :data="statusData.tableData2" style="height:43%; width: 40%; margin-bottom: 5px">
    <el-table-column fixed prop="username" label="学生学号" width="180" />
    <el-table-column prop="name" label="学生姓名" width="150" />

  </el-table>
  <el-pagination
      v-model:current-page="currentPage2"
      v-model:page-size="pageSize2"
      :page-sizes="[1, 2]"
      :small="false"
      :disabled="false"
      :background="false"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total2"
      @size-change="handleSizeChange2"
      @current-change="handleCurrentChange2"
  />
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "../request.js";
import {ElNotification} from "element-plus";
import {Refresh} from "@element-plus/icons-vue";

const currentPage = ref(1)
const pageSize = ref(5)
const total = ref(0)
const currentPage2 = ref(1)
const pageSize2 = ref(5)
const total2 = ref(0)

const statusData = reactive({
  tableData: [],
  tableData2: [],
})

const fresh = () => {
  request.get("/Dt/dt_findPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
    }
  }).then(res => {
    statusData.tableData = res.data.data
    total.value = res.data.total
  })
  request.get("/Dt/mdt_findPage", {
    params: {
      currentPage: currentPage2.value,
      pageSize: pageSize2.value,
    }
  }).then(res => {
    statusData.tableData2 = res.data.data
    total2.value = res.data.total
  })
}
fresh()

const handleSizeChange = (val) => {
  pageSize.value = val
  fresh()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fresh()
}
const handleSizeChange2 = (val) => {
  pageSize2.value = val
  fresh()
}
const handleCurrentChange2 = (val) => {
  currentPage2.value = val
  fresh()
}

</script>

<style>

</style>