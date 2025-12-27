<script setup>
import { userApi } from '@/api/system/user-api.js';

const qrCode = ref("");

const form = reactive({
  totpCode: '',
});


const formRef = useTemplateRef("formRef");

const onSubmit = () => {
	formRef.value.validate((valid, fields) => {
		if (!valid) {
			// fields 只有在验证失败的情况下才有值
			console.log(fields);
			return;
		}
		userApi.enableTotp(form.totpCode);
	});
};

onMounted( async () => {
  qrCode.value = await userApi.generateTotpQrCodeBase64();
});
</script>

<template>
  <div>
    <div style="text-indent: 2em; line-height: 2;">
      双因素认证（英语：Two-factor authentication，缩写为 2FA），又称两步骤验证（2-Step Verification，又译两步验证），是一种多重安全认证方法。
    </div>
    <div>
      <h3>移动端客户端工具推荐：</h3>
      <ul style="line-height: 2;">
        <li>Microsoft Authenticator（安卓，IOS）</li>
        <li>数盾OTP（微信小程序）</li>
        <li>Aegis (仅安卓端)</li>
      </ul>
    </div>
  </div>
  <div>
    <el-row>
      <el-col :span="8">
        <el-image style="width: 260px; height: 260px" :src="qrCode" :fit="'fill'" />
      </el-col>
      <el-col :span="16">
        <div style="margin-top: 18px;">
          <h4>绑定步骤：</h4>
          <div>
            <ol style="line-height: 2;">
              <li>使用手机客户端（Microsoft Authenticator、数盾OTP、Aegis）扫描二维码；</li>
              <li>把手机客户端的数字验证码填写到下面输入框，然后点击【<strong>绑定验证</strong>】按钮。</li>
            </ol>
          </div>
          <div style="margin-top: 10px;">
            <el-form ref="formRef" :model="form" :inline="true" @submit.prevent="onSubmit">
              <el-form-item prop="totpCode" label="" :rules="[
                { required: true, message: '必填', trigger: 'blur' },
                { type: 'number', message: '验证码只能是数字' },
                ]">
                <el-input v-model.number="form.totpCode" placeholder="请输入验证码" autocomplete="off" clearable />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" native-type="submit">绑定验证</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped></style>
