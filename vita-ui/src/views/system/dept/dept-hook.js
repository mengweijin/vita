export const columns = reactive({
  selection: { label: '选择列', visible: false },
  index: { label: '序号列', visible: false },
  id: { label: 'ID', visible: false },
  name: { label: '部门名称', visible: true },
  disabled: { label: '状态', visible: true },
  seq: { label: '排序', visible: true },
  remark: { label: '备注', visible: false },
  createByName: { label: '创建者', visible: true },
  createTime: { label: '创建时间', visible: true },
  updateByName: { label: '更新者', visible: true },
  updateTime: { label: '更新时间', visible: true },
  operation: { label: '操作', visible: true },
})
