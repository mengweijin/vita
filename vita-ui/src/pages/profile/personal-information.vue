<route lang="yaml">
meta:
  title: 个人信息
</route>

<script setup>
import { userApi } from "@/api/system/user-api";
import UserChangePassword from "@/pages/profile/components/user-change-password.vue";
import UserOnlineTerminal from "@/pages/profile/components/user-online-terminal.vue";
import UserTotp from "@/pages/profile/components/user-totp.vue";
import BasicInformation from "./components/basic-information.vue";
import UserSecurityLog from "./components/user-security-log.vue";

const loading = ref(true);

const size = ref("default");

const userInfo = ref({});

onMounted(() => {
  loading.value = true;
  userApi
    .getLoginUserInfo()
    .then((res) => {
      userInfo.value = res;
      loading.value = false;
    })
    .catch(() => {
      loading.value = false;
    });
});
</script>

<template>
  <div v-loading="loading" class="vt-height">
    <el-scrollbar>
      <el-container>
        <el-aside width="260px">
          <el-descriptions title="" :column="1" :label-width="80" :size="size" border>
            <el-descriptions-item label="头像" label-align="right" width="50" span="2">
              <el-avatar :src="userInfo?.avatar" size="large" v-if="userInfo?.avatar" />
              <el-avatar src="/avatar.jpg" size="large" v-else />
            </el-descriptions-item>
            <el-descriptions-item label="昵称" label-align="right">
              {{ userInfo?.nickname }}
            </el-descriptions-item>
            <el-descriptions-item label="用户名" label-align="right">
              {{ userInfo?.username }}
            </el-descriptions-item>
            <el-descriptions-item label="部门" label-align="right">
              {{ userInfo?.deptName }}
            </el-descriptions-item>
            <el-descriptions-item label="性别" label-align="right">
              <VtTagDict :code="'vt_user_gender'" :value="userInfo?.gender" :size="size"></VtTagDict>
            </el-descriptions-item>
            <el-descriptions-item label="邮箱" label-align="right">
              {{ userInfo?.email }}
            </el-descriptions-item>
            <el-descriptions-item label="手机号" label-align="right">
              {{ userInfo?.mobile }}
            </el-descriptions-item>
            <el-descriptions-item label="用户状态" label-align="right">
              <VtTagDict :code="'vt_disabled'" :value="userInfo?.disabled" :size="size"></VtTagDict>
            </el-descriptions-item>
            <el-descriptions-item label="角色" label-align="right">
              <template v-for="(item, index) in userInfo?.roleList" el-tag :key="item.id">
                <el-tag :size="size" :index="index" :type="'primary'" effect="dark">
                  {{ item.name }}
                </el-tag>
              </template>
            </el-descriptions-item>
            <el-descriptions-item label="岗位" label-align="right">
              <template v-for="(item, index) in userInfo?.postList" el-tag :key="item.id">
                <el-tag :size="size" :index="index" :type="'info'" effect="dark">
                  {{ item.name }}
                </el-tag>
              </template>
            </el-descriptions-item>
            <el-descriptions-item label="备注" label-align="right">
              <div style="white-space: pre-wrap;">
                {{ userInfo?.remark }}
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-aside>
        <el-main>
          <el-tabs type="border-card">
            <el-tab-pane label="基本资料" :lazy="true">
              <BasicInformation></BasicInformation>
            </el-tab-pane>
            <el-tab-pane label="修改密码" :lazy="true">
              <UserChangePassword></UserChangePassword>
            </el-tab-pane>
            <el-tab-pane label="绑定动态口令" :lazy="true">
              <UserTotp></UserTotp>
            </el-tab-pane>
            <el-tab-pane label="在线终端" :lazy="true">
              <UserOnlineTerminal></UserOnlineTerminal>
            </el-tab-pane>
            <el-tab-pane label="安全日志" :lazy="true">
              <UserSecurityLog></UserSecurityLog>
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>
    </el-scrollbar>
  </div>
</template>

<style scoped>
.el-tag+.el-tag {
  margin-left: 5px;
}

.vt-height {
  height: calc(var(--vt-tab-content-height));
}

.el-main {
  padding: 0px 0px 0px 15px;
}
</style>
