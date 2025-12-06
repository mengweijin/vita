<script setup>
const { src } = defineProps({
	src: {
		default: "",
		type: String,
	},
});

const loading = ref(false);

onMounted(() => {});
</script>

<template>
  <div>IFRAME</div>
  <!-- <iframe :src="src"></iframe> -->
</template>

<style scoped></style>
