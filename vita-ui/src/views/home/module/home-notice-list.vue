<script setup>
import { noticeApi } from '@/api/system/notice-api';

const loading = ref(false);

const size = ref('small');

const tableData = ref([]);

const queryParams = reactive({
  released: 'Y',
  total: 0,
  size: 8,
  current: 1,
});

const loadTableData = () => {
  loading.value = true;
  noticeApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();
}

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <div style="background-color: white; padding: 10px 15px 10px 15px;">
    <!-- 表格头-->
    <el-row :gutter="10" :size="size" style="margin-bottom: 10px;">
      <!-- 左侧 -->
      <el-col :span="1.5">
        通知/公告
      </el-col>

      <!-- 右侧 -->
      <el-col :span="1.5" style="margin-left: auto;">
        <el-button type="primary" :size="size">
          <template #icon>
            <el-icon>
              <Icon icon="ep:more-filled"></Icon>
            </el-icon>
          </template>
          更多
        </el-button>
      </el-col>

    </el-row>
    <el-table v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row>
      <el-table-column prop="title" label="标题" min-width="160" fixed="left" />
      <el-table-column prop="createTime" label="发布时间" align="center" width="140" />
    </el-table>
    <el-pagination layout="total, prev, pager, next, jumper" :size="size" v-model:current-page="queryParams.current"
      v-model:page-size="queryParams.size" :total="queryParams.total" @change="handlePageChange" />
  </div>
</template>


<style lang="css" scoped></style>
