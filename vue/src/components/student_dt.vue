<template>
  <div style="text-align: right; margin-bottom: 5px">
    <el-button style="margin-right: 0.4%" type="primary" round @click="student_dt">定题申请</el-button>
    <el-button :icon="Refresh" circle @click="refresh"/>
  </div>
  <h3 style="margin-left: 40%; margin-bottom: 10px">课题信息</h3>
  <el-dialog
      v-model="centerDialogVisible"
      title="定题申请"
      width="90%"
      align-center
  >
    <div style="text-align: right; margin-bottom: 5px">
      <el-button style="margin-right: 0.4%" type="primary" round @click="dt_insert">发送申请</el-button>
      <el-button :icon="Refresh" circle @click="refresh2"/>
    </div>
    <h3 style="margin-left: 45%; margin-bottom: 10px">已发送申请</h3>
    <el-table :data="statusData.tableData2" style="height:80%; width: 100%; margin-bottom: 5px">
      <el-table-column fixed prop="id" label="申请ID" width="70" />
      <el-table-column prop="stuuname" label="学生学号" width="140" />
      <el-table-column prop="stuname" label="学生姓名" width="120" />
      <el-table-column prop="sex" label="学生性别" width="80" />
      <el-table-column prop="age" label="学生年龄" width="80" />
      <el-table-column prop="tid" label="课题编号" width="80" />
      <el-table-column prop="teaname" label="授课教师" width="100" />
      <el-table-column prop="tname" label="课题名称" width="150" />
      <el-table-column prop="tt" label="课题题目" width="160" />
      <el-table-column prop="ps" label="申请状态" width="80" />
      <el-table-column fixed="right" label="数据操作" width="80">
        <template #default="scope">
          <el-popconfirm
            width="10"
            confirm-button-text="Yes"
            cancel-button-text="No"
            :hide-icon="true"
            title="确定取消该申请吗?"
            @confirm="dt_delete(scope.row)"
            @cancel=""
        >
            <template #reference>
              <el-button link type="primary" size="small">取消申请</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <el-dialog
      v-model="centerDialogVisible2"
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
      <el-form-item style="width: 40vh" label="学生学号" prop="stuuname">
        <el-input :model-value="statusData.myxx.username" disabled/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="学生姓名" prop="stuname">
        <el-input :model-value="statusData.myxx.name" disabled/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="学生性别" prop="sex">
        <el-input :model-value="statusData.myxx.sex" disabled/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="学生性别" prop="age">
        <el-input :model-value="statusData.myxx.age" disabled/>
      </el-form-item>
      <el-form-item style="width: 40vh" label="课题ID" prop="tid">
        <el-select v-model="statusData.formData.tid" placeholder="选择课题">
          <el-option
              v-for="item in statusData.l_tid"
              :key="item.tid"
              :label="item.tid"
              :value="item.tid"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible2=false; centerDialogVisible=true;">返回</el-button>
        <el-button type="primary" @click="dt">确认</el-button>
      </span>
    </template>
  </el-dialog>

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
  myxx: {},
  l_tid: [],
  sdt_ps: 0,
  sdt_count: 0,
  tableData: [],
  tableData2: [],
  formData: {},
  formrules: {
    tid: [
      {required: true, message: '请选择课题', trigger: 'blur'}
    ],
  }
})

request.get("/user/myxx", {
  params: {
    status: user.status,
    username: user.username
  }
}).then(res => {
  statusData.myxx = res.data
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
  request.get("/Kt/l_tid", {
    params: {
      stuuname: user.username
    }
  }).then(res => {
    statusData.l_tid = res.data
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
const fresh2 = () =>{
  request.get("/Dt/dt", {
    params: {
      stuuname: user.username
    }
  }).then(res => {
    statusData.tableData2 = res.data
  })
}
fresh2()
const refresh2 = () => {
  fresh2()
  ElNotification({
    title: '刷新成功',
    type: 'success',
    message: "已刷定题申请数据表"
  })
}

const centerDialogVisible = ref(false)
const centerDialogVisible2 = ref(false)
const student_dt = () => {
  centerDialogVisible.value = true
}
const dt_insert = () => {
  centerDialogVisible.value = false
  centerDialogVisible2.value = true

}
const dt_delete = (row) => {
  request.get("/Dt/sdt_ps",{
    params: {
      stuuname: user.username,
    }
  }).then(res => {
    statusData.sdt_ps = res.data

    if(statusData.sdt_ps===0){
      request.delete('/Dt/dt_delete', {
        params: {
          id: row.id,
        }
      }).then(res => {
        if (res.code === "200" && res.data === true) {
          ElNotification({
            title: '取消申请成功',
            type: 'success',
            message: "申请已取消"
          })
          fresh2()
        } else {
          ElNotification({
            title: '取消申请失败',
            type: 'error',
            message: "服务器异常"
          })
        }
      })
    }else{
      ElNotification({
        title: '取消申请失败',
        type: 'error',
        message: "定题已完成"
      })
    }
  })


}
const dt = () => {
  centerDialogVisible2.value = false
  centerDialogVisible.value = true

  request.get("/Dt/sdt_ps",{
    params: {
      stuuname: user.username,
    }
  }).then(res => {
    statusData.sdt_ps = res.data

    if(statusData.sdt_ps===0){
      request.get("/Dt/sdt_count", {
        params: {
          stuuname: user.username,
        }
      }).then(res => {
        statusData.sdt_count = res.data

        if(statusData.sdt_count>-1 && statusData.sdt_count<3){
          statusData.formData.stuuname = statusData.myxx.username
          statusData.formData.stuname = statusData.myxx.name
          statusData.formData.sex = statusData.myxx.sex
          statusData.formData.age = statusData.myxx.age
          statusData.formData.ps = '已发送'
          request.get("/Kt/kt", {
            params: {
              tid: statusData.formData.tid,
            }
          }).then(res => {
            statusData.formData.tid = res.data.tid
            statusData.formData.teaname = res.data.teaname
            statusData.formData.tname = res.data.tname
            statusData.formData.tt = res.data.tt

            proxy.$refs.ruleFormRef.validate((valid) => {
              if (valid) {
                request.put('/Dt/dt_insert', statusData.formData).then(res => {
                  if (res.code === "200" && res.data === true) {
                    ElNotification({
                      title: '定题申请发送成功',
                      type: 'success',
                      message: "申请已发送"
                    })
                    fresh2()
                  } else {
                    ElNotification({
                      title: '定题申请发送失败',
                      type: 'error',
                      message: "课题ID有误或服务器异常"
                    })
                  }
                })
              } else {
                ElNotification({
                  title: '定题申请发送失败',
                  type: 'error',
                  message: "课题ID有误"
                })
              }
            })
          })
        }else{
          ElNotification({
            title: '定题申请发送失败',
            type: 'error',
            message: "定题申请已满"
          })
        }
      })
    }else{
      ElNotification({
        title: '定题申请发送失败',
        type: 'error',
        message: "定题申请已完成"
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