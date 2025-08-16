<script setup>
import * as echarts from 'echarts';
import { homeApi } from '@/api/home-api';

const chartDomRef = ref(null);

let chart = null;

const initChart = (category, activeUsers, userOperations) => {
  chart = echarts.init(chartDomRef.value);

  let options = {
    legend: {
      orient: 'horizontal',
    },
    tooltip: {
      // 悬浮触发类型（'item'：数据项触发）
      trigger: 'item',
      // 背景色
      backgroundColor: 'rgba(50,50,50,0.7)',
      borderWidth: 0,
      textStyle: { color: '#FFF' }
    },
    xAxis: {
      type: 'category',
      data: category ?? [],
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        type: 'bar',
        name: '日用户登录数',
        data: activeUsers ?? [],
      },
      {
        type: 'bar',
        name: '日用户操作数',
        data: userOperations ?? [],
      },
    ]
  };
  chart.setOption(options);
}

onMounted(() => {
  homeApi.getConsoleChart().then((res) => {
    initChart(res.category, res.activeUsers, res.userOperations);
  });
});

onUnmounted(() => {
  if (chart) {
    // 销毁实例
    chart.dispose();
  }
})
</script>

<template>
  <div ref="chartDomRef" class="vt-chart"></div>
</template>

<style lang="css" scoped>
.vt-chart {
  width: 100%;
  height: 400px;
  margin-top: 20px;
  background-color: white;
}
</style>
