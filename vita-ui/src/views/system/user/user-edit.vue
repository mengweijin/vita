<script setup>
import { deptApi } from '@/api/system/dept-api';
import { userApi } from "@/api/system/user-api";
import { addFullPath } from '@/utils/tool.js';
import { toArrayTree } from 'xe-utils';
import VtDictSelect from "@/components/modules/vt-dict-select.vue";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

const deptList = ref([]);

const deptTreeSelectOptions = computed(() => {
  deptList.value.forEach((item) => item.disabled = false);
  addFullPath(deptList.value, { pathKey: 'name' })
  return toArrayTree(deptList.value, { sortKey: 'seq' });
});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  id: undefined,
  deptId: undefined,
  username: undefined,
  nickname: undefined,
  mobile: undefined,
  email: undefined,
  citizenId: undefined,
  gender: undefined,
  disabled: 'N',
  remark: undefined,
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.deptId = data.value.deptId ?? undefined;
  form.username = data.value.username ?? undefined;
  form.nickname = data.value.nickname ?? undefined;
  form.mobile = data.value.mobile ?? undefined;
  form.email = data.value.email ?? undefined;
  form.citizenId = data.value.citizenId ?? undefined;
  form.gender = data.value.gender ?? 'male';
  form.disabled = data.value.disabled ?? 'N';
  form.remark = data.value.remark ?? undefined;
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    if (form.id) {
      userApi.update(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    } else {
      userApi.create(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const onOpened = () => {
  loading.value = true;
  deptApi.list({ disabled: 'N' }).then((res) => {
    deptList.value = res;
  });
  init();
  loading.value = false;
}

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data, deptTreeSelectOptions })
</script>

<template>
  <el-dialog v-model="visible" :title="data?.id ? '编辑' : '新增'" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">

      <el-form-item prop="deptId" label="部门" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-tree-select v-model="form.deptId" :data="deptTreeSelectOptions"
          :props="{ label: 'nameFullPath', value: 'id', children: 'children' }" check-strictly filterable clearable
          default-expand-all placeholder="请选择">
          <template #default="{ data: { name } }">
            {{ name }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="username" label="登录账号" :rules="[
            { required: true, message: '必填', trigger: 'blur' },
            { pattern: /^\w+$/, message: '只能包含英文字母、数字和下划线' },
          ]">
            <el-input v-model="form.username" clearable maxlength="64" autocomplete="off" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="nickname" label="用户昵称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
            <el-input v-model="form.nickname" clearable maxlength="30" autocomplete="off" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="mobile" label="移动电话"
            :rules="[{ pattern: /(?:0|86|\+86)?1[3-9]\d{9}/, message: '电话号码格式不正确' }]">
            <el-input v-model="form.mobile" clearable maxlength="15" autocomplete="off" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="email" label="邮箱" :rules="[]">
            <el-input v-model="form.email" clearable maxlength="128" autocomplete="off" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item prop="citizenId" label="身份证号"
        :rules="[{ pattern: /[1-9]\d{5}[1-2]\d{3}((0\d)|(1[0-2]))(([012]\d)|3[0-1])\d{3}(\d|X|x)/, message: '身份证号格式不正确' }]">
        <el-input v-model="form.citizenId" clearable maxlength="18" autocomplete="off" />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="gender" label="性别">
            <VtDictSelect v-model="form.gender" :code="'vt_user_gender'"></VtDictSelect>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="disabled" label="状态">
            <el-switch v-model="form.disabled" inline-prompt active-text="启用" inactive-text="停用" active-value="N"
              inactive-value="Y" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item prop="remark" label="备注">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 8 }" />
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
