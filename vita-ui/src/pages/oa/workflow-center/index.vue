<route lang="yaml">
meta:
  title: 流程中心
  permission: oa:workflowCenter:view
</route>

<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";

const dialogWorkflowFormLoaderVisible = ref(false);
const definitionId = ref(null);
const openEmployeeLeaveForm = async () => {
  const definition = await flowDefinitionApi.queryByFlowCode("employee_leave");
  definitionId.value = definition.id;
  dialogWorkflowFormLoaderVisible.value = true;
};

const handleDevelopment = () => {
  ElMessage.warning({
    duration: 5000,
    message: "功能未开发！演示请参考【员工请假】模块。",
    showClose: true,
  });
};
onMounted(() => {});
</script>

<template>
  <div>
    <!-- 人事流程 -->
    <div class="vt-header" style="margin-top: 5px">人事流程</div>
    <div class="vt-card-container">
      <el-card>
        <div class="vt-card-content" @click="openEmployeeLeaveForm">
          <!-- 日历/请假：蓝色 -->
          <el-icon :size="30" color="#409EFF">
            <Icon icon="ep:calendar"></Icon>
          </el-icon>
          <span>员工请假</span>
        </div>
      </el-card>
      <el-card>
        <div class="vt-card-content" @click="handleDevelopment">
          <!-- 检查/转正：绿色 -->
          <el-icon :size="30" color="#67C23A">
            <Icon icon="ep:check"></Icon>
          </el-icon>
          <span>员工转正</span>
        </div>
      </el-card>
      <el-card>
        <div class="vt-card-content" @click="handleDevelopment">
          <!-- 交换/调岗：灰色 -->
          <el-icon :size="30" color="#909399">
            <Icon icon="ri:exchange-line"></Icon>
          </el-icon>
          <span>员工调岗</span>
        </div>
      </el-card>
      <el-card>
        <div class="vt-card-content" @click="handleDevelopment">
          <!-- 移除/离职：红色 -->
          <el-icon :size="30" color="#F56C6C">
            <Icon icon="ep:remove"></Icon>
          </el-icon>
          <span>员工离职</span>
        </div>
      </el-card>
    </div>

    <!-- 财务流程 -->
    <div class="vt-header">财务流程</div>
    <div class="vt-card-container">
      <el-card>
        <div class="vt-card-content" @click="handleDevelopment">
          <!-- 金钱/报销：橙色 -->
          <el-icon :size="30" color="#E6A23C">
            <Icon icon="ep:money"></Icon>
          </el-icon>
          <span>报销流程</span>
        </div>
      </el-card>
      <el-card>
        <div class="vt-card-content" @click="handleDevelopment">
          <!-- 钱包/预支：金色 -->
          <el-icon :size="30" color="#F7BA2A">
            <Icon icon="ep:wallet"></Icon>
          </el-icon>
          <span>预支费用申请</span>
        </div>
      </el-card>
    </div>

    <VtDialogWorkflowFormLoader
      v-model="dialogWorkflowFormLoaderVisible"
      :definition-id="definitionId"
    />
  </div>
</template>

<style scoped>
.vt-header {
  margin: 20px 0 10px 0;
  font-weight: bold;
  font-size: 16px;
}
.vt-card-container {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}
.el-card {
  width: 200px;
  flex-shrink: 0;
  cursor: pointer;
  transition: all 0.3s;
}
.el-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}
.vt-card-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
</style>
