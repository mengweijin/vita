export function useLogSystem() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    loggerLevel: { label: "日志级别", visible: true },
    threadName: { label: "线程名称", visible: false },
    loggerName: { label: "日志名称", visible: false },
    formattedMessage: { label: "日志内容", visible: true },
    stackTrace: { label: "堆栈信息", visible: false },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: true },
    updateByName: { label: "更新者", visible: false },
    updateTime: { label: "更新时间", visible: false },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
