export function useForm() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    name: { label: "表单名称", visible: true },
    routePath: { label: "路由路径", visible: true },
    remark: { label: "备注", visible: true },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: false },
    updateByName: { label: "更新者", visible: true },
    updateTime: { label: "更新时间", visible: true },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
