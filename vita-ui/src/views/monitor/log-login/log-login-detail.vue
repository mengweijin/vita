<script setup>
const loading = ref(true);

const size = ref("default");

const visible = ref(false);

const data = ref({});

const onOpened = () => {
	loading.value = false;
};

const onClosed = () => {
	visible.value = false;
	data.value = {};
};

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data });
</script>

<template>
  <el-dialog v-model="visible" :title="'登录日志详情'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
    width="80%">
    <div v-loading="loading">
      <el-descriptions title="" :column="2" :size="size" border>
        <el-descriptions-item label="登录账号" label-align="right" min-width="100">
          {{ data?.username }}
        </el-descriptions-item>
        <el-descriptions-item label="登录类型" label-align="right">
          <VtTagDict :code="'vt_login_type'" :value="data?.loginType" :size="'default'"></VtTagDict>
        </el-descriptions-item>

        <el-descriptions-item label="登录 IP" label-align="right">
          {{ data?.ip }}
        </el-descriptions-item>

        <el-descriptions-item label=" IP 位置" label-align="right">
          {{ data?.ipLocation }}
        </el-descriptions-item>

        <el-descriptions-item label="操作系统" label-align="right">
          {{ data?.os }}
        </el-descriptions-item>
        <el-descriptions-item label="浏览器" label-align="right">
          {{ data?.browser }}
        </el-descriptions-item>
        <el-descriptions-item label="设备平台" label-align="right">
          {{ data?.platform }}
        </el-descriptions-item>

        <el-descriptions-item label="登录是否成功" label-align="right">
          <VtTagDict :code="'vt_succeeded'" :value="data?.success" :size="'default'"></VtTagDict>
        </el-descriptions-item>

        <el-descriptions-item label="创建者" label-align="right">
          {{ data?.createByName }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间" label-align="right">
          {{ data?.createTime }}
        </el-descriptions-item>

        <el-descriptions-item label="更新者" label-align="right">
          {{ data?.updateByName }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间" label-align="right">
          {{ data?.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="失败信息" label-align="right" :span="2" v-if="data?.success === 'N'">
          <div class="vt-descriptions-item">
            {{ data?.errorMsg }}
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </el-dialog>
</template>

<style scoped>
.vt-descriptions-item {
  /* 根据需求调整高度 */
  max-height: 350px;
  /* 启用水平滚动 */
  overflow-x: auto;
  /* 启用垂直滚动 */
  overflow-y: auto;
  /* 长单词自动换行 */
  word-break: break-all;
  /** 保留所有空白符 */
  white-space: pre-wrap;
}
</style>
