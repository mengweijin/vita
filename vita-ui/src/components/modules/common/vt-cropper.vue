<script setup>
import Cropper from 'cropperjs';

const loading = ref(false);

const visible = ref(false);

const dataBase64 = ref("");

const emit = defineEmits(["callback"]);

const imageSrc = ref('/avatar.jpg');

const imageRef = useTemplateRef('imageRef');

const cropperRef = ref(null);

const onOpened = () => {
	loading.value = true;
	cropperRef.value = new Cropper(imageRef.value);
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$ready((image) => {
		loading.value = false;
	});
};

const onClosed = () => {
	visible.value = false;
	cropperRef.value = null;
};

const onSubmit = () => {
	emit("callback", resultBase64);
	onClosed();
};

const moveDistance = ref(10);
const rotationAngle = ref(45);
const zoomScale = ref(0.1);

const handleMoveUp = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$move(0, -moveDistance.value);
};

const handleMoveDown = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$move(0, moveDistance.value);
};

const handleMoveLeft = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$move(-moveDistance.value, 0);
};

const handleMoveRight = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$move(moveDistance.value, 0);
};

const handleRotateLeft = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$rotate(`${-rotationAngle.value}deg`);
};

const handleRotateRight = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$rotate(`${rotationAngle.value}deg`);
};

const handleZoomBigger = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$zoom(zoomScale.value);
};

const handleZoomSmaller = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$zoom(-zoomScale.value);
};

const handleScaleX = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$scale(-1, 1);
};

const handleScaleY = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$scale(1, -1);
};

const handleCenter = () => {
	const cropperImage = cropperRef.value.getCropperImage();
	cropperImage.$center("contain");
};


/** 暴露给父组件，父组件可通过 cropperRef.value.visible = true; 来赋值 */
defineExpose({ visible });

onMounted(() => {
});
</script>

<template>
	<el-dialog v-model="visible" :title="'图片裁剪'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
		width="80%">
		<div style="height: 300px;">
			<img ref="imageRef" :src="imageSrc" alt="Picture">
		</div>
		<div style="margin-top: 10px;">
			<el-form :inline="true">
				<el-form-item prop="moveDistance" label="移动距离">
					<el-input v-model="moveDistance" style="width: 50px;" />
				</el-form-item>

				<el-form-item>
					<el-tooltip content="上移" placement="top">
						<el-button type="primary" circle @click="handleMoveUp">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:arrow-up-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="下移" placement="top">
						<el-button type="primary" circle @click="handleMoveDown">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:arrow-down-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="左移" placement="top">
						<el-button type="primary" circle @click="handleMoveLeft">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:arrow-left-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="右移" placement="top">
						<el-button type="primary" circle @click="handleMoveRight">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:arrow-right-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
				</el-form-item>

				<el-form-item label=" ">
					<el-divider direction="vertical" />
				</el-form-item>

				<el-form-item prop="rotationAngle" label="旋转角度">
					<el-input v-model="rotationAngle" style="width: 50px;" />
				</el-form-item>
				<el-form-item>
					<el-tooltip content="逆时针旋转" placement="top">
						<el-button type="warning" circle @click="handleRotateLeft">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:rotate-left-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="顺时针旋转" placement="top">
						<el-button type="warning" circle @click="handleRotateRight">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:rotate-right-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
				</el-form-item>

				<el-form-item label=" ">
					<el-divider direction="vertical" />
				</el-form-item>

				<el-form-item prop="zoomScale" label="缩放系数">
					<el-input v-model="zoomScale" style="width: 50px;" />
				</el-form-item>
				<el-form-item>
					<el-tooltip content="放大" placement="top">
						<el-button type="success" circle @click="handleZoomBigger">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:zoom-in-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="缩小" placement="top">
						<el-button type="success" circle @click="handleZoomSmaller">
							<template #icon>
								<el-icon>
									<Icon icon="ant-design:zoom-out-outlined"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
				</el-form-item>

				<el-form-item label=" ">
					<el-divider direction="vertical" />
				</el-form-item>

				<el-form-item label=" ">
					<el-tooltip content="水平翻转" placement="top">
						<el-button type="danger" circle @click="handleScaleX">
							<template #icon>
								<el-icon>
									<Icon icon="ri:arrow-left-right-fill"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="垂直翻转" placement="top">
						<el-button type="danger" circle @click="handleScaleY">
							<template #icon>
								<el-icon>
									<Icon icon="ri:arrow-up-down-fill"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
					<el-tooltip content="居中" placement="top">
						<el-button type="danger" circle @click="handleCenter">
							<template #icon>
								<el-icon>
									<Icon icon="ri:artboard-2-fill"></Icon>
								</el-icon>
							</template>
						</el-button>
					</el-tooltip>
				</el-form-item>
			</el-form>
		</div>
		<template #footer>
			<div>
				<el-button type="primary">
					<template #icon>
						<el-icon>
							<Icon icon="ep:check"></Icon>
						</el-icon>
					</template>
					确定
				</el-button>
				<el-button type="warning">
					<template #icon>
						<el-icon>
							<Icon icon="ep:refresh-left"></Icon>
						</el-icon>
					</template>
					重置
				</el-button>
				<el-button type="primary" @click="onClosed">
					<template #icon>
						<el-icon>
							<Icon icon="ep:close"></Icon>
						</el-icon>
					</template>
					取消
				</el-button>
			</div>
		</template>
	</el-dialog>
</template>

<style scoped>
.el-form--inline .el-form-item {
	margin-right: 10px;
}

.el-divider--vertical {
	height: 2em;
	border-left: 2px var(--el-border-color) var(--el-border-style);
	margin-left: -5px;
}
</style>
