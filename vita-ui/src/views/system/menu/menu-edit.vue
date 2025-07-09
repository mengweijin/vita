<script setup>
import { addFullPath } from "@/utils/tool";
import { menuApi } from "@/api/system/menu-api";
import { toArrayTree } from "xe-utils";
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  id: undefined,
  parentId: undefined,
  type: undefined,
  title: undefined,
  icon: undefined,
  permission: undefined,
  component: undefined,
  routeName: undefined,
  routePath: undefined,
  url: undefined,
  seq: undefined,
  disabled: undefined,
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.parentId = data.value.parentId ?? undefined;
  form.type = data.value.type ?? 'BTN';
  form.title = data.value.title ?? undefined;
  form.icon = data.value.icon ?? undefined;
  form.permission = data.value.permission ?? undefined;
  form.component = data.value.component ?? undefined;
  form.routeName = data.value.routeName ?? undefined;
  form.routePath = data.value.routePath ?? undefined;
  form.url = data.value.url ?? undefined;
  form.seq = data.value.seq ?? 1;
  form.disabled = data.value.disabled ?? 'N';
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
      menuApi.update(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    } else {
      menuApi.create(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const menuTypeOptions = computed(() => {
  const menuTypes = dictStore.get('vt_menu_type');
  return menuTypes.map(item => {
    item.disabled = (item.disabled === 'Y' ? true : false);
    return item;
  });
});

const menuList = ref([]);

const menuTreeSelectOptions = computed(() => {
  menuList.value.forEach((item) => item.disabled = false);
  addFullPath(menuList.value, { pathKey: 'title' })
  return toArrayTree(menuList.value, { sortKey: 'seq' });
});

const onOpened = () => {
  loading.value = true;
  menuApi.list().then((res) => {
    menuList.value = res;
    init();
    nextTick(() => {
      loading.value = false;
    });

  });
}

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
}

/** 暴露给父组件，父组件可通过 menuEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="data?.id ? '编辑' : '新增'" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="type" label="菜单类型">
        <el-segmented v-model="form.type" :options="menuTypeOptions"
          :props="{ label: 'label', value: 'val', disabled: 'disabled' }" />
      </el-form-item>

      <el-form-item prop="parentId" label="父菜单">
        <el-tree-select v-model="form.parentId" :data="menuTreeSelectOptions"
          :props="{ label: 'titleFullPath', value: 'id', children: 'children' }" check-strictly filterable clearable
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
        <el-input v-model="form.title" clearable maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="permission">
        <template #label>
          <div class="vt-question-icon-container">
            <el-tooltip placement="top" content="权限格式：以冒号分隔，比如：system:user:view">
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
            <span>权限</span>
          </div>
        </template>
        <el-input v-model="form.permission" clearable autocomplete="off" />
      </el-form-item>

      <el-form-item prop="component" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'MENU'">
        <template #label>
          <div class="vt-question-icon-container">
            <el-tooltip placement="top" content="*.vue 组件文件的路径。比如：src/views/system/menu/menu-list.vue">
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
            <span>组件路径</span>
          </div>
        </template>
        <el-input v-model="form.component" clearable autocomplete="off">
          <template #prepend>src/views/</template>
        </el-input>
      </el-form-item>

      <el-form-item prop="routePath" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'DIR' || form.type === 'MENU' || form.type === 'IFRAME'">
        <template #label>
          <div class="vt-question-icon-container">
            <el-tooltip placement="top" content="vue-router 路由的路径，也是浏览器地址栏访问的路径。比如：/system/menu">
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
            <span>路由路径</span>
          </div>
        </template>
        <el-input v-model="form.routePath" clearable autocomplete="off" />
      </el-form-item>

      <el-form-item prop="routeName" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'DIR' || form.type === 'MENU' || form.type === 'IFRAME'">
        <template #label>
          <div class="vt-question-icon-container">
            <el-tooltip placement="top" content="vue-router 路由的名称。比如：SystemMenu">
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
            <span>路由名称</span>
          </div>
        </template>
        <el-input v-model="form.routeName" clearable autocomplete="off" />
      </el-form-item>

      <el-form-item prop="url" label="URL" :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.type === 'IFRAME' || form.type === 'URL'">
        <el-input v-model="form.url" clearable autocomplete="off"></el-input>
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
