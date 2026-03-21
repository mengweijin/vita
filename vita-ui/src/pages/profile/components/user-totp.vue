<script setup>
import { userApi } from '@/api/system/user-api.js';

const form = reactive({});

const formRef = useTemplateRef("formRef");

const onValidate = () => {
  ElMessageBox.prompt('请输入动态口令', '提示', {
    beforeClose: async (action, instance, done) => {
      if (action === 'confirm') {
        const inputValue = instance.inputValue;
        instance.confirmButtonLoading = true;
        instance.confirmButtonText = '验证中...';
        const bool = await userApi.validateTotpCode(inputValue).catch(() => {
          instance.confirmButtonText = '确认';
          instance.confirmButtonLoading = false;
        });
        if (bool) {
          done();
          ElMessage.success({
            duration: 5000,
            message: "动态口令验证通过。",
            showClose: true,
          });
        }
      } else {
        done();
      }
    },
    cancelButtonText: '取消',
    confirmButtonText: '确认',
    inputErrorMessage: '验证码必须是 6 位数字',
    inputPattern: /^\d{6}$/,
  });
};

const bindTotp = async () => {
  totpVO.value = await userApi.generateTotpQrcode();
}

const totpVO = ref({
  key: null,
  qrcode: null
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

  <div>
    <el-row>
      <el-col :span="15">
        <div style="margin-top: 18px;">
          <h4>绑定步骤：</h4>
          <div>
            <ol style="line-height: 2;">
              <li>点击【<strong>显示二维码</strong>】按钮，进行二级认证，认证通过后，再次点击【<strong>显示二维码</strong>】按钮；</li>
              <li>使用手机客户端（Microsoft Authenticator、数盾OTP）扫描二维码；</li>
              <li>输入生成的数字验证码，点击【<strong>验证</strong>】按钮。</li>
            </ol>
          </div>
          <div style="margin-top: 10px; margin-left: 30px;">
            <el-form ref="formRef" :model="form" :inline="true">
              <el-form-item>
                <el-button type="primary" @click="bindTotp">显示二维码</el-button>
                <el-button type="warning" @click="onValidate">验证</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-col>
      <el-col :span="9" style="height: 350px;">
        <el-image v-if="totpVO.qrcode" style="width: 300px; height: 300px" :src="totpVO.qrcode" :fit="'fill'"
          alt="已失效" />
        <div v-if="totpVO.key" style="margin-left: 20px; margin-top: -5px;">{{ totpVO.key }}</div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped></style>
