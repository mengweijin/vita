<route lang="yaml">
meta:
  title: 字典数据
</route>

<script setup>
import { dictDataApi, dictTypeApi } from "@/api/system/dict-api.js";
import { useDict } from "./hooks.js";
import DictDataEdit from "./components/dict-data-edit.vue";
const columns = useDict().dictDataColumns;

const route = useRoute();

const dictTypeId = route.params.dictTypeId;

const dictType = ref({});

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const loadTableData = () => {
  dictDataApi.list({ typeId: dictTypeId }).then((res) => {
    tableData.value = res;
    loading.value = false;
  });
};

// -----------------------------------------
const editDialogVisible = ref(false);
const editData = ref(null);

const handleAdd = () => {
  editData.value = null;
  editDialogVisible.value = true;
};

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  editData.value = { ...row };
  editDialogVisible.value = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  dictDataApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

const handleBatchDelete = () => {
  const ids = selected.value.map((item) => item.id).join();
  handleDelete(ids);
};

const title = computed(() => `${dictType.value?.name || ""} ${dictType.value?.code || ""}`);

onMounted(() => {
  loading.value = true;
  dictTypeApi.getById(dictTypeId).then((res) => {
    dictType.value = res;
    loadTableData();
  });
});
</script>

<template>
  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->
    <el-col :span="1.5" v-permission="'system:dictData:create'">
      <el-button type="primary" @click="handleAdd(null)">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length" v-permission="'system:dictData:remove'">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的【${selected.map((i) => i.label).join()}】吗？`"
        confirm-button-text="确定"
        cancel-button-text="取消"
        @confirm="handleBatchDelete"
      >
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
    <el-col :span="1.5">
      <div>
        <h3>{{ title }}</h3>
      </div>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight
      :tableRef="tableRef"
      :columns="columns"
      :shows="['size', 'columns']"
      @update-size="(val) => (size = val)"
    />
  </el-row>
  <div class="vt-table">
    <el-table
      ref="tableRef"
      v-loading="loading"
      :data="tableData"
      :size="size"
      row-key="id"
      height="100%"
      stripe
      border
      show-overflow-tooltip
      highlight-current-row
      @selection-change="(val) => (selected = val)"
    >
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.code.visible" prop="code" label="字典编码" min-width="220" />
      <el-table-column v-if="columns.label.visible" prop="label" label="字典标签" min-width="160" />
      <el-table-column v-if="columns.val.visible" prop="val" label="字典值" min-width="160" />
      <el-table-column
        v-if="columns.tag.visible"
        prop="tag"
        label="标签样式"
        min-width="130"
        align="center"
      >
        <template #default="{ row }">
          <el-tag :key="row.val + ''" :size="size" :type="row.tag" effect="dark">
            {{ row.label + "" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.seq.visible"
        prop="seq"
        label="排序"
        min-width="90"
        align="center"
        sortable
      />
      <el-table-column
        v-if="columns.disabled.visible"
        prop="disabled"
        label="字典状态"
        min-width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_disabled'" :value="row.disabled" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="150" />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="创建者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
        align="center"
        width="160"
      />
      <el-table-column
        v-if="columns.updateByName.visible"
        prop="updateByName"
        label="更新者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.updateTime.visible"
        prop="updateTime"
        label="更新时间"
        align="center"
        width="160"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top" v-if="false">
              <el-button
                type="primary"
                text
                :size="size"
                @click="handleAdd(scope.row.id)"
                v-permission="'system:dictData:create'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:plus"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleEdit(scope.row)"
                v-permission="'system:dictData:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block">
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除字典标签【${scope.row.label}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'system:dictData:remove'"
                    >
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
  </div>
  <DictDataEdit
    v-model:visible="editDialogVisible"
    :data="editData"
    :dictType="dictType"
    @refresh="loadTableData"
  ></DictDataEdit>
</template>

<style scoped>
.vt-table {
  /** 查询表单：50px; 表格头：62px；分页组件：42px; 其它：13px */
  height: calc(var(--vt-table-height) + 50px + 42px);
}
</style>
