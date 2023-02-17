<template>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <el-table :data="statusData.tableData" style="height:80%; width: 100%; margin-bottom: 5px">
    <el-table-column fixed prop="tid" label="课题ID" width="70" />
    <el-table-column prop="teaname" label="教师用户名" width="120" />
    <el-table-column prop="tname" label="课题名称" width="150" />
    <el-table-column prop="tt" label="题目名称" width="180" />
    <el-table-column  label="题目内容" width="600" >
      <template #default="scope">
        <el-popover
            placement="bottom"
            title="题目内容"
            :width="500"
            trigger="click"
            :content="scope.row.ttc"
        >
          <template #reference>
            <el-button>查看课题内容</el-button>
          </template>
        </el-popover>
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
import {reactive, ref} from "vue";
import request from "../request.js";
import {ElNotification} from "element-plus";
import {Refresh} from "@element-plus/icons-vue";

const currentPage = ref(1)
const pageSize = ref(2)
const total = ref(0)

const statusData = reactive({
  tableData: [],

})

const fresh = () =>{
  request.get("/Kt/kt_findPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      status: 'kt'
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