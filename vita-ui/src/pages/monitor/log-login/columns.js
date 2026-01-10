const columns = reactive({
  selection: { label: "选择列", visible: false },
  index: { label: "序号列", visible: false },
  id: { label: "ID", visible: false },
  username: { label: "登录账号", visible: true },
  loginType: { label: "登录类型", visible: true },
  ip: { label: "IP", visible: true },
  ipLocation: { label: "登录位置", visible: true },
  browser: { label: "浏览器", visible: true },
  platform: { label: "设备平台", visible: true },
  os: { label: "操作系统", visible: true },
  success: { label: "操作状态", visible: true },
  errorMsg: { label: "失败信息", visible: false },
  createByName: { label: "操作者", visible: true },
  createTime: { label: "操作时间", visible: true },
  updateByName: { label: "更新者", visible: false },
  updateTime: { label: "更新时间", visible: false },
  operation: { label: "操作", visible: true },
});

export { columns };
