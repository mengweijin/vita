<script setup>
import { userApi } from '@/api/system/user-api.js';

const form = reactive({
  code: '',
  key: '',
});


const formRef = useTemplateRef("formRef");

const onSubmit = () => {
  form.key = totpVO.value.key;
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    userApi.saveTotp(form).then((r) => {
      if(r.code === 200) {
        hasTotpKey.value = true;
      }
    }).catch(() => {});
  });
};

const rebindTotp = () => {
  ElMessageBox.prompt('请输入动态口令', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    inputPattern: /^\d{6}$/,
    inputErrorMessage: '验证码必须是 6 位数字',
    beforeClose: async (action, instance, done) => {
      if (action === 'confirm') {
        const inputValue = instance.inputValue;
        instance.confirmButtonLoading = true;
        instance.confirmButtonText = '验证中...';
        const bool = await userApi.validateTotpCode(inputValue).catch(() => {
          instance.confirmButtonText = '确认';
          instance.confirmButtonLoading = false;
        });
        if(bool) {
          hasTotpKey.value = false;
          done();
        }
      } else {
        done();
      }
    },
  });
}

const hasTotpKey = ref(false);

const totpVO = ref({});

onMounted(async () => {
  hasTotpKey.value = await userApi.hasTotpKey();
  totpVO.value = await userApi.generateTotpQrcode();
});
</script>

<template>
  <div>
    <div style="text-indent: 2em; line-height: 2;">
      双因素认证（英语：Two-factor authentication，缩写为 2FA），又称两步骤验证（2-Step Verification，又译两步验证）。
    </div>
    <div>
      <h3>移动端客户端工具推荐：</h3>
      <ul style="line-height: 2;">
        <li>Microsoft Authenticator（安卓，IOS）</li>
        <li>数盾OTP（微信小程序）</li>
      </ul>
    </div>
  </div>

  <div v-if="hasTotpKey">
    <div style="margin-top: 10px; font-size: 18px; font-weight: bold;">
      <span>当前用户已绑定动态口令。</span>
      <el-button type="primary" @click="rebindTotp">重新绑定</el-button>
    </div>
  </div>
  <div v-else>
    <el-row>
      <el-col :span="9">
        <el-image style="width: 300px; height: 300px" :src="totpVO.qrcode" :fit="'fill'" alt="已失效"/>
        <div style="margin-left: 20px; margin-top: -5px;">{{ totpVO.key }}</div>
      </el-col>
      <el-col :span="15">
        <div style="margin-top: 18px;">
          <h4>绑定步骤：</h4>
          <div>
            <ol style="line-height: 2;">
              <li>使用手机客户端（Microsoft Authenticator、数盾OTP）扫描二维码；</li>
              <li>输入生成的数字验证码，点击【<strong>绑定验证</strong>】按钮。</li>
            </ol>
          </div>
          <div style="margin-top: 10px;">
            <el-form ref="formRef" :model="form" :inline="true">
              <el-form-item prop="code" label="" :rules="[
                { required: true, message: '必填', trigger: 'blur' },
                { type: 'number', message: '动态口令只能是数字' },
              ]">
                <el-input v-model.number="form.code" placeholder="请输入动态口令" autocomplete="off" clearable />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">绑定验证</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped></style>
