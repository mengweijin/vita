import { useEventBus } from "@vueuse/core";

// 创建并导出一个事件总线实例（可以带一个命名空间，避免冲突）
export const dialogPageLoaderWorkflowBus = useEventBus("vt-dialog-page-loader-workflow-bus");
