export function useGenerator() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: true },
    name: { label: "表名称", visible: true },
    havePrimaryKey: { label: "是否有主键", visible: true },
    fieldNames: { label: "字段名称", visible: true },
    comment: { label: "表注释", visible: true },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
