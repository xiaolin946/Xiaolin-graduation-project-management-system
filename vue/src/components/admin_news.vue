<template>
    <div style="text-align: right; margin-bottom: 5px">
      <el-button style="margin-right: 0.4%" type="primary" round @click="insert_news">发送新消息</el-button>
      <el-button :icon="Refresh" circle @click="refresh"/>
    </div>
    <el-dialog
        v-model="centerDialogVisible"
        title="消息内容"
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
        <el-form-item style="margin-bottom: 4vh; width: 40vh" label="选择类型" prop="xstatus">
          <el-select v-model="statusData.formData.xstatus" placeholder="消息类型">
            <el-option label="公告" value="公告"/>
            <el-option label="通知" value="通知"/>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 40vh" label="发送用户" prop="puser">
          <el-input :model-value="user.username" disabled/>
        </el-form-item>
        <el-form-item style="width: 40vh" label="接收用户" prop="guser">
          <el-input type="textarea" autosize v-model="statusData.formData.guser"/>
        </el-form-item>
        <el-form-item style="width: 40vh" label="标题" prop="title">
          <el-input type="textarea" autosize v-model="statusData.formData.title"/>
        </el-form-item>
        <el-form-item style="width: 40vh" label="内容" prop="content">
          <el-input type="textarea" autosize v-model="statusData.formData.content"/>
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
      <el-table-column fixed prop="xid" label="ID" width="60" />
      <el-table-column prop="xstatus" label="消息类别" width="120" />
      <el-table-column prop="puser" label="发送用户" width="120" />
      <el-table-column prop="guser" label="接收用户" width="160" />
      <el-table-column prop="title" label="标题" width="200" />
      <el-table-column prop="content" label="消息内容" width="400" />
      <el-table-column fixed="right" label="数据操作" width="160">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="update_news(scope.row)">更新消息</el-button>
          <el-popconfirm
              width="10"
              confirm-button-text="Yes"
              cancel-button-text="No"
              :hide-icon="true"
              title="确定删除该消息吗?"
              @confirm="delete_news(scope.row)"
              @cancel=""
          >
            <template #reference>
              <el-button link type="primary" size="small">删除消息</el-button>
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

const user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const statusData = reactive({
  tableData: [],
  formData: {},
  formrules: {
    xstatus: [
      {required: true, message: '请选择类型', trigger: 'blur'}
    ],
    title: [
      {required: true, message: '请输入标题', trigger: 'blur'}
    ],
    guser: [
      {required: true, message: '请输入接收用户', trigger: 'blur'}
    ]
  },
})

const fresh = () =>{
  request.get("/News/news_findPage", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
    }
  }).then(res => {
    statusData.tableData = res.data.data
    total.value = res.data.total
  })
}
fresh()

const centerDialogVisible = ref(false)
const insert_news = () => {
  centerDialogVisible.value = true
  statusData.formData = {
    puser: user.username,
  }
}
const update_news = (row) => {
  centerDialogVisible.value = true
  statusData.formData = JSON.parse(JSON.stringify(row))
}
const iou = () => {
  centerDialogVisible.value = false
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      if (statusData.formData.xid == null) {
        request.post('/News/news_insert', statusData.formData).then(res => {
          if (res.code === "200" && res.data === true) {
            ElNotification({
              title: '发送消息成功',
              type: 'success',
              message: "已发送" + statusData.formData.xstatus + "标题: " + statusData.formData.title + " 内容: " + statusData.formData.content
            })
            fresh()
          }else{
            ElNotification({
              title: '发送消息失败',
              type: 'error',
              message: "消息内容格式有误或服务器异常"
            })
          }
        })
      }else{
        request.put('/News/news_update',statusData.formData).then(res => {
          if(res.code === "200" && res.data === true){
            ElNotification({
              title: '更新消息内容成功',
              type: 'success',
              message: "消息整体内容已更新"
            })
            fresh()
          }else{
            ElNotification({
              title: '更新消息内容失败',
              type: 'error',
              message: "消息内容格式有误或服务器异常"
            })
          }
        })
      }
    }else{
      ElNotification({
        title: '操作数据失败',
        type: 'error',
        message: "消息内容格式有误或服务器异常"
      })
    }
  })
}
const refresh = () => {
    fresh()
    ElNotification({
      title: '刷新成功',
      type: 'success',
      message: "已刷新消息数据表"
    })
}
const delete_news = (row) => {
  request.delete('/News/news_delete', {
    params: {
      xid: row.xid,
    }
  }).then(res => {
    if (res.code === "200" && res.data === true) {
      ElNotification({
        title: '删除消息成功',
        type: 'success',
        message: "消息已删除"
      })
      fresh()
    } else {
      ElNotification({
        title: '删除消息失败',
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