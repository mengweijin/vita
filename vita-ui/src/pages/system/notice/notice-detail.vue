<script setup>
const loading = ref(true);

const visible = ref(false);

const data = ref({});

const onOpened = () => {
	loading.value = true;

	loading.value = false;
};

const onClosed = () => {
	visible.value = false;
	data.value = {};
};

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ data, visible });
</script>

<template>
  <el-dialog v-model="visible" :title="'通知/公告'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
    width="90%">

    <div style="text-align: center; margin-bottom: 20px;">
      <h1>{{ data.title }}</h1>
    </div>

    <div v-html="data.description" style="font-size: 16px; line-height: 2;"></div>

    <div style="text-align: right;margin-top: 20px;">
      <strong>作者：</strong><span>{{ data.createByName }}</span>
      <strong style="margin-left: 20px;">更新时间：</strong><span style="margin-right: 30px;">{{ data.updateTime }}</span>
    </div>

  </el-dialog>
</template>

<style scoped></style>
