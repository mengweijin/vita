const columns = reactive({
  selection: { label: "选择列", visible: false },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: false },
  title: { label: "标题", visible: true },
  description: { label: "内容", visible: false },
  released: { label: "发布状态", visible: true },
  createByName: { label: "创建者", visible: true },
  createTime: { label: "创建时间", visible: true },
  updateByName: { label: "更新者", visible: true },
  updateTime: { label: "更新时间", visible: true },
  operation: { label: "操作", visible: true },
});

export { columns };
