<route lang="yaml">
meta:
  title: 我的流程
  permission: oa:myWorkflow:view
</route>

<script setup>
import WorkflowInstanceList from "@/pages/workflow/instance/components/workflow-instance-list.vue";
</script>

<template>
  <WorkflowInstanceList :scope="'my'" />
</template>

<style scoped></style>
