<script setup>
import { userApi } from "@/api/system/user-api.js";
import { useUserStore } from "@/store/user-store.js";
import utils from "@/utils/utils.js";

const loading = ref(true);

const size = ref("default");

// 直接传对象
const userModel = defineModel("user", {
  default: () => ({}),
  type: Object,
});

const formRef = useTemplateRef("formRef");

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  avatar: undefined,
  email: undefined,
  gender: undefined,
  id: undefined,
  mobile: undefined,
  nickname: undefined,
});

const init = async () => {
  while (true) {
    if (userModel.value?.id) {
      form.id = userModel.value?.id ?? undefined;
      form.nickname = userModel.value?.nickname ?? undefined;
      form.mobile = userModel.value?.mobile ?? undefined;
      form.email = userModel.value?.email ?? undefined;
      form.gender = userModel.value?.gender ?? undefined;
      form.avatar = userModel.value?.avatar ?? "/avatar.jpg";

      loading.value = false;
      break;
    }
    await utils.sleep(500);
  }
};

init();

const resetForm = () => {
  formRef.value.resetFields();
  init();
};

const onSubmit = () => {
  form.avatar = cropperSrc.value || undefined;
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    userApi.updateBasicInformation(form).then(() => {
      userModel.value.nickname = form.nickname;
      userModel.value.mobile = form.mobile;
      userModel.value.email = form.email;
      userModel.value.gender = form.gender;
      userModel.value.id = form.id;
      userModel.value.avatar = form.avatar;

      useUserStore().initUser();
    });
  });
};

const cropperVisible = ref(false);
const cropperSrc = ref("");
const openVtCropper = () => {
  cropperSrc.value = userModel.value?.avatar ?? "/avatar.jpg";
  cropperVisible.value = true;
};

onMounted(() => {});
</script>

<template>
  <div v-loading="loading">
    <div>
      <el-form-item>
        <el-avatar :src="form.avatar" :size="150" />

        <el-button type="primary" style="margin-left: 20px" @click="openVtCropper">
          <template #icon>
            <el-icon>
              <Icon icon="ep:upload-filled"></Icon>
            </el-icon> </template
          >更新图像</el-button
        >
      </el-form-item>
      <VtCropper v-model="form.avatar" v-model:visible="cropperVisible" v-model:src="cropperSrc" />
    </div>
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item
        prop="nickname"
        label="用户昵称"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.nickname" clearable maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-form-item
        prop="mobile"
        label="移动电话"
        :rules="[{ pattern: /(?:0|86|\+86)?1[3-9]\d{9}/, message: '电话号码格式不正确' }]"
      >
        <el-input v-model="form.mobile" clearable maxlength="15" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="email" label="电子邮箱" :rules="[]">
        <el-input v-model="form.email" clearable maxlength="128" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="gender" label="性别">
        <VtSelectDict v-model="form.gender" :code="'vt_user_gender'"></VtSelectDict>
      </el-form-item>

      <!-- 即使不需要标签，也应设置 label=" "（中间必须要有个空格）。以避免出现 ElementPlusError: [ElForm] unexpected width 0 警告。-->
      <el-form-item label=" ">
        <el-button type="primary" style="width: 160px" @click="onSubmit">保存</el-button>
        <el-button type="warning" style="width: 160px" @click="resetForm">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped></style>
