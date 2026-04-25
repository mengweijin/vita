import { useLoginStore } from "@/store/login-store.js";
import { useMessageStore } from "@/store/message-store.js";
import { Icon } from "@iconify/vue";
import { SSE } from "sse.js";

const { VITE_BASE_API } = import.meta.env;
const { VITE_APP_PREFIX } = import.meta.env;

export const useSseStore = defineStore(
  `${VITE_APP_PREFIX}-sse`,
  () => {
    /**
     * 原生 EventSource 的 readyState 各个值的含义：
     * 0：CONNECTING
     * 1：OPEN
     * 2: CLOSED
     * 例如：关闭连接可设置：eventSource.value.readyState = 2;
     */
    const eventSource = ref(null);

    /**
     * 连接 SSE 服务器
     */
    const connect = () => {
      // 先断开已有连接
      disconnect();
      // 处理路径 SSE URL
      const url =
        `${VITE_BASE_API}/monitor/sse/subscribe?t=${Date.now()}`.replace(
          "//",
          "/",
        );

      // 创建SSE连接，关键步骤：在 headers 中传递 Token
      eventSource.value = new SSE(url, {
        autoReconnect: true,
        headers: {
          Authorization: useLoginStore().getBearerToken(),
        },
        reconnectDelay: 3000,
        start: true,
        withCredentials: true,
      });

      eventSource.value.addEventListener("open", (_event) => {
        // 后台第一次有消息时才打开连接，在这之前 eventSource.value.readyState = 0;
        console.log("Connection established");
      });

      eventSource.value.addEventListener("error", (event) => {
        // 连接发生错误
        console.error("SSE连接错误:", event);
      });

      eventSource.value.addEventListener("message", (event) => {
        // 服务器发送的消息会在这里触发
        ElMessage.success({
          dangerouslyUseHTMLString: true,
          duration: 10000,
          icon: h(Icon, {
            height: 24,
            icon: "ep:chat-line-round",
            width: 24,
          }),
          message: `<h4>您有新的消息，请注意查看！</h4> <br> ${event.data}`,
          // 设置到视口边缘的距离（当位置为'top'时为顶部，当位置为'bottom'时为底部）默认：16
          offset: 70,
          placement: "top-right",
          showClose: true,
        });

        const messageStore = useMessageStore();
        const { notViewedCount } = storeToRefs(messageStore);
        notViewedCount.value = notViewedCount.value + 1;
      });
    };

    const disconnect = () => {
      if (eventSource.value?.readyState !== undefined) {
        // 原生 EventSource，设置 readyState 为 2 (CLOSED)
        eventSource.value.readyState = 2;
      }
      eventSource.value = null;
    };

    const clear = () => {
      disconnect();
    };

    return {
      clear,
      connect,
      disconnect,
      eventSource,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
