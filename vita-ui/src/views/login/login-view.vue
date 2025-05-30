<script setup>
import LayFooter from "@/layout/lay-footer.vue";

import { loginApi } from '@/api/login-api';
import router from '@/router/index';

import { useUserStore } from '@/store/user-store';
const userStore = useUserStore();

import { useDictStore } from "@/store/dict-store";
const dictStore = useDictStore();

const visible = ref(false);

const form = reactive({
  username: 'admin',
  password: 'aday.fun',
  otp: '',
  captcha: '',
  remember: [],
  loginMethod: 'password',
})

const formRef = ref(null);

const rules = reactive({
  username: [
    { required: true, message: '必填', trigger: 'blur' },
    { min: 3, max: 30, message: '长度需要在 3-30 个字符之间' }
  ],
});

const captchaEnabled = ref(false);
const captchaImg = ref(null);

const initCaptcha = async () => {
  captchaEnabled.value = await loginApi.getCaptchaEnabled();
  if (captchaEnabled.value) {
    await onRefreshCaptcha();
  }
  visible.value = true;
}

const onRefreshCaptcha = async () => {
  captchaImg.value = await loginApi.getCaptcha();
};

const onForgetPassword = () => {
  ElMessageBox.alert('请联系系统管理员！', '忘记密码？', {
    confirmButtonText: 'OK',
  })
};

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (valid) {
      loginApi.login(form).then((r) => {
        // 保存用户和token 到 userStore
        userStore.initUser(r.data);
        // 加载字典
        dictStore.initDicts();
        router.push('/');
      });
    } else {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
    }
  });
};

const onkeypress = ({ code }) => {
  if (["Enter", "NumpadEnter"].includes(code)) { onSubmit(); }
}

onBeforeMount(() => {
  initCaptcha();
});

onMounted(() => {
  window.document.addEventListener("keypress", onkeypress);
});

onBeforeUnmount(() => {
  window.document.removeEventListener("keypress", onkeypress);
});
</script>

<template>
  <el-container v-show="visible">
    <el-main>
      <el-form :model="form" :rules="rules" ref="formRef" :size="'large'">
        <el-form-item>
          <div style="width: 100%;text-align: center;"><img src="/logo.svg" /></div>
        </el-form-item>
        <el-form-item style="margin-top: -15px;">
          <div class="vt-login-title">Vita（微塔）管理系统</div>
        </el-form-item>
        <el-form-item prop="username" style="margin-top: 0px;">
          <el-input v-model="form.username" maxlength="30" clearable placeholder="请输入用户名">
            <template #prefix>
              <el-icon :size="22">
                <Icon icon="ep:user" />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" v-if="form.loginMethod === 'password'" :rules="[
          { required: true, message: '必填', trigger: 'blur' },
          { pattern: /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/, message: '密码应在8-18位之间数字、字母、符号的至少任意两种的组合' }
        ]">
          <el-input v-model="form.password" maxlength="30" clearable type="password" placeholder="请输入密码" show-password>
            <template #prefix>
              <el-icon :size="22">
                <Icon icon="ep:lock" />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="otp" v-if="form.loginMethod === 'otp'" :rules="[
          { required: true, message: '必填', trigger: 'blur' },
          { pattern: /^\d{4}$/, message: '口令应为 4 位数字' }
        ]">
          <el-input v-model="form.otp" maxlength="4" clearable placeholder="请输入动态口令" show-password>
            <template #prefix>
              <el-icon :size="22">
                <Icon icon="ri:lock-password-line" />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="captcha" v-if="captchaEnabled" :rules="[
          { required: true, message: '必填', trigger: 'blur' },
          { pattern: /^-*\d+$/, message: '验证码应为数字' }
        ]">
          <el-input v-model="form.captcha" maxlength="30" clearable placeholder="验证码">
            <template #prefix>
              <el-icon :size="22">
                <Icon icon="ri:shield-keyhole-line" />
              </el-icon>
            </template>
            <template #append>
              <a href="javascript:;" class="vt-login-captcha" @click="onRefreshCaptcha"><img :src="captchaImg" /></a>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item style="margin-top: 5px;">
          <el-checkbox-group v-model="form.remember">
            <el-checkbox value="Y" name="remember">记住密码</el-checkbox>
          </el-checkbox-group>
          <a href="javascript:;" style="position: absolute; right: 0;text-decoration: none;"
            @click="onForgetPassword">忘记密码？</a>
        </el-form-item>
        <el-form-item label="登录方式" style="margin-top: -5px;">
          <el-radio-group v-model="form.loginMethod">
            <el-radio value="password">密码</el-radio>
            <el-radio value="otp">动态口令</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-top: 8px;">
          <el-button type="primary" style="width: 100%;" @click="onSubmit">登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer>
      <LayFooter />
    </el-footer>
  </el-container>
</template>

<style scoped>
/* 移动端样式 */
@media screen and (max-width: 768px) {
  .vt-login {
    height: calc(100% - 74px);
  }
}

/* PC端样式 */
@media screen and (min-width: 768px) {
  .vt-login {
    height: calc(100% - 34px);
  }
}

.vt-login-title {
  width: 100%;
  text-align: center;
  font-size: 22px;
  font-weight: bold;
  color: var(--vt-primary-color);
}

.vt-login-captcha {
  display: flex;
  justify-content: center;
  align-items: center;
  align-content: center;
  padding: 3px;
  width: 140px;
}

:deep(.el-input-group__append:has(.vt-login-captcha)) {
  padding: 0;
  background-color: white;
}

.el-form-item+.el-form-item {
  margin-top: 15px;
}

.el-form-item--large {
  margin-bottom: 0px;
}

:deep(.el-main) {
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - var(--vt-footer-height));
  min-height: calc(100vh - var(--vt-footer-height));
  background-color: #f7f7f7;
}

:deep(.el-footer) {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
  background-color: #eeeeee;
  height: var(--vt-footer-height);
}
</style>
