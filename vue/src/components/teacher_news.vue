<template>
  <h3 style="margin-left: 40%; margin-bottom: 10px">消息箱</h3>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <el-table :data="statusData.tableData" style="height:72%; width: 100%; margin-bottom: 5px">
    <el-table-column fixed prop="xid" label="ID" width="60" />
    <el-table-column prop="xstatus" label="消息类别" width="120" />
    <el-table-column prop="puser" label="发送用户" width="120" />
    <el-table-column prop="guser" label="接收用户" width="120" />
    <el-table-column prop="title" label="标题" width="180" show-overflow-tooltip/>
    <el-table-column prop="content" label="消息内容" width="200" show-overflow-tooltip/>
    <el-table-column prop="pdate" label="消息发送时间" width="200"/>
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
import {reactive, ref} from "vue";
import {Refresh} from "@element-plus/icons-vue";
import {ElNotification} from "element-plus";
import request from "../request.js";

const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const statusData = reactive({
  tableData: [],
})

const fresh = () =>{
  request.get("/News/snews_findPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      status: user.status
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
    message: "已刷新消息列表"
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