<script setup>
import { menuApi } from "@/api/system/menu-api";
import { useDictStore } from "@/store/dict-store.js";
import utils from "@/utils/utils.js";

const dictStore = useDictStore();

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
  disabled: "N",
  icon: undefined,
  id: undefined,
  parentId: undefined,
  permission: undefined,
  seq: 1,
  title: undefined,
  type: "MENU",
  url: undefined,
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
      await menuApi.update(form.value);
    } else {
      await menuApi.create(form.value);
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
const menuTypeOptions = computed(() => {
  const menuTypes = dictStore.get("vt_menu_type");
  return menuTypes.map((item) => {
    item.disabled = item.disabled === "Y";
    return item;
  });
});

const menuList = ref([]);

const menuTreeSelectOptions = computed(() => {
  // 使用临时数组避免污染原始数据，并转换 disabled 字段值为布尔值
  const list = menuList.value.map((item) => ({ ...item, disabled: false }));
  // 添加全路径名称
  utils.addFullPath(list, { pathKey: "title" });
  // 转换为树形结构
  return utils.toArrayTree(list, { sortKey: "seq" });
});

onMounted(async () => {
  menuList.value = await menuApi.list();
});
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '编辑' : '新增'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="50%"
  >
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="type" label="菜单类型">
        <el-segmented
          v-model="form.type"
          :options="menuTypeOptions"
          :props="{ label: 'label', value: 'val', disabled: 'disabled' }"
        />
      </el-form-item>

      <el-form-item prop="parentId" label="父菜单">
        <el-tree-select
          v-model="form.parentId"
          :data="menuTreeSelectOptions"
          :props="{ label: 'titleFullPath', value: 'id', children: 'children' }"
          check-strictly
          filterable
          clearable
          placeholder=""
          :disabled="data?.id"
        >
          <template #default="{ data: { title } }">
            {{ title }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-form-item
        prop="icon"
        label="图标"
        style="width: 65%"
        v-if="form.type === 'DIR' || form.type === 'MENU'"
      >
        <VtIconPicker v-model="form.icon"></VtIconPicker>
      </el-form-item>

      <el-form-item
        prop="title"
        label="标题"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.title" clearable maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="permission">
        <template #label>
          <div class="vt-question-icon-container">
            <span>权限</span>
            <el-tooltip placement="top">
              <template #content>权限格式：以冒号分隔，比如：system:user:view</template>
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
          </div>
        </template>
        <el-input v-model="form.permission" clearable autocomplete="off" />
      </el-form-item>

      <el-form-item
        prop="url"
        label="URL"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'MENU' || form.type === 'URL'"
      >
        <template #label>
          <div class="vt-question-icon-container">
            <span>路由路径/URL</span>
            <el-tooltip placement="top">
              <template #content>
                vue-router 路由的路径或一个完整的 URL 地址，也是浏览器地址栏访问的路径。<br />
                比如：/system/menu 或 https://aday.fun
              </template>
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
          </div>
        </template>
        <el-input v-model="form.url" clearable autocomplete="off"></el-input>
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

<style scoped>
.vt-question-icon-container {
  display: flex;
  align-items: center;
}

.vt-question-icon:hover {
  /* 默认显示手型 */
  cursor: pointer;
}
</style>
