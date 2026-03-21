const columns = reactive({
  selection: { label: "选择列", visible: false },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: false },
  name: { label: "任务名称", visible: true },
  cron: { label: "CRON 表达式", visible: true },
  beanName: { label: "执行 Bean 名称", visible: true },
  args: { label: "执行参数", visible: true },
  disabled: { label: "状态", visible: true },
  remark: { label: "备注", visible: true },
  createByName: { label: "创建者", visible: false },
  createTime: { label: "创建时间", visible: false },
  updateByName: { label: "更新者", visible: false },
  updateTime: { label: "更新时间", visible: false },
  operation: { label: "操作", visible: true },
});

const taskLogColumns = reactive({
  selection: { label: "选择列", visible: true },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: true },
  schedulingTaskId: { label: "调度任务ID", visible: false },
  args: { label: "实际执行参数", visible: true },
  status: { label: "执行状态", visible: true },
  success: { label: "执行结果", visible: true },
  costTime: { label: "消耗时间（毫秒）", visible: true },
  message: { label: "附加信息", visible: true },
  createByName: { label: "创建者", visible: false },
  createTime: { label: "创建时间", visible: true },
  updateByName: { label: "更新者", visible: false },
  updateTime: { label: "更新时间", visible: true },
  operation: { label: "操作", visible: true },
});

export { columns, taskLogColumns };
