<script setup>
import { categoryApi } from "@/api/system/category-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  data: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["update:visible", "refresh"]);

// 是否为编辑态（有 id 视为编辑）。!! 是 JavaScript 里快速把一个值转换成布尔值（true/false）的简写，本质是两次取反
const isEdit = computed(() => !!props.data?.id);

const INITIAL_FORM = {
  code: undefined,
  disabled: "N",
  id: undefined,
  name: undefined,
  parentId: undefined,
  remark: undefined,
  seq: 1,
};

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = ref({ ...INITIAL_FORM });

const formRef = useTemplateRef("formRef");

const onReset = () => {
  formRef.value?.resetFields();
  // 重置后清除验证错误
  formRef.value?.clearValidate();
};

const onClosed = () => {
  emit("update:visible", false);
};

const onSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false);
  if (valid) {
    if (isEdit.value) {
      await categoryApi.update(form.value);
    } else {
      await categoryApi.create(form.value);
    }
    emit("refresh");
    emit("update:visible", false);
  }
};

// 监听 data 变化：回填表单或重置
watch(
  () => props.data,
  (val) => {
    if (val) {
      // 填充表单，仅挑选 INITIAL_FORM 中定义的字段以避免冗余提交
      form.value = utils.pick(val, Object.keys(INITIAL_FORM));
    } else {
      // 当关闭弹窗或清除数据时，重置表单
      formRef.value?.clearValidate();
      form.value = { ...INITIAL_FORM };
    }
  },
  { immediate: true },
);

// -------------------------------------
const categoryList = ref([]);

const treeOptions = computed(() => {
  // 使用临时数组避免污染原始数据，并转换 disabled 字段值为布尔值
  const list = categoryList.value.map((item) => ({ ...item, disabled: false }));
  // 添加全路径名称
  utils.addFullPath(list, { pathKey: "name" });
  // 转换为树形结构
  return utils.toArrayTree(list, { sortKey: "seq" });
});

onMounted(async () => {
  categoryList.value = await categoryApi.list();
});
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '编辑' : '新增'"
    destroy-on-close
    align-center
    width="40%"
    @closed="onClosed"
  >
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="parentId" label="父节点">
        <el-tree-select
          v-model="form.parentId"
          :data="treeOptions"
          :props="{ label: 'nameFullPath', value: 'id', children: 'children' }"
          check-strictly
          filterable
          clearable
          default-expand-all
          placeholder=""
          :disabled="isEdit"
        >
          <template #default="{ data: { name } }">
            {{ name }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-form-item
        prop="name"
        label="名称"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.name" clearable maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item
        prop="code"
        label="编码"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.code" clearable maxlength="64" autocomplete="off" />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="disabled" label="状态">
            <el-switch
              v-model="form.disabled"
              inline-prompt
              active-text="启用"
              inactive-text="停用"
              active-value="N"
              inactive-value="Y"
            />
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
        <el-button type="warning" @click="onReset">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="info" @click="onClosed">
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
