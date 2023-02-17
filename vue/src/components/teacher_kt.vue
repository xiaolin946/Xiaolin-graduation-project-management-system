<template>
  <h3 style="margin-left: 40%; margin-bottom: 10px">我的课题</h3>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button style="margin-right: 0.4%" type="primary" round @click="mykt_insert">创建新课题</el-button>
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <el-dialog
      v-model="centerDialogVisible"
      title="课题内容"
      width="50%"
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
      <el-form-item style="width: 40vh" label="课题ID" prop="tid">
        <el-input v-model="statusData.formData.tid" maxlength="5"/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="授课教师" prop="teaname">
        <el-input :model-value="user.username" disabled/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="课题名称" prop="tname">
        <el-input v-model="statusData.formData.tname"/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="课题题目" prop="tt">
        <el-input type="textarea" autosize v-model="statusData.formData.tt"/>
      </el-form-item>
      <el-form-item style="width: 80vh" label="题目内容" prop="ttc">
        <el-input type="textarea" autosize v-model="statusData.formData.ttc"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="kt">确认</el-button>
      </span>
    </template>
  </el-dialog>
  <el-table :data="statusData.tableData" style="height:80%; width: 100%; margin-bottom: 5px">
    <el-table-column fixed prop="id" label="序号" width="70" />
    <el-table-column fixed prop="tid" label="课题ID" width="70" />
    <el-table-column prop="teaname" label="教师用户名" width="120" />
    <el-table-column prop="tname" label="课题名称" width="150" />
    <el-table-column prop="tt" label="题目名称" width="180" />
    <el-table-column  label="题目内容" width="260" >
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
    <el-table-column fixed="right" label="数据操作" width="160">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="mykt_update(scope.row)">更新课题</el-button>
        <el-popconfirm
            width="10"
            confirm-button-text="Yes"
            cancel-button-text="No"
            :hide-icon="true"
            title="确定删除该课题吗?"
            @confirm="mykt_delete(scope.row)"
            @cancel=""
        >
          <template #reference>
            <el-button link type="primary" size="small">删除课题</el-button>
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
    tid: [
      {required: true, message: '请输入课题ID', trigger: 'blur'},
      {max: 5, message: '课题ID必须为五位', trigger: 'blur'},
      {min: 5, message: '课题ID必须为五位', trigger: 'blur'}
    ],
    tname: [
      {required: true, message: '请输入课题名称', trigger: 'blur'}
    ],
    tt: [
      {required: true, message: '请输入课题题目', trigger: 'blur'}
    ],
    ttc: [
      {required: true, message: '请输入题目内容', trigger: 'blur'}
    ]
  },
})

const fresh = () =>{
  request.get("/Kt/mykt_findPage", {
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

const centerDialogVisible = ref(false)
const mykt_insert = () => {
  centerDialogVisible.value = true
  statusData.formData = {
    teaname: user.username,
  }
}
const mykt_update = (row) => {
  centerDialogVisible.value = true
  statusData.formData = JSON.parse(JSON.stringify(row))
}
const kt = () => {
  centerDialogVisible.value = false
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      if (statusData.formData.id == null) {
        request.post('/Kt/mykt_insert', statusData.formData).then(res => {
          if (res.code === "200" && res.data === true) {
            ElNotification({
              title: '课题创建成功',
              type: 'success',
              message: "课题: " + statusData.formData.tname + "创建完成"
            })
            fresh()
          }else{
            ElNotification({
              title: '课题创建失败',
              type: 'error',
              message: "课题ID已存在或课题内容格式有误"
            })
          }
        })
      }else{
        request.put('/Kt/mykt_update',statusData.formData).then(res => {
          if(res.code === "200" && res.data === true){
            ElNotification({
              title: '更新课题内容成功',
              type: 'success',
              message: "课题整体内容已更新"
            })
            fresh()
          }else{
            ElNotification({
              title: '更新课题内容失败',
              type: 'error',
              message: "课题ID已存在或课题内容格式有误"
            })
          }
        })
      }
    }else{
      ElNotification({
        title: '操作数据失败',
        type: 'error',
        message: "内容格式有误"
      })
    }
  })
}
const refresh = () => {
  fresh()
  ElNotification({
    title: '刷新成功',
    type: 'success',
    message: "已刷新课题数据表"
  })
}
const mykt_delete = (row) => {
  request.delete('/Kt/mykt_delete', {
    params: {
      id: row.id,
    }
  }).then(res => {
    if (res.code === "200" && res.data === true) {
      ElNotification({
        title: '删除课题成功',
        type: 'success',
        message: "课题已删除"
      })
      fresh()
    } else {
      ElNotification({
        title: '删除课题失败',
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