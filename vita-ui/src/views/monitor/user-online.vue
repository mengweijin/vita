<script setup>
import { userOnlineApi } from "@/api/monitor/user-online-api";
import utils from "@/utils/utils.js";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const columns = reactive({
	createTime: { label: "Session 创建时间", visible: false },
	id: { label: "ID", visible: true },
	index: { label: "序号列", visible: false },
	loginId: { label: "账号", visible: true },
	loginType: { label: "登录类型", visible: true },
	operation: { label: "操作", visible: true },
	terminalInfoCount: { label: "最近登录终端数", visible: true },
	type: { label: "会话类型", visible: true },
});

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
	current: 1,
	keywords: undefined,
	loginType: undefined,
	size: 10,
	total: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
	queryFormRef.value.resetFields();
	loadTableData();
};

const loadTableData = () => {
	loading.value = true;
	userOnlineApi.page(queryParams).then((res) => {
		tableData.value = res.records;
		queryParams.total = res.total;
		loading.value = false;
	});
};

const handlePageChange = (currentPage, pageSize) => {
	queryParams.current = currentPage;
	queryParams.size = pageSize;
	loadTableData();
};

const handleKickOutByUsername = (username) => {
	userOnlineApi.kickOutByUsername(username).then(() => {
		loadTableData();
	});
};

const handleKickOutByToken = (row) => {
	userOnlineApi.kickOutByToken(row.encryptTokenValue).then(() => {
		utils.remove(dialog.data.terminalInfoList, (item) => item.index === row.index);
		loadTableData();
	});
};

const dialog = reactive({
	data: {},
	visible: false,
});

const handleDetail = (row) => {
	dialog.data = { ...row };
	dialog.visible = true;
};

onMounted(() => {
	loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData"
    class="vt-search-container">
    <el-form-item prop="keywords" label="关键字">
      <el-input v-model="queryParams.keywords" placeholder="账号、登录类型" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" native-type="submit">
        <template #icon>
          <el-icon>
            <Icon icon="ep:search"></Icon>
          </el-icon>
        </template>
        搜索
      </el-button>
      <el-button type="warning" @click="resetQueryForm">
        <template #icon>
          <el-icon>
            <Icon icon="ep:refresh-left"></Icon>
          </el-icon>
        </template>
        重置
      </el-button>
    </el-form-item>
  </el-form>

  <el-divider style="margin: 0px;" />

  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->

    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table-container">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row>
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.loginId.visible" prop="loginId" label="账号" min-width="80" align="center" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.type.visible" prop="type" label="会话类型" min-width="120" />
      <el-table-column v-if="columns.loginType.visible" prop="loginType" label="登录类型" min-width="100" />
      <el-table-column v-if="columns.terminalInfoCount.visible" label="最近登录终端数" min-width="80" align="center">
        <template #default="{ row }">
          {{ row.terminalInfoList.length }}
        </template>
      </el-table-column>
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="Session 创建时间" width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="详情" placement="top">
              <el-button type="primary" text :size="size" @click="handleDetail(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="强制下线" placement="top">
              <div style="display: inline-block;">
                <el-popconfirm placement="left" width="400" :title="`确定从所有已登录终端强制下线用户【${scope.row.loginId}】吗？`"
                  confirm-button-text="确定" cancel-button-text="取消"
                  @confirm="handleKickOutByUsername(scope.row.loginId)">
                  <template #reference>
                    <el-button type="danger" text :size="size">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ri:logout-box-line"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination background layout="total, sizes, prev, pager, next, jumper"
      v-model:current-page="queryParams.current" v-model:page-size="queryParams.size" :total="queryParams.total"
      @change="handlePageChange" />
  </div>

  <el-dialog v-model="dialog.visible" :title="`【${dialog.data.loginId}】- 登录终端详情`" destroy-on-close align-center
    width="80%">
    <el-table :data="dialog.data.terminalInfoList" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row>
      <el-table-column prop="index" label="会话索引" width="100" fixed="left" />
      <el-table-column prop="tokenValue" label="Token" min-width="200" />
      <el-table-column prop="deviceType" label="设备类型" width="140" />
      <el-table-column prop="deviceId" label="设备唯一标识" width="300" />
      <el-table-column prop="createTime" label="登录时间" align="center" width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" align="center" width="90">
        <template #default="scope">
          <div>
            <el-tooltip content="强制下线" placement="top">
              <div style="display: inline-block;">
                <el-popconfirm placement="left" width="400"
                  :title="`确定从会话索引【${scope.row.index}】终端【${scope.row.deviceId}】强制下线账号【${dialog.data.loginId}】吗？`"
                  confirm-button-text="确定" cancel-button-text="取消" @confirm="handleKickOutByToken(scope.row)">
                  <template #reference>
                    <el-button type="danger" text :size="size">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ri:logout-box-line"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<style scoped></style>
