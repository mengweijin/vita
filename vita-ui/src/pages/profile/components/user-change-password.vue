<script setup>
import { loginApi } from "@/api/login-api.js";
import { userApi } from "@/api/system/user-api.js";
import { useLoginStore } from "@/store/login-store.js";

const router = useRouter();
const loginStore = useLoginStore();

const form = reactive({
  confirmPassword: "",
  password: "",
});

const formRef = useTemplateRef("formRef");

const submitForm = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }

    if (form.password !== form.confirmPassword) {
      ElMessage.error({
        message: "输入的两次密码不一致！",
        showClose: true,
      });
      return;
    }

    userApi.changePassword(form).then((r) => {
      if (r.code === 200) {
        // 后端登出
        loginApi.logout().finally(() => {
          // 前端登出
          loginStore.logout();
          // 跳转登录页
          router.push("/login");
        });
      }
    });
  });
};

onMounted(() => {
  userApi.checkSafe();
});
</script>

<template>
  <el-form ref="formRef" :model="form" label-width="auto" style="width: 300px">
    <el-form-item
      prop="password"
      label="新的密码"
      :rules="[
        { required: true, message: '必填', trigger: 'blur' },
        {
          pattern:
            /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/,
          message: '应为8-18位字母、数字、符号至少两种组合',
        },
      ]"
    >
      <el-input
        v-model="form.password"
        maxlength="18"
        clearable
        type="password"
        placeholder="请输入旧密码"
        show-password
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item
      prop="confirmPassword"
      label="确认密码"
      :rules="[
        { required: true, message: '必填', trigger: 'blur' },
        {
          pattern:
            /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/,
          message: '应为8-18位字母、数字、符号至少两种组合',
        },
      ]"
    >
      <el-input
        v-model="form.confirmPassword"
        maxlength="18"
        clearable
        type="password"
        placeholder="请输入确认密码"
        show-password
        autocomplete="off"
      />
    </el-form-item>

    <el-form-item label=" ">
      <el-button type="primary" @click="submitForm()" style="width: 100%">确认</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped></style>
