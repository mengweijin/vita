<route lang="yaml">
meta:
  title: 流程实例
  permission: workflow:instance:view
</route>

<script setup>
import WorkflowInstanceList from "./components/workflow-instance-list.vue";
</script>

<template>
  <WorkflowInstanceList :scope="'all'" />
</template>

<style scoped></style>
