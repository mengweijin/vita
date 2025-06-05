<script setup>
import { addFullPath } from "@/utils/tool";
import { categoryApi } from "@/api/system/category-api";
import { toArrayTree } from "xe-utils";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  id: undefined,
  parentId: undefined,
  name: undefined,
  code: undefined,
  seq: undefined,
  disabled: undefined,
  remark: undefined,
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.parentId = data.value.parentId ?? undefined;
  form.name = data.value.name ?? undefined;
  form.code = data.value.code ?? undefined;
  form.seq = data.value.seq ?? 1;
  form.disabled = data.value.disabled ?? 'N';
  form.remark = data.value.remark ?? undefined;
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    if (form.id) {
      categoryApi.update(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    } else {
      categoryApi.create(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const categoryList = ref([]);

const categoryTreeSelectOptions = computed(() => {
  categoryList.value.forEach((item) => item.disabled = false);
  addFullPath(categoryList.value, { pathKey: 'name' })
  return toArrayTree(categoryList.value, { sortKey: 'seq' });
});

const onOpened = () => {
  loading.value = true;
  categoryApi.list().then((res) => {
    categoryList.value = res;
    init();
    loading.value = false;
  });
}

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
}

/** 暴露给父组件，父组件可通过 categoryEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="data?.id ? '编辑' : '新增'" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="parentId" label="父节点">
        <el-tree-select v-model="form.parentId" :data="categoryTreeSelectOptions"
          :props="{ label: 'nameFullPath', value: 'id', children: 'children' }" check-strictly filterable clearable
          default-expand-all placeholder="请选择">
          <template #default="{ data: { name } }">
            {{ name }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-form-item prop="name" label="名称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.name" clearable maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="code" label="编码" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.code" clearable maxlength="64" autocomplete="off" />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="disabled" label="状态">
            <el-switch v-model="form.disabled" inline-prompt active-text="启用" inactive-text="停用" active-value="N"
              inactive-value="Y" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="seq" label="排序">
            <el-input-number v-model="form.seq" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item prop="remark" label="备注">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 8 }" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" @click="onSubmit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="warning" @click="init">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="primary" @click="onClosed">
          <template #icon>
            <el-icon>
              <Icon icon="ep:close"></Icon>
            </el-icon>
          </template>
          取消
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>
