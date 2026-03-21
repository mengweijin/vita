<script setup>
import { homeApi } from "@/api/home-api.js";
import * as echarts from "echarts";

const chartDomRef = useTemplateRef("chartDomRef");

let chart = null;

const initChart = (category, activeUsers, userOperations) => {
	chart = echarts.init(chartDomRef.value);

	const options = {
		legend: {
			orient: "horizontal",
		},
		series: [
			{
				data: activeUsers ?? [],
				name: "日用户登录数",
				type: "bar",
			},
			{
				data: userOperations ?? [],
				name: "日用户操作数",
				type: "bar",
			},
		],
		tooltip: {
			// 背景色
			backgroundColor: "rgba(50,50,50,0.7)",
			borderWidth: 0,
			textStyle: { color: "#FFF" },
			// 悬浮触发类型（'item'：数据项触发）
			trigger: "item",
		},
		xAxis: {
			data: category ?? [],
			type: "category",
		},
		yAxis: {
			type: "value",
		},
	};
	chart.setOption(options);
};

onMounted(() => {
	homeApi.queryConsoleChart().then((res) => {
		initChart(res.category, res.activeUsers, res.userOperations);
	});
});

onUnmounted(() => {
	if (chart) {
		// 销毁实例
		chart.dispose();
	}
});
</script>

<template>
	<div>
		<div class="vt-chart-title">用户活跃度统计</div>
		<div ref="chartDomRef" class="vt-chart"></div>
	</div>

</template>

<style lang="css" scoped>
.vt-chart-title {
	margin-top: 20px;
	padding: 10px 0px 0px 15px;
	background-color: white;
}

.vt-chart {
	width: 100%;
	height: 100%;
	min-height: 450px;
	background-color: white;
}
</style>
