<script setup>
import { roleApi } from '@/api/system/role-api';
import { userApi } from "@/api/system/user-api";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  userId: undefined,
  roleIds: [],
});

const init = () => {
  form.userId = data.value.id ?? undefined;
  form.roleIds = data.value?.roleIds ?? [];
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    userApi.setRoles(form).then((r) => {
      onClosed();
    });
  });
}

const roleList = ref([]);

const initRoleList = async () => {
  roleList.value = await roleApi.list({ disabled: 'N' });
}


const initSensitiveInfo = async () => {
  let user = await userApi.getSensitiveUserById(data.value.id);
  form.roleIds = user.roleIds;
};

const onOpened = async () => {
  loading.value = true;
  await initRoleList();
  init();
  await initSensitiveInfo();
  loading.value = false;
}

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="`用户【${data.nickname}】设置角色`" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="roleIds" label="选中角色" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-select v-model="form.roleIds" clearable filterable multiple placeholder="请选择">
          <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id"
            :disabled="item.disabled === 'Y'" />
        </el-select>
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
        <el-button type="warning" @click="init" v-if="false">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="danger" @click="onClosed">
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
