import { SSE } from "sse.js";
import { sseApi } from "@/api/monitor/sse-api.js";

const { VITE_BASE_API } = import.meta.env;
const { VITE_APP_PREFIX } = import.meta.env;

export const useSseStore = defineStore(
	`${VITE_APP_PREFIX}-sse`,
	() => {
		const sseConnected = ref(false);

		const eventSourceRef = ref(null);

		/**
		 * 连接 SSE 服务器
		 */
		const connect = async (token) => {
			console.log("连接 SSE 服务器中......");
			// 先断开已有连接（如果存在）
			disconnect();

			// SSE URL
			let url = `${window.location.origin}${VITE_BASE_API}/monitor/sse/subscribe?t=${Date.now()}`;

			// 创建SSE连接，关键步骤：在 headers 中传递 Token
			let eventSource = new SSE(url, {
				start: true,
				autoReconnect: false,
				headers: {
					Authorization: `Bearer ${token}`,
				},
			});

			eventSource.addEventListener("open", function (event) {
				// 连接成功建立
				console.log("SSE连接已打开");
				sseConnected.value = true;
			});

			eventSource.addEventListener("error", function (event) {
				// 连接发生错误
				console.error("SSE连接错误:", event);
			});

			eventSource.addEventListener("message", function (event) {
				// 服务器发送的消息会在这里触发
				ElMessage.primary({
					message: `您有新的消息，请注意查看！<br> ${event.data}`,
					dangerouslyUseHTMLString: true,
					duration: 5000,
					showClose: true,
					placement: "top-right",
					icon: '<Icon icon="ep:chat-line-round" width="24" height="24" />',
				});
			});

			eventSourceRef.value = eventSource;
		};

		/**
		 * 断开 SSE 连接
		 */
		const disconnect = () => {
			if (eventSourceRef.value) {
				eventSourceRef.value.close();
				console.log("SSE 连接已关闭");
				// 通知后端关闭连接
				sseApi.close();
			}
			eventSourceRef.value = null;
			sseConnected.value = false;
		};

		return {
			sseConnected,
			eventSourceRef,
			connect,
			disconnect,
		};
	},
	{
		persist: {
			storage: sessionStorage,
		},
	},
);
