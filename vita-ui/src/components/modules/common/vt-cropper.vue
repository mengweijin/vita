<script setup>
import "cropperjs";

const modelValue = defineModel({ type: String });

const visible = defineModel("visible", { default: false, required: true, type: Boolean });
const src = defineModel("src", { default: "", required: true, type: String });

const cropperCanvas = useTemplateRef("cropperCanvas");
const cropperImage = useTemplateRef("cropperImage");
const cropperShade = useTemplateRef("cropperShade");
const cropperSelection = useTemplateRef("cropperSelection");

const onClosed = () => {
  visible.value = false;
};

const onSubmit = async () => {
  const canvas = await cropperSelection.value.$toCanvas();
  const imageBase64 = canvas.toDataURL("image/png");
  modelValue.value = imageBase64;
  src.value = imageBase64;
  onClosed();
};

const handleOnImageSelected = (uploadFile) => {
  const file = uploadFile.raw;
  if (!file) {
    return;
  }

  try {
    // 将文件转换为 base64 的通用函数
    const reader = new FileReader();
    reader.onload = (e) => {
      src.value = e.target.result;
    };
    reader.readAsDataURL(file);
  } catch (_error) {}
};

// 纵横比
const aspectRatio = ref(1);
// 移动距离
const moveDistance = ref(10);
// 旋转角度
const rotationAngle = ref(45);
// 缩放比例
const zoomScale = ref(0.1);

const handleAspectRatio = (ratio) => {
  aspectRatio.value = ratio;
};

const handleMoveUp = () => {
  cropperImage.value.$move(0, -moveDistance.value);
};

const handleMoveDown = () => {
  cropperImage.value.$move(0, moveDistance.value);
};

const handleMoveLeft = () => {
  cropperImage.value.$move(-moveDistance.value, 0);
};

const handleMoveRight = () => {
  cropperImage.value.$move(moveDistance.value, 0);
};

const handleRotateLeft = () => {
  cropperImage.value.$rotate(`${-rotationAngle.value}deg`);
};

const handleRotateRight = () => {
  cropperImage.value.$rotate(`${rotationAngle.value}deg`);
};

const handleZoomBigger = () => {
  cropperImage.value.$zoom(zoomScale.value);
};

const handleZoomSmaller = () => {
  cropperImage.value.$zoom(-zoomScale.value);
};

const handleScaleX = () => {
  cropperImage.value.$scale(-1, 1);
};

const handleScaleY = () => {
  cropperImage.value.$scale(1, -1);
};

const handleCenter = () => {
  cropperImage.value.$center("contain");
};
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="'图像裁剪'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="1000px"
    style="height: 560px"
  >
    <div>
      <div>
        <el-form :inline="true">
          <el-form-item prop="aspectRatio" label="裁剪比例">
            <el-select
              v-model.number="aspectRatio"
              style="width: 100px"
              @change="handleAspectRatio"
              placeholder="自由裁剪"
            >
              <el-option label="自由裁剪" :value="undefined"></el-option>
              <el-option label="1:1" :value="1"></el-option>
              <el-option label="4:3" :value="4 / 3"></el-option>
              <el-option label="16:9" :value="16 / 9"></el-option>
              <el-option label="3:4" :value="3 / 4"></el-option>
              <el-option label="9:16" :value="9 / 16"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-upload
              :show-file-list="false"
              :auto-upload="false"
              action="#"
              :on-change="handleOnImageSelected"
              accept="image/*"
            >
              <el-button type="primary">
                <template #icon>
                  <el-icon>
                    <Icon icon="ep:upload"></Icon>
                  </el-icon>
                </template>
                上传图片
              </el-button>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-row :gutter="10">
          <el-col :span="16">
            <cropper-canvas ref="cropperCanvas" background style="height: 350px">
              <cropper-image
                ref="cropperImage"
                :src="src"
                alt="Picture"
                rotatable
                scalable
                skewable
                translatable
              ></cropper-image>
              <cropper-shade ref="cropperShade" hidden></cropper-shade>
              <cropper-handle action="select" plain></cropper-handle>
              <cropper-selection
                ref="cropperSelection"
                id="cropperSelection"
                :aspect-ratio="aspectRatio"
                initial-coverage="1"
                movable
                resizable
                outlined
              >
                <cropper-grid role="grid" covered></cropper-grid>
                <cropper-crosshair centered></cropper-crosshair>
                <cropper-handle
                  action="move"
                  theme-color="rgba(255, 255, 255, 0.35)"
                ></cropper-handle>
                <cropper-handle action="n-resize"></cropper-handle>
                <cropper-handle action="e-resize"></cropper-handle>
                <cropper-handle action="s-resize"></cropper-handle>
                <cropper-handle action="w-resize"></cropper-handle>
                <cropper-handle action="ne-resize"></cropper-handle>
                <cropper-handle action="nw-resize"></cropper-handle>
                <cropper-handle action="se-resize"></cropper-handle>
                <cropper-handle action="sw-resize"></cropper-handle>
              </cropper-selection>
            </cropper-canvas>
          </el-col>
          <el-col :span="8">
            <div class="cropper-viewers">
              <cropper-viewer
                selection="#cropperSelection"
                class="vt-cropper-viewer"
                style="width: 160px"
              ></cropper-viewer>
              <cropper-viewer
                selection="#cropperSelection"
                class="vt-cropper-viewer"
                style="width: 80px"
              ></cropper-viewer>
              <cropper-viewer
                selection="#cropperSelection"
                class="vt-cropper-viewer"
                style="width: 40px"
              ></cropper-viewer>
            </div>
            <div class="cropper-viewers" v-if="aspectRatio === 1">
              <div
                class="vt-cropper-viewer vt-cropper-viewer-circle"
                style="width: 160px; height: 160px"
              >
                <cropper-viewer selection="#cropperSelection" style="width: 160px"></cropper-viewer>
              </div>
              <div
                class="vt-cropper-viewer vt-cropper-viewer-circle"
                style="width: 80px; height: 80px"
              >
                <cropper-viewer selection="#cropperSelection" style="width: 80px"></cropper-viewer>
              </div>
              <div
                class="vt-cropper-viewer vt-cropper-viewer-circle"
                style="width: 40px; height: 40px"
              >
                <cropper-viewer selection="#cropperSelection" style="width: 40px"></cropper-viewer>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div style="margin-top: 10px">
        <el-form :inline="true">
          <el-form-item prop="moveDistance" label="移动距离">
            <el-input v-model="moveDistance" style="width: 50px" />
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
            <el-input v-model="rotationAngle" style="width: 50px" />
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
            <el-input v-model="zoomScale" style="width: 50px" />
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
    </div>
    <template #footer>
      <div>
        <el-button type="primary" @click="onSubmit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="info" @click="onClosed">
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

.vt-cropper-viewer {
  border: 1px solid var(--vp-c-divider);
  display: inline-block;
  margin-right: 0.25rem;
}

.vt-cropper-viewer-circle {
  /* 1. 关键：将容器设置为正方形 */
  width: 160px;
  height: 160px;
  /* 2. 核心：设置为圆形并隐藏溢出 */
  border-radius: 50%;
  overflow: hidden;
}
</style>
