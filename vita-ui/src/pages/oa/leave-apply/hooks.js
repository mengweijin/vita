export function useLeaveApply() {
  const columns = reactive({
    selection: { label: "选择列", visible: false },
    index: { label: "序号列", visible: false },
    id: { label: "ID", visible: false },
    leaveType: { label: "休假类型", visible: true },
    startTime: { label: "开始时间", visible: true },
    endTime: { label: "结束时间", visible: true },
    leaveDays: { label: "休假天数", visible: true },
    remark: { label: "备注", visible: true },
    attachmentIds: { label: "附件", visible: false },
    workflowId: { label: "流程 ID", visible: false },
    createByName: { label: "创建者", visible: false },
    createTime: { label: "创建时间", visible: true },
    updateByName: { label: "更新者", visible: true },
    updateTime: { label: "更新时间", visible: true },
    operation: { label: "操作", visible: true },
  });

  return { columns };
}
