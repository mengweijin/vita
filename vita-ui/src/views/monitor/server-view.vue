<script setup>
import { monitorServerApi } from "@/api/monitor/server-api";
import { timeAgo } from "@/utils/tool";

const loading = ref(true);

const size = ref('default');

const data = ref(null);

const serverInfo = ref(null);

const cpuInfo = ref(null);

const memoryInfo = ref(null);

const jvmInfo = ref(null);

const diskInfo = ref([]);

onMounted(() => {
  monitorServerApi.serverInfo().then((res) => {
    data.value = res;
    serverInfo.value = res.server;
    cpuInfo.value = res.cpu;
    memoryInfo.value = res.memory;
    jvmInfo.value = res.jvm;
    diskInfo.value = res.disk;

    loading.value = false;
  });
});

</script>

<template>
  <el-scrollbar v-loading="loading" style="height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));">
    <el-descriptions title="服务器信息" :column="2" :size="size" border style="margin-top: 15px;">
      <el-descriptions-item label="厂商" label-align="right" min-width="200">
        {{ serverInfo?.manufacturer }}
      </el-descriptions-item>
      <el-descriptions-item label="主机名" label-align="right" min-width="200">
        {{ serverInfo?.hostName }}
      </el-descriptions-item>
      <el-descriptions-item label="主机地址" label-align="right">
        {{ serverInfo?.hostAddress }}
      </el-descriptions-item>
      <el-descriptions-item label="系统架构" label-align="right">
        {{ serverInfo?.operationSystemArch }}
      </el-descriptions-item>
      <el-descriptions-item label="操作系统" label-align="right">
        {{ serverInfo?.operationSystem }}
      </el-descriptions-item>
    </el-descriptions>

    <el-descriptions title="处理器信息" :column="2" :size="size" border>
      <el-descriptions-item label="CPU核心数" label-align="right" min-width="200">
        {{ cpuInfo?.cpuNum }}
      </el-descriptions-item>
      <el-descriptions-item label="CPU系统使用率" label-align="right" min-width="200">
        {{ cpuInfo?.sys }}%
      </el-descriptions-item>
      <el-descriptions-item label="CPU用户使用率" label-align="right">
        {{ cpuInfo?.user }}%
      </el-descriptions-item>
      <el-descriptions-item label="CPU当前等待率" label-align="right">
        {{ cpuInfo?.wait }}%
      </el-descriptions-item>
      <el-descriptions-item label="CPU当前空闲率" label-align="right">
        {{ cpuInfo?.free }}%
      </el-descriptions-item>
      <el-descriptions-item label="CPU型号信息" label-align="right">
        <div style="white-space: pre-wrap;">
          {{ cpuInfo?.cpuModel }}
        </div>
      </el-descriptions-item>
    </el-descriptions>

    <el-descriptions title="内存信息" :column="1" :size="size" border>
      <el-descriptions-item label="总内存" label-align="right">
        {{ memoryInfo?.global }}
      </el-descriptions-item>
      <el-descriptions-item label="物理内存" label-align="right">
        <div style="white-space: pre-wrap;">
          {{ memoryInfo?.physical }}
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="虚拟内存" label-align="right">
        <div style="white-space: pre-wrap;">
          {{ memoryInfo?.virtual }}
        </div>
      </el-descriptions-item>
    </el-descriptions>


    <el-descriptions title="JVM 信息" :column="2" :size="size" border>
      <el-descriptions-item label="虚拟机名称" label-align="right" min-width="200">
        {{ jvmInfo?.jvmName }}
      </el-descriptions-item>
      <el-descriptions-item label="虚拟机版本" label-align="right" min-width="200">
        {{ jvmInfo?.jvmVersion }}
      </el-descriptions-item>
      <el-descriptions-item label="虚拟机厂商" label-align="right">
        {{ jvmInfo?.jvmVendor }}
      </el-descriptions-item>
      <el-descriptions-item label="JAVA_HOME" label-align="right">
        {{ jvmInfo?.javaHome }}
      </el-descriptions-item>
      <el-descriptions-item label="应用安装位置" label-align="right">
        {{ jvmInfo?.projectHome }}
      </el-descriptions-item>
      <el-descriptions-item label="进程 ID" label-align="right">
        {{ jvmInfo?.currentProcessIdentifier }}
      </el-descriptions-item>
      <el-descriptions-item label="启动时间" label-align="right">
        {{ jvmInfo?.startTime }}
      </el-descriptions-item>
      <el-descriptions-item label="运行时间" label-align="right">
        {{ timeAgo(jvmInfo?.runTimeSeconds) }}
      </el-descriptions-item>
      <el-descriptions-item label="JVM 最大内存" label-align="right">
        {{ jvmInfo?.maxMemory }}
      </el-descriptions-item>
      <el-descriptions-item label="JVM 已分配内存" label-align="right">
        {{ jvmInfo?.totalMemory }}
      </el-descriptions-item>
      <el-descriptions-item label="JVM 空闲内存" label-align="right">
        {{ jvmInfo?.freeMemory }}
      </el-descriptions-item>
      <el-descriptions-item label="JVM 剩余可用内存" label-align="right">
        {{ jvmInfo?.usableMemory }}
      </el-descriptions-item>
      <el-descriptions-item label="启动参数" label-align="right">
        {{ jvmInfo?.startArgs }}
      </el-descriptions-item>
    </el-descriptions>

    <div class="el-descriptions__title" style="margin: 30px 0px 15px 0px;">磁盘信息</div>
    <el-table v-loading="loading" :data="diskInfo" :size="size" stripe border show-overflow-tooltip
      highlight-current-row>
      <el-table-column prop="mountName" label="卷名称" min-width="120" />
      <el-table-column prop="diskFormat" label="磁盘格式" min-width="100" />
      <el-table-column prop="diskName" label="磁盘名称" min-width="180" />
      <el-table-column prop="total" label="磁盘总容量" min-width="100" />
      <el-table-column prop="free" label="磁盘剩余容量" min-width="100" />
      <el-table-column prop="used" label="磁盘已使用容量" min-width="100" />
      <el-table-column prop="usageRate" label="磁盘使用率" min-width="100" />
    </el-table>

  </el-scrollbar>

</template>

<style scoped>
.el-descriptions+.el-descriptions {
  margin-top: 25px;
}

.el-descriptions__extra>.el-button {
  margin-right: 20px;
}
</style>
