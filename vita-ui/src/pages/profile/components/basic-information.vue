<script setup>
import { userApi } from "@/api/system/user-api.js";

const loading = ref(true);

const size = ref("default");

const formRef = useTemplateRef("formRef");

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  email: undefined,
  gender: undefined,
  id: undefined,
  mobile: undefined,
  nickname: undefined,
});

const userInfo = ref({});

const init = () => {
  form.id = userInfo.value.id ?? undefined;
  form.nickname = userInfo.value.nickname ?? undefined;
  form.mobile = userInfo.value.mobile ?? undefined;
  form.email = userInfo.value.email ?? undefined;
  form.gender = userInfo.value.gender ?? undefined;
};

const resetForm = () => {
  formRef.value.resetFields();
  init();
};

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    userApi.updateBasicInformation(form);
  });
};

const cropperRef = useTemplateRef("cropperRef");

const openVtCropper = () => {
  cropperRef.value.dataBase64 = "";
  cropperRef.value.visible = true;
};

const setUserAvatar = (base64) => {
  console.log("裁剪后的图片 base64 数据：", base64);
};

onMounted(() => {
  userApi.getLoginUserInfo().then((res) => {
    userInfo.value = res;
    init();
    loading.value = false;
  });
});
</script>

<template>
  <div>
    <el-form-item>
      <el-avatar :src="userInfo?.avatar" :size="150" v-if="userInfo?.avatar" />
      <el-avatar src="/avatar.jpg" :size="150" v-else />
      <el-button type="primary" style="margin-left: 20px;" @click="openVtCropper">
        <template #icon>
          <el-icon>
            <Icon icon="ep:upload-filled"></Icon>
          </el-icon>
        </template>更新图像</el-button>
    </el-form-item>
  </div>
  <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">

    <el-form-item prop="nickname" label="用户昵称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
      <el-input v-model="form.nickname" clearable maxlength="30" autocomplete="off" />
    </el-form-item>

    <el-form-item prop="mobile" label="移动电话" :rules="[{ pattern: /(?:0|86|\+86)?1[3-9]\d{9}/, message: '电话号码格式不正确' }]">
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
      <el-button type="primary" style="width: 160px;" @click="onSubmit">保存</el-button>
      <el-button type="warning" style="width: 160px;" @click="resetForm">
        <template #icon>
          <el-icon>
            <Icon icon="ep:refresh-left"></Icon>
          </el-icon>
        </template>
        重置
      </el-button>
    </el-form-item>
  </el-form>

  <VtCropper ref="cropperRef"></VtCropper>
</template>

<style scoped></style>
