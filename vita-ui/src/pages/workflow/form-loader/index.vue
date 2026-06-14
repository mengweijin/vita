<route lang="yaml">
meta:
  title: 发起流程
</route>

<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import { useWorkflowFormPageStore } from "@/store/workflow-form-page-store.js";
const workflowFormPageStore = useWorkflowFormPageStore();
const { tabTitle, flowCode, businessId, readonly, loading } = storeToRefs(workflowFormPageStore);
const route = useRoute();

const title = ref(null);

// 动态路由地址
const routePath = ref("");

watchEffect(async () => {
  routePath.value = await flowDefinitionApi.queryPublishedDefinitionStartFormRoutePathByFlowCode(
    flowCode.value,
  );

  loading.value = false;
});
</script>

<template>
  <div v-loading="loading" style="height: 100%">
    <VtRoutePageLoader :route-path="routePath" :readonly="readonly" :businessId="businessId" />
  </div>
</template>

<style scoped></style>
