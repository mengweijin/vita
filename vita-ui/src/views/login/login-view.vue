<script setup>
import LayFooter from "@/layout/lay-footer.vue";

import { loginApi } from '@/api/login-api';
import router from '@/router/index';
import { useRoute } from 'vue-router';
const route = useRoute();

import { useUserStore } from '@/store/user-store';
const userStore = useUserStore();

import { useDictStore } from "@/store/dict-store";
const dictStore = useDictStore();

import { useMenuStore } from '@/store/menu-store';
const menuStore = useMenuStore();

const visible = ref(false);

const form = reactive({
  username: 'admin',
  password: 'aday.fun',
  otp: '',
  captcha: '',
  remember: false,
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
  ElMessageBox.alert('请联系管理员！', '忘记密码？', {
    confirmButtonText: '确定',
  })
};

const onSubmit = () => {

  formRef.value.validate((valid, fields) => {
    if (valid) {


      // loginApi.login() 方法中已经关闭了 http.js 中的全局 loading
      loginApi.login(form).then(async (r) => {
        // 这里手动 loading
        let loading = ElLoading.service({ fullscreen: true });

        // 先保存 token 到 userStore，并初始化用户基本信息、角色、权限等
        await userStore.initUser(r.data.token);
        // 加载菜单动态路由
        await menuStore.refresh();
        // 加载字典
        await dictStore.refresh();
        // 跳转到访问页或首页
        router.push(route.query.redirect || '/');

        loading?.close();
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

const optEnabled = ref(false);

const initLoginOtpEnabled = () => {
  loginApi.getLoginOtpEnabled().then((res) => {
    optEnabled.value = res;
  });
}

onBeforeMount(() => {
  initCaptcha();
  initLoginOtpEnabled();
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
          <div style="width: 100%;text-align: center; padding: 10px;"><img src="/logo.svg" /></div>
        </el-form-item>
        <el-form-item style="margin-top: -15px;">
          <div class="vt-login-title">微塔（Vita）管理系统</div>
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
        <el-form-item prop="password" :rules="[
          { required: true, message: '必填', trigger: 'blur' },
          { pattern: /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/, message: '密码应为8-18位字母、数字、符号至少两种组合' }
        ]">
          <el-input v-model="form.password" maxlength="18" clearable type="password" placeholder="请输入密码" show-password>
            <template #prefix>
              <el-icon :size="22">
                <Icon icon="ep:lock" />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="otp" v-if="optEnabled" :rules="[
          { required: true, message: '必填', trigger: 'blur' },
          { pattern: /^\d{4}$/, message: '口令应为 4 位数字' }
        ]">
          <el-input v-model="form.otp" maxlength="4" clearable placeholder="请输入动态口令">
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
          <el-checkbox v-model="form.remember">记住我</el-checkbox>
          <a href="javascript:;" style="position: absolute; right: 0;text-decoration: none;"
            @click="onForgetPassword">忘记密码？</a>
        </el-form-item>
        <el-form-item style="margin-top: 5px;">
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
