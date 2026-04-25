<script setup>
import { roleApi } from "@/api/system/role-api";
import VtSelectUser from "@/components/modules/system/vt-select-user.vue";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  roleId: undefined,
  userIds: [],
});

const init = () => {
  form.roleId = data.value.id ?? undefined;
  form.userIds = data.value?.userIds ?? [];
};

const formRef = useTemplateRef("formRef");

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    roleApi.setUsers(form.roleId, form.userIds).then((r) => {
      emit("refresh-table");
      onClosed();
    });
  });
};

const emit = defineEmits(["refresh-table"]);

const onOpened = async () => {
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
  <el-dialog
    v-model="visible"
    :title="`角色【${data.name}】新增用户`"
    destroy-on-close
    :align-center="false"
    @opened="onOpened"
    @closed="onClosed"
    width="600px"
  >
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <VtSelectUser v-model="form.userIds" :multiple="true"></VtSelectUser>
    </el-form>
    <template #footer>
      <div style="margin-top: 15px">
        <el-button type="primary" @click="onSubmit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="warning" @click="init" v-if="false">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="danger" @click="onClosed">
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
