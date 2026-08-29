<route lang="yaml">
meta:
  layout: none
  title: 登录
</route>

<script setup>
import { loginApi } from "@/api/login-api.js";
import LayFooter from "@/layout/lay-footer.vue";

const router = useRouter();
const route = useRoute();

import { useLoginStore } from "@/store/login-store.js";

const loginStore = useLoginStore();

import FingerprintJS from "@fingerprintjs/fingerprintjs";

const loading = ref(false);

const visible = ref(false);

const form = reactive({
  captcha: "",
  deviceId: undefined,
  password: "aday.fun",
  remember: false,
  username: "admin",
});

const formRef = useTemplateRef("formRef");

const rules = reactive({
  username: [
    { message: "必填", required: true, trigger: "blur" },
    { max: 30, message: "长度需要在 3-30 个字符之间", min: 3 },
  ],
});

const captchaEnabled = ref(false);
const captchaImg = ref(null);

const initCaptcha = async () => {
  captchaEnabled.value = await loginApi.getCaptchaEnabled();
  if (captchaEnabled.value) {
    await onRefreshCaptcha();
  }
};

const onRefreshCaptcha = async () => {
  captchaImg.value = await loginApi.getCaptcha();
};

const onForgetPassword = () => {
  ElMessageBox.alert("请联系管理员！", "忘记密码？", {
    confirmButtonText: "确定",
  });
};

const onSubmit = () => {
  // 这里手动 loading
  const loading = ElLoading.service({ fullscreen: true });
  formRef.value.validate((valid, fields) => {
    if (valid) {
      // loginApi.login() 方法中已经关闭了 http.js 中的全局 loading
      loginApi
        .login(form)
        .then(async (r) => {
          // 保存 token
          loginStore.setToken(r.data.token);
          if (form.remember) {
            // 记住我，后端默认保存 7 天
            loginStore.setLocalStorageToken(r.data.token);
          } else {
            // 不记住我，关闭浏览器即失效。并清理掉 localStorage 中之前存储的 token
            loginStore.removeLocalStorageToken();
          }
          // 初始化用户数据
          await loginStore.initData();

          // 跳转到访问页或首页
          router.push(route.query.redirect || "/");

          loading?.close();
        })
        .catch(() => {
          loading?.close();
        });
    } else {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      loading?.close();
    }
  });
};

const onkeypress = ({ code }) => {
  if (["Enter", "NumpadEnter"].includes(code)) {
    onSubmit();
  }
};

const initVisitorId = async () => {
  // 初始化指纹库
  const fp = await FingerprintJS.load();
  // 生成浏览器指纹
  const result = await fp.get();
  form.deviceId = result.visitorId;
};

onMounted(async () => {
  loading.value = true;
  await initCaptcha();
  await initVisitorId();
  window.document.addEventListener("keypress", onkeypress);
  visible.value = true;
  loading.value = false;
});

onBeforeUnmount(() => {
  window.document.removeEventListener("keypress", onkeypress);
});
</script>

<template>
  <el-container v-loading="loading" v-show="visible">
    <el-main>
      <div class="vt-login-background">
        <el-form :model="form" :rules="rules" ref="formRef" :size="'large'" class="vt-login-form">
          <el-form-item>
            <div style="width: 100%; text-align: center; padding: 10px">
              <img src="/images/logo.svg" />
            </div>
          </el-form-item>
          <el-form-item style="margin-top: -25px">
            <div class="vt-login-title">
              <img src="/webp/favicon.webp" style="width: 64px" />&nbsp;微塔管理系统
            </div>
          </el-form-item>
          <el-form-item prop="username" style="margin-top: 0px">
            <el-input v-model="form.username" maxlength="30" clearable placeholder="请输入用户名">
              <template #prefix>
                <el-icon :size="22">
                  <Icon icon="ep:user" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item
            prop="password"
            :rules="[
              { required: true, message: '必填', trigger: 'blur' },
              {
                pattern:
                  /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/,
                message: '密码应为8-18位字母、数字、符号至少两种组合',
              },
            ]"
          >
            <el-input
              v-model="form.password"
              maxlength="18"
              clearable
              type="password"
              placeholder="请输入密码"
              show-password
            >
              <template #prefix>
                <el-icon :size="22">
                  <Icon icon="ep:lock" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item
            prop="captcha"
            v-if="captchaEnabled"
            :rules="[
              { required: true, message: '必填', trigger: 'blur' },
              { pattern: /^\d+$/, message: '验证码应为数字' },
            ]"
          >
            <el-input v-model="form.captcha" maxlength="30" clearable placeholder="验证码">
              <template #prepend>
                <a href="javascript:;" class="vt-login-captcha" @click="onRefreshCaptcha"
                  ><img :src="captchaImg"
                /></a>
              </template>
              <template #prefix>
                <el-icon :size="22">
                  <Icon icon="ri:shield-keyhole-line" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item style="margin-top: 5px">
            <el-checkbox v-model="form.remember">记住我</el-checkbox>
            <a
              href="javascript:;"
              style="position: absolute; right: 0; text-decoration: none"
              @click="onForgetPassword"
              >忘记密码？</a
            >
          </el-form-item>
          <el-form-item style="margin-top: 5px">
            <el-button type="primary" style="width: 100%" @click="onSubmit">登录</el-button>
          </el-form-item>
          <el-form-item style="margin-top: 5px">
            <div>登录账号：</div>
            <div>admin（管理员）</div>
            <div>vita（普通用户）</div>
          </el-form-item>
        </el-form>
      </div>
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

.vt-login-form {
  margin-top: -150px;
}

.vt-login-title {
  width: 100%;
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  color: var(--vt-primary-color);
}

.vt-login-captcha {
  display: flex;
  justify-content: center;
  align-items: center;
  align-content: center;
  padding: 2px;
  margin-left: -20px;
  margin-right: -20px;
  width: 140px;
}

.el-form-item + .el-form-item {
  margin-top: 15px;
}

.el-form-item--large {
  margin-bottom: 0px;
}

.el-main {
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - var(--vt-footer-height));
  min-height: calc(100vh - var(--vt-footer-height));
  background-color: #f7f7f7;
}

/* 全屏固定容器，无视 body 边距，完美覆盖视口 */
.vt-login-background {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("/webp/background.webp");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  font-family: "Segoe UI", "Roboto", "Helvetica Neue", sans-serif;
  z-index: 0;
}

.el-form {
  width: 340px;
  max-width: 90%;
}

.el-footer {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
  /* background-color: #eeeeee; */
  height: var(--vt-footer-height);
  z-index: 0;
}
</style>
