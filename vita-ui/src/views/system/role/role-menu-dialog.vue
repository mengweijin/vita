<script setup>
import { menuApi } from "@/api/system/menu-api";
import { roleApi } from "@/api/system/role-api";
import { toArrayTree, debounce } from 'xe-utils';

const loading = ref(true);

const visible = ref(false);

const data = ref({});

const treeRef = ref(null);

/** 父子不互相关联。true: 父子不联动；false: 父子联动。 */
const checkStrictly = ref(true);

const treeProps = reactive({
  children: 'children',
  label: 'title',
  disabled: (data, node) => data.disabled === 'Y',
})

const treeData = ref([]);

const loadTreeData = async () => {
  let menuList = await menuApi.list();
  treeData.value = toArrayTree(menuList, { sortKey: 'seq' });
};

const keywords = ref('');

const filterNodeMethod = (value, data, node) => {
  console.log(value);
  if (!value) {
    return true;
  }
  return data.title.includes(value);
};

const handleSearch = debounce((val) => {
  treeRef.value?.filter(val);
}, 1000);

watch(keywords, (val) => {
  handleSearch(val);
});


const onSubmit = () => {
  // 获取全选节点 keys（包括父节点）
  const checkedKeys = treeRef.value.getCheckedKeys(false);
  // 获取半选节点 keys
  const halfCheckedKeys = treeRef.value.getHalfCheckedKeys();
  // 使用 ... 扩展运算符把两个数组合并为一个
  roleApi.setPermission(data.value.id, [...checkedKeys, ...halfCheckedKeys]).then(() => {
    onClosed();
  });
}

const defaultCheckedKeys = ref([]);

const onOpened = async () => {
  loading.value = true;
  await loadTreeData();
  defaultCheckedKeys.value = await menuApi.getMenuIdsByRoleId(data.value.id);
  checkStrictly.value = false;
  loading.value = false;
}

const onClosed = () => {
  visible.value = false;
  checkStrictly.value = true;
  keywords.value = null;
  data.value = {};
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="`角色授权 - ${data.name}`" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="350px">
    <div v-loading="loading">
      <el-checkbox v-model="checkStrictly" v-show="false">父子节点不联动</el-checkbox>
      <el-input v-model="keywords" placeholder="筛选" />
      <el-tree ref="treeRef" :node-key="'id'" :show-checkbox="true" :check-strictly="checkStrictly" :props="treeProps"
        :data="treeData" :default-checked-keys="defaultCheckedKeys" :filter-node-method="filterNodeMethod" />
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
.vt-table-container {
  flex: 1;
  /* 查询表单：70px; 表格头：63px； */
  height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height) - 70px - 63px);
}
</style>
