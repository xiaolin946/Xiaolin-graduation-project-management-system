<template>
  <h2 style="margin-left: 45%; margin-bottom: 20px">上传论文</h2>
  <el-upload
      class="upload-demo"
      drag
      action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
      multiple
      :limit="1"
      accept="zip,tz"
  >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">
      Drop file here or <em>click to upload</em>
    </div>
    <template #tip>
      <div class="el-upload__tip">
        zip/tz files with a size less than 500mb
      </div>
    </template>
  </el-upload>
</template>

<script setup>
import { UploadFilled } from '@element-plus/icons-vue'
</script>

<style>

</style>