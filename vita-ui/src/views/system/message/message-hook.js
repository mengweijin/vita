export const columns = reactive({
	selection: { label: "选择列", visible: true },
	index: { label: "序号列", visible: false },
	messageId: { label: "ID", visible: false },
	title: { label: "标题", visible: true },
	content: { label: "内容", visible: true },
	category: { label: "消息分类", visible: true },
	createByName: { label: "发送者", visible: true },
	createTime: { label: "发送时间", visible: true },
	viewed: { label: "已读/未读", visible: true },
	viewedTime: { label: "查看时间", visible: false },
	operation: { label: "操作", visible: true },
});
