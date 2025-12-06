<script setup>
import { configApi } from "@/api/system/config-api";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
	configKey: undefined,
	configValue: undefined,
	id: undefined,
	remark: undefined,
});

const init = () => {
	form.id = data.value.id ?? undefined;
	form.configKey = data.value.configKey ?? undefined;
	form.configValue = data.value.configValue ?? undefined;
	form.remark = data.value.remark ?? undefined;
};

const formRef = useTemplateRef("formRef");

const onSubmit = () => {
	formRef.value.validate((valid, fields) => {
		if (!valid) {
			// fields 只有在验证失败的情况下才有值
			console.log(fields);
			return;
		}
		if (form.id) {
			configApi.update(form).then((r) => {
				emit("refresh-table");
				onClosed();
			});
		} else {
			configApi.create(form).then((r) => {
				emit("refresh-table");
				onClosed();
			});
		}
	});
};

const emit = defineEmits(["refresh-table"]);

const onOpened = () => {
	loading.value = true;
	init();
	loading.value = false;
};

const onClosed = () => {
	visible.value = false;
	data.value = {};
	init();
};

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ data, visible });
</script>

<template>
  <el-dialog v-model="visible" :title="data?.id ? '编辑' : '新增'" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">

      <el-form-item prop="configKey" label="配置键" :rules="[
        { required: true, message: '必填', trigger: 'blur' },
        { pattern: /^vita\./, message: '配置键必须以 “vita.” 开头', trigger: 'blur' }
      ]">
        <el-input v-model="form.configKey" clearable maxlength="128" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="configValue" label="配置值" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.configValue" type="textarea" clearable maxlength="255"
          :autosize="{ minRows: 1, maxRows: 8 }" />
      </el-form-item>

      <el-form-item prop="remark" label="备注">
        <el-input v-model="form.remark" type="textarea" maxlength="500" :autosize="{ minRows: 3, maxRows: 8 }" />
      </el-form-item>
    </el-form>
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
        <el-button type="warning" @click="init">
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

<style scoped></style>
