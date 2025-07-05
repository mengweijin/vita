<script setup>
import { configApi } from '@/api/system/config-api';
import { userApi } from "@/api/system/user-api";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  username: undefined,
  password: undefined,
});

const init = () => {
  form.username = data.value.username ?? undefined;
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    userApi.resetPassword(form).then((r) => {
      onClosed();
    });
  });
}

const isResetToDefaultPassword = ref(true);

watch(isResetToDefaultPassword, (newData) => {
  if (newData) {
    form.password = defaultPassword.value;
  } else {
    form.password = null;
  }
});

const defaultPassword = ref(null);

const initDefaultPassword = () => {
  configApi.getByCode('vt_user_password_default').then((res) => {
    defaultPassword.value = res?.val ?? undefined;
    if (isResetToDefaultPassword.value) {
      form.password = defaultPassword.value;
    }
  });
};

const onOpened = () => {
  loading.value = true;
  init();
  initDefaultPassword();
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
  <el-dialog v-model="visible" :title="`用户【${data.nickname}】重置密码`" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item style="margin-top: 5px;">
        <el-checkbox v-model="isResetToDefaultPassword">是否重置为系统默认密码</el-checkbox>
      </el-form-item>

      <el-form-item prop="password" label="新密码" :rules="[
        { required: true, message: '必填', trigger: 'blur' },
        { pattern: /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/, message: '应为8-18位字母、数字、符号至少两种组合' },
      ]">
        <el-input v-model="form.password" maxlength="18" :readonly="isResetToDefaultPassword" clearable type="password"
          placeholder="请输入旧密码" show-password autocomplete="off" />
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
