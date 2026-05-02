export function useDict() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    name: { label: "字典名称", visible: true },
    code: { label: "字典编码", visible: true },
    remark: { label: "备注", visible: true },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: false },
    updateByName: { label: "更新者", visible: true },
    updateTime: { label: "更新时间", visible: true },
    operation: { label: "操作", visible: true },
  });

  const dictDataColumns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    code: { label: "字典编码", visible: false },
    label: { label: "字典标签", visible: true },
    val: { label: "字典值", visible: true },
    tag: { label: "标签样式", visible: true },
    seq: { label: "排序", visible: true },
    disabled: { label: "字典状态", visible: true },
    remark: { label: "备注", visible: true },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: false },
    updateByName: { label: "更新者", visible: true },
    updateTime: { label: "更新时间", visible: true },
    operation: { label: "操作", visible: true },
  });

  return { columns, dictDataColumns };
}
