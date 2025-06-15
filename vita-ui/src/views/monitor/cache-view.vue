<script setup>
import { monitorCacheApi } from "@/api/monitor/cache-api";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import { debounce, isEmpty } from 'xe-utils';
import { isJSON } from "@/utils/tool";

const loading = ref(false);

const cacheNames = ref([]);

const cacheList = ref([]);

const filteredCacheList = ref([]);

const activeIndex = ref(-1);

const cacheName = ref('');

const cacheValue = ref('');

const query = ref(null);

const handleCacheNameChange = async (val) => {
  cacheName.value = val;
  cacheList.value = [];
  filteredCacheList.value = [];
  cacheValue.value = "";
  activeIndex.value = -1;

  if (!isEmpty(val)) {
    loading.value = true;
    cacheList.value = await monitorCacheApi.query(val);
    filteredCacheList.value = handleFilterCacheList(query.value);
    loading.value = false;
  }
};

const viewCacheValue = (val, index) => {
  cacheValue.value = val;
  activeIndex.value = index;
};

const handleFilterCacheList = debounce((val) => {
  if (isEmpty(val)) {
    filteredCacheList.value = cacheList.value;
  } else {
    filteredCacheList.value = cacheList.value.filter(item => item.name.includes(val));
  }
}, 1000);

const refreshCache = (cacheName) => {
  handleCacheNameChange(cacheName);
};

const removeCache = (cacheName, cacheKey = '') => {
  monitorCacheApi.remove(cacheName, cacheKey).then(() => {
    handleCacheNameChange(cacheName);
  });
};

onMounted(async () => {
  loading.value = true;
  cacheNames.value = await monitorCacheApi.names();
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
            <el-option v-for="item in cacheNames" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin: 0px; margin-left: 18px;">
          <el-button type="primary" :disabled="isEmpty(cacheName)" @click="refreshCache(cacheName)">
            <template #icon>
              <el-icon>
                <Icon icon="ep:refresh"></Icon>
              </el-icon>
            </template>
            刷新
          </el-button>
          <el-button type="danger" :disabled="isEmpty(cacheName)" @click="removeCache(cacheName)">
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

      <el-input v-model="query" placeholder="筛选" clearable @input="handleFilterCacheList"
        style="margin-bottom: 10px;" />

      <div v-for="(item, index) in filteredCacheList" :key="item.key" :class="['cache-item']">
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
          <el-button type="primary" text :disabled="isEmpty(cacheName)" style="float: right;">
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
