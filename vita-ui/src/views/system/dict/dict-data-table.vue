<script setup>
import { dictDataApi } from "@/api/system/dict-api";
import VtTableBarRight from "@/components/modules/vt-table-bar-right.vue";
import VtDictTag from "@/components/modules/vt-dict-tag.vue";
import DictDataEdit from './dict-data-edit.vue';

const loading = ref(true);

const visible = ref(false);

const dictType = ref(null);

const size = ref('default');

const tableData = ref([]);

const columns = reactive({
  selection: { label: '选择列', visible: false },
  index: { label: '序号列', visible: false },
  id: { label: 'ID', visible: false },
  code: { label: '字典编码', visible: false },
  label: { label: '字典标签', visible: true },
  val: { label: '字典值', visible: true },
  tag: { label: '标签样式', visible: true },
  seq: { label: '排序', visible: true },
  disabled: { label: '字典状态', visible: true },
  remark: { label: '备注', visible: true },
  createByName: { label: '创建者', visible: false },
  createTime: { label: '创建时间', visible: false },
  updateByName: { label: '更新者', visible: true },
  updateTime: { label: '更新时间', visible: true },
  operation: { label: '操作', visible: true },
});

const loadTableData = () => {
  dictDataApi.list({ code: dictType.value.code }).then((res) => {
    tableData.value = res;
    loading.value = false;
  });
};

const dictDataEditRef = ref(null);

const handleAdd = () => {
  dictDataEditRef.value.data = { code: dictType.value.code };
  dictDataEditRef.value.visible = true;
}

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  dictDataEditRef.value.data = { ...row };
  dictDataEditRef.value.visible = true;
}

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  dictDataApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handleBatchDelete = () => {
  let ids = selected.value.map(item => item.id).join();
  handleDelete(ids);
}

const onOpened = () => {
  loadTableData();
}

const onClosed = () => {
  visible.value = false;
  dictType.value = null;
  selected.value = [];
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, dictType })
</script>

<template>
  <el-dialog v-model="visible" :title="`字典数据 -【${dictType?.name}】-【${dictType?.code}】`" destroy-on-close align-center
    @opened="onOpened" @closed="onClosed" width="80%">
    <div v-loading="loading">
      <!-- 表格头-->
      <el-row :gutter="10" style="padding: 15px 0px">
        <!-- 左侧 -->
        <el-col :span="1.5">
          <el-button type="primary" @click="handleAdd(null)">
            <template #icon>
              <el-icon>
                <Icon icon="ep:plus"></Icon>
              </el-icon>
            </template>
            新增
          </el-button>
        </el-col>
        <el-col :span="1.5" v-show="selected.length">
          <el-popconfirm placement="right" width="400" :title="`确定全部删除已选择的【${selected.map(i => i.label).join()}】吗？`"
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
        <VtTableBarRight :tableRef="tableRef" :columns="columns" :shows="['size', 'columns']"
          @update-size="(val) => size = val" />
      </el-row>

      <el-table :data="tableData" :size="size" row-key="id" stripe border show-overflow-tooltip highlight-current-row
        @selection-change="(val) => selected = val">
        <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
        <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
        <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
        <el-table-column v-if="columns.code.visible" prop="code" label="字典编码" min-width="220" />
        <el-table-column v-if="columns.label.visible" prop="label" label="字典标签" min-width="160" />
        <el-table-column v-if="columns.val.visible" prop="val" label="字典值" min-width="160" />
        <el-table-column v-if="columns.tag.visible" prop="tag" label="标签样式" min-width="100" align="center">
          <template #default="{ row }">
            <el-tag :key="row.val + ''" :size="size" :type="row.tag" effect="dark">
              {{ row.label + "" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.seq.visible" prop="seq" label="排序" min-width="90" align="center" sortable />
        <el-table-column v-if="columns.disabled.visible" prop="disabled" label="字典状态" min-width="100" align="center">
          <template #default="{ row }">
            <VtDictTag :code="'vt_disabled'" :value="row.disabled" :size="size"></VtDictTag>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="180" />
        <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center"
          min-width="100" />
        <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center"
          min-width="180" />
        <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center"
          min-width="100" />
        <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center"
          min-width="180" />
        <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
          <template #default="scope">
            <div>
              <el-tooltip content="新增" placement="top" v-if="false">
                <el-button type="primary" text :size="size" @click="handleAdd(scope.row.id)">
                  <template #icon>
                    <el-icon :size="size">
                      <Icon icon="ep:plus"></Icon>
                    </el-icon>
                  </template>
                </el-button>
              </el-tooltip>
              <el-tooltip content="编辑" placement="top">
                <el-button type="primary" text :size="size" style="margin-left: 0px;" @click="handleEdit(scope.row)">
                  <template #icon>
                    <el-icon :size="size">
                      <Icon icon="ep:edit"></Icon>
                    </el-icon>
                  </template>
                </el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <div style="display: inline-block;">
                  <el-popconfirm placement="left" width="400" :title="`确定删除字典标签【${scope.row.label}】吗？`"
                    confirm-button-text="确定" cancel-button-text="取消" @confirm="handleDelete(scope.row.id)">
                    <template #reference>
                      <el-button type="danger" text :size="size">
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

      <DictDataEdit ref="dictDataEditRef" @refresh-table="loadTableData"></DictDataEdit>
    </div>
  </el-dialog>
</template>

<style scoped></style>
