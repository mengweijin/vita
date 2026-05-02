export function useLogDataChange() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: true },
    tableName: { label: "表名称", visible: true },
    businessId: { label: "业务数据 ID", visible: true },
    readableMessages: { label: "变更信息", visible: true },
    createByName: { label: "操作者", visible: true },
    createTime: { label: "操作时间", visible: true },
    updateByName: { label: "更新者", visible: false },
    updateTime: { label: "更新时间", visible: false },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
