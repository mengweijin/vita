<script setup>
import { userApi } from "@/api/system/user-api.js";
import { useSecondaryAuthStore } from "@/store/secondary-auth-store.js";

const secondaryAuthStore = useSecondaryAuthStore();
const { secondaryAuthDialogVisible } = storeToRefs(secondaryAuthStore);

const loading = ref(false);

const formRef = useTemplateRef("formRef");

const form = reactive({
  safeMode: "PASSWORD",
  value: undefined,
});

const onSafeModeChange = (val) => {
  form.value = null;
};

const onClosed = () => {
  secondaryAuthDialogVisible.value = false;
  loading.value = false;
};

const onSubmit = () => {
  loading.value = true;
  userApi
    .secondaryAuth(form)
    .then(() => {
      onClosed();
      ElMessage.success({
        duration: 5000,
        message: "二级认证成功！请继续你之前的操作。",
        showClose: true,
      });
    })
    .finally(() => {
      loading.value = false;
    });
};
</script>

<template>
  <el-dialog
    :model-value="secondaryAuthDialogVisible"
    :title="'二级认证'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="500px"
    style="height: 210px"
  >
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="80px">
      <el-form-item prop="safeMode" label="认证模式">
        <VtRadioDict :code="'vt_safe_mode'" v-model="form.safeMode" @change="onSafeModeChange" />
      </el-form-item>
      <el-form-item
        v-if="form.safeMode === 'TOTP'"
        prop="value"
        :label="'动态口令'"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input-otp v-model="form.value" />
      </el-form-item>
      <el-form-item
        v-else
        prop="value"
        :label="'用户密码'"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input
          v-model="form.value"
          clearable
          show-password
          type="password"
          maxlength="18"
          autocomplete="off"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" :disabled="loading" @click="onSubmit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="info" :disabled="loading" @click="onClosed">
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
