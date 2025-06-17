<script setup>
import { cacheApi } from "@/api/monitor/cache-api";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import { debounce, isEmpty } from 'xe-utils';
import { isJSON } from "@/utils/tool";

const loading = ref(false);

const cacheNameList = ref([]);

const dataList = ref([]);

const cacheName = ref(null);

const activeIndex = ref(-1);

const cacheValue = ref('');

const handleCacheNameChange = async (name) => {
  cacheName.value = name;
  dataList.value = [];
  filteredDataList.value = [];
  cacheValue.value = "";
  activeIndex.value = -1;

  if (!isEmpty(name)) {
    loading.value = true;
    dataList.value = await cacheApi.query(name);
    handleFilterDataList(keywords.value);
    loading.value = false;
  }
};

const keywords = ref(null);

const filteredDataList = ref([]);

const handleFilterDataList = debounce((name) => {
  if (isEmpty(name)) {
    filteredDataList.value = dataList.value;
  } else {
    filteredDataList.value = dataList.value.filter(item => item.name.includes(name));
  }
}, 1000);

const refreshByCacheName = (name) => {
  handleCacheNameChange(name);
};

const clearByCacheName = (name) => {
  cacheApi.clearByName(name).then(() => {
    handleCacheNameChange(name);
  });
};

const viewCacheValue = (val, index) => {
  cacheValue.value = val;
  activeIndex.value = index;
};

const removeCache = (name, key) => {
  cacheApi.remove(name, key).then(async () => {
    await handleCacheNameChange(name);
    handleFilterDataList(name);
  });
};

const refreshCacheByNameAndKey = (name, key) => {
  cacheApi.queryCacheByNameAndKey(name, key).then((res) => {
    cacheValue.value = res.value;
  });
};

onMounted(async () => {
  loading.value = true;
  cacheNameList.value = await cacheApi.names();
  loading.value = false;
});

</script>

<template>
  <el-container v-loading="loading" style="padding: 10px 0px;">
    <el-aside width="450px">
      <el-form :inline="true">
        <el-form-item style="margin: 0px;">
          <el-select v-model="cacheName" clearable filterable style="width: 260px;" :size="'default'"
            placeholder="请选择缓存名称" @change="handleCacheNameChange">
            <el-option v-for="item in cacheNameList" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin: 0px; margin-left: 18px;">
          <el-button type="primary" :disabled="isEmpty(cacheName)" @click="refreshByCacheName(cacheName)">
            <template #icon>
              <el-icon>
                <Icon icon="ep:refresh"></Icon>
              </el-icon>
            </template>
            刷新
          </el-button>
          <el-button type="danger" :disabled="isEmpty(cacheName)" @click="clearByCacheName(cacheName)">
            <template #icon>
              <el-icon>
                <Icon icon="ep:delete"></Icon>
              </el-icon>
            </template>
            清空
          </el-button>
        </el-form-item>
      </el-form>

      <el-divider style="margin: 10px 0px;" />

      <el-input v-model="keywords" placeholder="筛选" clearable @input="handleFilterDataList"
        style="margin-bottom: 10px;" />

      <div v-for="(item, index) in filteredDataList" :key="item.key" :class="['cache-item']">
        <div :class="[index === activeIndex ? 'active' : '']" @click="viewCacheValue(item.value, index)"
          style="display: inline-block; background-color: #eeeeee; width: 340px;">
          <a href="javascript:">
            <el-icon>
              <Icon icon="ep:info-filled" />
            </el-icon>
            <span style="margin-left: 8px; padding-bottom: 2px;">{{ item.key }}</span>
          </a>
        </div>

        <el-tooltip content="删除" placement="top">
          <el-button type="danger" text :disabled="isEmpty(cacheName)" @click="removeCache(cacheName, item.key)"
            style="float: right;margin-right: 2px;">
            <template #icon>
              <el-icon>
                <Icon icon="ep:delete"></Icon>
              </el-icon>
            </template>
          </el-button>
        </el-tooltip>

        <el-tooltip content="刷新" placement="top">
          <el-button type="primary" text :disabled="isEmpty(cacheName)" style="float: right;"
            @click="refreshCacheByNameAndKey(cacheName, item.key)">
            <template #icon>
              <el-icon>
                <Icon icon="ep:refresh"></Icon>
              </el-icon>
            </template>
          </el-button>
        </el-tooltip>
      </div>
    </el-aside>
    <el-main style="background-color: #f7f7f7; margin-left: 10px; padding: 5px 10px;">
      <el-scrollbar>
        <template v-if="cacheValue != null">
          <vue-json-pretty v-if="isJSON(cacheValue)" :data="JSON.parse(cacheValue)" />
          <div v-else>{{ cacheValue }}</div>
        </template>
      </el-scrollbar>
    </el-main>
  </el-container>

</template>

<style scoped>
.active {
  background-color: var(--vt-primary-color) !important;
  border-radius: 4px;
}

.cache-item+.cache-item {
  margin: 2px 0px;
}

.cache-item a {
  text-decoration: none;
  display: flex;
  /* 垂直居中 */
  align-items: center;
  color: black;
}

.cache-item a:hover {
  background-color: var(--vt-primary-color);
}
</style>
