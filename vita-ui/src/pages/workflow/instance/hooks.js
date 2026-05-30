export function useFlowInstance() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    definitionId: { label: "流程定义ID", visible: false },
    flowName: { label: "流程名称", visible: true },
    businessId: { label: "业务ID", visible: true },
    nodeType: { label: "节点类型", visible: true },
    nodeCode: { label: "流程节点编码", visible: true },
    nodeName: { label: "流程节点名称", visible: true },
    variable: { label: "流程变量", visible: true },
    flowStatus: { label: "流程状态", visible: true },
    activityStatus: { label: "流程激活状态", visible: true },
    formCustom: { label: "审批表单是否自定义", visible: true },
    formPath: { label: "审批表单路径", visible: true },
    defJson: { label: "流程定义json", visible: false },
    ext: { label: "扩展字段", visible: false },
    delFlag: { label: "删除标记", visible: false },
    tenantId: { label: "租户ID", visible: false },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: false },
    updateByName: { label: "更新者", visible: false },
    updateTime: { label: "更新时间", visible: false },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
