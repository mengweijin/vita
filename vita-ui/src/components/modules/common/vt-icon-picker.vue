<script setup>
import { debounce, isEmpty } from 'xe-utils';
import { listIcons } from '@iconify/vue';

const inputValue = defineModel({ type: String, default: '' });

const iconPreview = ref('ep:search');

const iconCollection = ref('ep');

const collectionOptions = ref([
  { label: 'Element-Plus', value: 'ep' },
  { label: 'Remix', value: 'ri' },
  { label: 'Ant-Design', value: 'ant-design' },
]);

const currentPage = ref(1);

const pageSize = ref(70);

const allIconList = computed(() => {
  return listIcons(null, iconCollection.value);
});

const currentIconList = ref([]);

const pageIconList = (list) => {
  let start = (currentPage.value - 1) * pageSize.value;
  let end = currentPage.value * pageSize.value;
  return list.slice(start, end);
};

const handleIconCollectionChange = (val) => {
  currentPage.value = 1;
  handleSearch();
};

const handleCurrentChange = (current) => {
  currentPage.value = current;
  handleSearch();
};

const search = ref(null);

const handleSearch = debounce(() => {
  if (search.value) {
    let filteredList = allIconList.value.filter(icon => icon.includes(search.value));
    currentIconList.value = pageIconList(filteredList);
  } else {
    currentIconList.value = pageIconList(allIconList.value);
  }
}, 1000);

const popoverRef = ref(null);

const changeIcon = (value) => {
  inputValue.value = value;
};

const clearInputValue = () => {
  inputValue.value = '';
}

const close = () => {
  // 手动关闭 popover
  popoverRef.value.hide();
};

watch(inputValue, (newIcon) => {
  if (isEmpty(inputValue.value)) {
    iconPreview.value = 'ep:search';
  } else {
    iconPreview.value = newIcon;
  }
});

onMounted(() => {
  handleSearch();
});
</script>

<template>
  <el-input v-model="inputValue" autocomplete="off" disabled style="width: 50%;">
    <template #append>
      <el-popover ref="popoverRef" placement="bottom" width="510" trigger="click">
        <div>
          <el-input v-model="search" @input="handleSearch" clearable style="width: 50%" placeholder="请输入要查找的图标" />

          <span style="float: right; ">
            <el-button type="danger" size="small" @click="clearInputValue" style="margin-right: 10px;">清空</el-button>
            <el-button type="primary" size="small" @click="close">确定</el-button>
          </span>

        </div>
        <div style="margin-top: 8px;">
          <el-segmented v-model="iconCollection" :options="collectionOptions"
            :props="{ label: 'label', value: 'value' }" block @change="handleIconCollectionChange" />
        </div>
        <el-scrollbar max-height="260px">
          <div class="vt-icon-picker-content">
            <el-button text v-for="item in currentIconList" :key="item" @click="changeIcon(item)">
              <el-tooltip :content="item" placement="top">
                <el-icon size="large">
                  <Icon :icon="item" />
                </el-icon>
              </el-tooltip>
            </el-button>
          </div>
        </el-scrollbar>

        <el-pagination background :current-page="currentPage" :page-size="pageSize" size="small"
          layout="total, prev, pager, next, jumper" :total="allIconList.length" @current-change="handleCurrentChange" />

        <template #reference>
          <el-button text>
            <el-icon size="large">
              <Icon :icon="iconPreview" />
            </el-icon>
          </el-button>
        </template>
      </el-popover>
    </template>
  </el-input>
</template>

<style scoped>
.vt-icon-picker-content {
  display: flex;
  flex-wrap: wrap;
  margin: 15px 0px;
}

.el-button+.el-button {
  margin-left: 0px;
}
</style>
