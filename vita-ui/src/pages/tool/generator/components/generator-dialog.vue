<script setup>
import { generatorApi } from '@/api/tool/generator-api.js';
import VtCodeHighlight from '@/components/modules/common/vt-code-highlight.vue';
import utils from '@/utils/utils.js';

const loading = ref(true);

const visible = ref(false);

const templateList = ref([]);

const defaultArgs = ref({});

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  templateId: undefined,
  tableName: undefined,
  tablePrefix: undefined,
  packages: undefined,
  moduleName: undefined,
  author: undefined,
});

const init = () => {
  form.templateId = defaultArgs.value.templateId ?? undefined;
  form.tableName = defaultArgs.value.tableName ?? undefined;
  form.tablePrefix = defaultArgs.value.tablePrefix ?? undefined;
  form.packages = defaultArgs.value.packages ?? undefined;
  form.moduleName = defaultArgs.value.moduleName ?? undefined;
  form.author = defaultArgs.value.author ?? undefined;
};

const formRef = useTemplateRef("formRef");

const onSubmit = () => {
	formRef.value.validate((valid, fields) => {
		if (!valid) {
			// fields 只有在验证失败的情况下才有值
			console.log(fields);
			return;
		}
		// TODO 提交表单
	});
};

const onOpened = () => {
	loading.value = true;
  generatorApi.listTemplate().then((res) => {
    templateList.value = res;
  });
  generatorApi.queryDefaultArgs().then((res) => {
    defaultArgs.value = res;
    init();
     form.tableName = data.value.name;
  });
  loading.value = false;
};

const onClosed = () => {
	visible.value = false;
  data.value = {};
};

const treeRef = useTemplateRef("treeRef");

const treeProps = reactive({
  children: "children",
  disabled: "disabled",
  label: "name",
});

const treeData = computed(() => {
  return utils.toArrayTree(templateList.value, { sortKey: "name" });
});

const contentPreview = ref({});

const handleTreeNodeClick = (data, node) => {
  form.templateId = data.id;
  generatorApi.run(form).then((res) => {
    contentPreview.value = res;
  });
};

/** 暴露给父组件，父组件可通过 generatorDialogRef.value.visible = true; 来赋值 */
defineExpose({ data, visible });
</script>

<template>
  <el-dialog v-model="visible" :title="`表 [${data?.name}] 生成代码`" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" fullscreen width="100%">
    <el-container v-loading="loading">
      <el-aside width="300px">
        <el-scrollbar>
          <el-tree ref="treeRef" :node-key="'id'" :props="treeProps" :data="treeData" default-expand-all highlight-current
            :expand-on-click-node="false" @node-click="handleTreeNodeClick" class="vt-tree vt-height" />
        </el-scrollbar>
      </el-aside>
      <el-main class="vt-height">
        <!-- 查询表单 -->
        <el-form ref="formRef" :model="form" :inline="true">
          <el-form-item prop="tablePrefix" label="忽略表前缀">
            <el-input v-model="form.tablePrefix" placeholder="" clearable style="width: 90px;"/>
          </el-form-item>
          <el-form-item prop="author" label="作者">
            <el-input v-model="form.author" placeholder="" clearable style="width: 95px;" />
          </el-form-item>
          <el-form-item prop="packages" label="包名">
            <el-input v-model="form.packages" placeholder="" clearable style="width: 200px;" />
          </el-form-item>
          <el-form-item prop="moduleName" label="模块名">
            <el-input v-model="form.moduleName" placeholder="" clearable style="width: 80px;" />
          </el-form-item>
        </el-form>

        <!-- <el-divider style="margin: -5px 0 0 0;" /> -->
        <VtCodeHighlight :code="contentPreview?.content" language="java" />
      </el-main>
    </el-container>

    <template #footer>
      <div v-if="false">
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

<style scoped>
.vt-tree {
  margin-right: 20px;
}

.vt-height {
  height: calc(var(--vt-tab-content-height)) !important;
}

.el-main {
  padding: 0px;
  overflow: hidden;
}
</style>
