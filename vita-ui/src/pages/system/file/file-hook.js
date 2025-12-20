export const columns = reactive({
	createByName: { label: "创建者", visible: true },
	createTime: { label: "创建时间", visible: true },
	id: { label: "ID", visible: false },
	index: { label: "序号列", visible: false },
	md5: { label: "MD5", visible: true },
	name: { label: "文件名称", visible: true },
	operation: { label: "操作", visible: true },
	selection: { label: "选择列", visible: false },
	storagePath: { label: "存储路径", visible: true },
	suffix: { label: "文件后缀", visible: true },
	updateByName: { label: "更新者", visible: false },
	updateTime: { label: "更新时间", visible: false },
});
