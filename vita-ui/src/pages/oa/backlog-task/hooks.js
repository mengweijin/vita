export function useFlowTask() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    definitionId: { label: "流程定义 ID", visible: false },
    instanceId: { label: "流程编号", visible: true },
    nodeCode: { label: "节点编码", visible: false },
    nodeName: { label: "节点名称", visible: true },
    nodeType: { label: "节点类型", visible: false },
    flowName: { label: "流程名称", visible: true },
    flowStatus: { label: "流程状态", visible: true },
    formCustom: { label: "自定义表单", visible: false },
    formPath: { label: "表单路径", visible: false },
    createByName: { label: "创建者", visible: true },
    createTime: { label: "创建时间", visible: true },
    updateByName: { label: "更新者", visible: true },
    updateTime: { label: "更新时间", visible: true },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
