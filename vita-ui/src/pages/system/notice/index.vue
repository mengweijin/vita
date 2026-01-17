<route lang="yaml">
meta:
  title: 系统公告
</route>


<script setup>
import { noticeApi } from "@/api/system/notice-api";
import { columns } from "./columns.js";
import NoticeDetail from "./components/notice-detail.vue";
import NoticeEdit from "./components/notice-edit.vue";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  current: 1,
  description: undefined,
  released: undefined,
  size: 10,
  title: undefined,
  total: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  noticeApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

const noticeEditRef = useTemplateRef("noticeEditRef");

const handleAdd = () => {
  noticeEditRef.value.data = {};
  noticeEditRef.value.visible = true;
};

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  noticeEditRef.value.data = { ...row };
  noticeEditRef.value.visible = true;
};

const handleRelease = (id) => {
  noticeApi.release(id).then(() => {
    loadTableData();
  });
};

const handleRevoke = (id) => {
  noticeApi.revoke(id).then(() => {
    loadTableData();
  });
};

const noticeDetailRef = useTemplateRef("noticeDetailRef");

const handleViewDetail = (row) => {
  noticeDetailRef.value.data = { ...row };
  noticeDetailRef.value.visible = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  noticeApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

const handleBatchDelete = () => {
  const ids = selected.value.map((item) => item.id).join();
  handleDelete(ids);
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="title" label="标题">
      <el-input v-model="queryParams.title" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="description" label="正文">
      <el-input v-model="queryParams.description" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="released" label="发布状态">
      <VtSelectDict v-model="queryParams.released" :code="'vt_released'"></VtSelectDict>
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
    <el-col :span="1.5" v-permission="'system:notice:create'">
      <el-button type="primary" @click="handleAdd(null)">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length" v-permission="'system:notice:remove'">
      <el-popconfirm placement="right" width="400" :title="`确定全部删除已选择的【${selected.map(i => i.title).join()}】吗？`"
        confirm-button-text="确定" cancel-button-text="取消" @confirm="handleBatchDelete">
        <template #reference>
          <el-button type="danger">
            <template #icon>
              <el-icon>
                <Icon icon="ep:delete"></Icon>
              </el-icon>
            </template>
            批量删除
          </el-button>
        </template>
      </el-popconfirm>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.title.visible" prop="title" label="标题" min-width="200" fixed="left">
        <template #default="{ row }">
          <a href="javascript:void(0);" class="vt-title" @click="handleViewDetail(row)">{{ row.title }}</a>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.description.visible" prop="description" label="内容" min-width="300" />
      <el-table-column v-if="columns.released.visible" prop="released" label="发布状态" width="120" align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_released'" :value="row.released" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center" width="100" />
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center" width="180" />
      <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center" width="100" />
      <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center" width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="210">
        <template #default="scope">
          <div>
            <el-tooltip content="查看" placement="top">
              <el-button type="primary" text :size="size" @click="handleViewDetail(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="发布" placement="top" v-if="scope.row.released === 'N'">
              <el-button type="primary" text :size="size" style="margin-left: 0px;" @click="handleRelease(scope.row.id)"
                v-permission="'system:notice:release'">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:promotion"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="撤回" placement="top" v-if="scope.row.released === 'Y'">
              <el-button type="warning" text :size="size" style="margin-left: 0px;" @click="handleRevoke(scope.row.id)"
                v-permission="'system:notice:revoke'">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:arrow-go-back-fill"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="编辑" placement="top" v-if="scope.row.released === 'N'">
              <el-button type="primary" text :size="size" style="margin-left: 0px;" @click="handleEdit(scope.row)"
                v-permission="'system:notice:update'">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="删除" placement="top" v-if="scope.row.released === 'N'">
              <div style="display: inline-block;">
                <el-popconfirm placement="left" width="400" :title="`确定删除【${scope.row.title}】吗？`"
                  confirm-button-text="确定" cancel-button-text="取消" @confirm="handleDelete(scope.row.id)">
                  <template #reference>
                    <el-button type="danger" text :size="size" v-permission="'system:notice:remove'">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ep:delete"></Icon>
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

  <NoticeEdit ref="noticeEditRef" @refresh-table="loadTableData"></NoticeEdit>
  <NoticeDetail ref="noticeDetailRef"></NoticeDetail>
</template>

<style scoped></style>
