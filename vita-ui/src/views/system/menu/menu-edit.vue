<script setup>
import { copyDefinedProperties } from "@/utils/tool";
import VtIconPicker from "@/components/modules/vt-icon-picker.vue";
import { menuApi } from "@/api/system/menu-api";
import { toArrayTree, find } from "xe-utils";
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则二次打开可能就打不开了 */
const form = reactive({
  id: undefined,
  parentId: undefined,
  type: 'BTN',
  title: undefined,
  icon: undefined,
  permission: undefined,
  component: undefined,
  routeName: undefined,
  routePath: undefined,
  url: undefined,
  seq: 1,
  disabled: 'N',
});

const onDialogOpen = () => {
  copyDefinedProperties(form, data.value);
}

const formRef = ref(null);

const resetForm = () => {
  formRef.value.resetFields();
  onDialogOpen();
};

const title = computed(() => {
  return data?.id ? '编辑' : '新增';
});

const onSubmit = () => {

}

const closeDialog = () => {
  formRef.value.resetFields();
  visible.value = false;
};

const menuTypeOptions = computed(() => {
  const menuTypes = dictStore.getDicts('vt_menu_type');
  return menuTypes.map(item => {
    item.disabled = (item.disabled === 'Y' ? true : false);
    return item;
  });
});

const menuTreeSelectOptions = ref([]);

const initMenuTreeSelectOptions = async () => {
  let menuList = await menuApi.list();
  menuList = menuList.map(item => {
    item.disabled = false;
    return item;
  });
  let treeData = toArrayTree(menuList, { sortKey: 'seq' });
  menuTreeSelectOptions.value = treeData;
}

onMounted(() => {
  initMenuTreeSelectOptions();
});

/** 暴露给父组件，父组件可通过 menuEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="title" destroy-on-close align-center @opened="onDialogOpen" width="40%">
    <el-form ref="formRef" :model="form" label-width="auto" @submit.prevent="onSubmit">
      <el-form-item prop="type" label="菜单类型">
        <el-segmented v-model="form.type" :options="menuTypeOptions"
          :props="{ label: 'label', value: 'val', disabled: 'disabled' }" />
      </el-form-item>

      <el-form-item prop="parentId" label="父菜单">
        <el-tree-select v-model="form.parentId" :data="menuTreeSelectOptions"
          :props="{ label: 'titlePath', value: 'id', children: 'children' }" check-strictly filterable clearable
          placeholder="请选择">
          <template #default="{ data: { title } }">
            {{ title }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-form-item prop="icon" label="图标" v-if="form.type === 'DIR' || form.type === 'MENU'">
        <VtIconPicker v-model="form.icon"></VtIconPicker>
      </el-form-item>

      <el-form-item prop="title" label="标题" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.title" maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="permission" label="权限">
        <el-input v-model="form.permission" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="component" label="组件路径" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'MENU'">
        <el-input v-model="form.component" autocomplete="off">
          <template #prepend>src/views/</template>
        </el-input>
      </el-form-item>

      <el-form-item prop="routePath" label="路由路径" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'DIR' || form.type === 'MENU' || form.type === 'IFRAME'">
        <el-input v-model="form.routePath" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="routeName" label="路由名称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'DIR' || form.type === 'MENU' || form.type === 'IFRAME'">
        <el-input v-model="form.routeName" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="url" label="URL" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'IFRAME' || form.type === 'URL'">
        <el-input v-model="form.url" autocomplete="off"></el-input>
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
