<script setup>
import { ref, reactive, onMounted } from "vue";

const { message } = defineProps({
  message: {
    type: String,
    default: '',
  }
});

const loading = ref(false);

const data = reactive({
  username: "admin",
});

onMounted(() => { });

</script>

<template>
  <div>Dept</div>
</template>

<style scoped></style>
