<script setup>
import { Icon } from '@iconify/vue';
import LayFooter from "@/layout/LayFooter.vue";
import { reactive } from 'vue';
import { login } from '@/api/login';
import router from '@/router/index.js'
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore.js'
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const form = reactive({
  username: 'admin',
  password: 'aday.fun',
  remember: [],
  loginMethod: 'password',
})

const onSubmit = () => {
  login(form).then((r) => {
    user.value = r.data;
    router.push('/');
  });
}
</script>

<template>
  <el-container>
    <el-main>
      <div class="vt-login-container">
        <el-form :model="form" label-width="auto" style="max-width: 600px">
          <el-form-item>
            <div style="width: 100%;text-align: center;"><img src="/logo.svg" /></div>
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.username" maxlength="30" clearable placeholder="请输入用户名">
              <template #prefix>
                <Icon icon="ep:user-filled" width="20" height="20" />
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.password" maxlength="30" clearable type="password" placeholder="请输入密码"
              show-password>
              <template #prefix>
                <Icon icon="ep:lock" width="20" height="20" />
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-checkbox-group v-model="form.remember">
              <el-checkbox value="Y" name="remember">记住密码</el-checkbox>
            </el-checkbox-group>
            <a href="javascript:;" style="position: absolute; right: 0;text-decoration: none;">忘记密码？</a>
          </el-form-item>
          <el-form-item label="登录方式">
            <el-radio-group v-model="form.loginMethod">
              <el-radio value="password">密码</el-radio>
              <el-radio value="totp">动态口令</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" autofocus @click="onSubmit" style="width: 100%;">登录</el-button>
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
.vt-login-container {
  height: calc(100vh - var(--vt-footer-height));
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
}

.vt-form-item {
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
}

:deep(.el-main) {
  min-height: calc(100vh - var(--vt-footer-height));
  padding: 0;
}

:deep(.el-footer) {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
}
</style>
