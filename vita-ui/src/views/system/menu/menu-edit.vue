<script setup>
import VtIconPicker from "@/components/modules/vt-icon-picker.vue";
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const visible = ref(false);

const data = ref(null);

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值 */
const form = reactive({
  // id: undefined,
  // type: 'BTN',
  // title: undefined,
  // icon: undefined,
  // permission: undefined,
  // component: undefined,
  // routeName: undefined,
  // routePath: undefined,
  // seq: 1,
  // disabled: 'N',
});

const onDialogOpen = () => {
  let row = data.value;

  form.id = row?.id ?? undefined;
  form.type = row?.type ?? 'BTN';
  form.title = row?.title ?? undefined;
  form.icon = row?.icon ?? undefined;
  form.permission = row?.permission ?? undefined;
  form.component = row?.component ?? undefined;
  form.routeName = row?.routeName ?? undefined;
  form.routePath = row?.routePath ?? undefined;
  form.seq = row?.seq ?? 1;
  form.disabled = row?.disabled ?? 'N';
}

const formRef = ref(null);

const resetForm = () => {
  formRef.value.resetFields();
};

const onSubmit = () => {

}

const title = computed(() => {
  return data?.id ? '编辑' : '新增';
});

const closeDialog = () => {
  visible.value = false;
};

const menuTypeOptions = computed(() => {
  const menuTypes = dictStore.getDicts('vt_menu_type');
  return menuTypes.map(item => {
    item.disabled = (item.disabled === 'Y' ? true : false);
    return item;
  });
});

onMounted(() => {
});

/** 暴露给父组件，父组件可通过 menuEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <!--
      1. 父组件已经使用 v-if 来控制 dialog 组件了，所以这里面直接 model-value 固定写 true，即 el-dialog 内部逻辑一直显示 dialog。
      2. 如果使用 model 控制弹层显示/隐藏：直接绑定 v-model 会有警告，使用 model-value 绑定则没有警告
   -->
  <el-dialog :model-value="visible" :title="title" @open="onDialogOpen" align-center width="60%">
    <el-form ref="formRef" :model="form" label-width="auto" @submit.prevent="onSubmit">
      <el-form-item prop="type" label="菜单类型">
        <el-segmented v-model="form.type" :options="menuTypeOptions"
          :props="{ label: 'label', value: 'val', disabled: 'disabled' }" />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="title" label="标题">
            <el-input v-model="form.title" autocomplete="off" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="permission" label="权限">
            <el-input v-model="form.permission" autocomplete="off" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="icon" label="图标">
            <VtIconPicker v-model="form.icon"></VtIconPicker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="component" label="组件路径">
            <el-input v-model="form.component" autocomplete="off">
              <template #prepend>src/views/</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="routeName" label="路由名称">
            <el-input v-model="form.routeName" autocomplete="off" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="routePath" label="路由路径">
            <el-input v-model="form.routePath" autocomplete="off" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="disabled" label="菜单状态">
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
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" native-type="submit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="warning" @click="resetForm">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="primary" @click="closeDialog">
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
