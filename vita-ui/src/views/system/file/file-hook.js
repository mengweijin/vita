export const columns = reactive({
  selection: { label: '选择列', visible: false },
  index: { label: '序号列', visible: false },
  id: { label: 'ID', visible: false },
  name: { label: '文件名称', visible: true },
  suffix: { label: '文件后缀', visible: true },
  storagePath: { label: '存储路径', visible: true },
  md5: { label: 'MD5', visible: true },
  createByName: { label: '创建者', visible: true },
  createTime: { label: '创建时间', visible: true },
  updateByName: { label: '更新者', visible: false },
  updateTime: { label: '更新时间', visible: false },
  operation: { label: '操作', visible: true },
});
