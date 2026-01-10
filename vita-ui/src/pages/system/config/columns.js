const columns = reactive({
  selection: { label: "选择列", visible: false },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: false },
  configKey: { label: "配置键", visible: true },
  configValue: { label: "配置值", visible: true },
  remark: { label: "备注", visible: true },
  createByName: { label: "创建者", visible: false },
  createTime: { label: "创建时间", visible: false },
  updateByName: { label: "更新者", visible: false },
  updateTime: { label: "更新时间", visible: false },
  operation: { label: "操作", visible: true },
});

export { columns };
