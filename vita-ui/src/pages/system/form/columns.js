const columns = reactive({
  selection: { label: "选择列", visible: false },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: false },
  parentId: { label: "父级ID", visible: false },
  name: { label: "表单名称", visible: true },
  type: { label: "表单类型", visible: true },
  staticFormRoute: { label: "静态表单路径", visible: false },
  dynamicFormId: { label: "动态表单ID", visible: false },
  remark: { label: "备注", visible: true },
  createByName: { label: "创建者", visible: true },
  createTime: { label: "创建时间", visible: true },
  updateByName: { label: "更新者", visible: true },
  updateTime: { label: "更新时间", visible: true },
  operation: { label: "操作", visible: true },
});

export { columns };
