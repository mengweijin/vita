import utils from "@/utils/utils.js";

/**
 * 使用（在元素上增加指令）：
 * v-device.pc
 * v-device.mobile
 * v-device.mobile.pc
 */
export default {
	mounted(el, binding) {
		// 这里使用 modifiers，则使用时应为：v-device.pc 或者多个：v-device.mobile.pc
		const modifiers = binding.modifiers;

		// 初始检查
		const checkDevice = () => {
			const currentDevice = utils.browse().isMobile ? "mobile" : "pc";
			const isMatch = Object.keys(modifiers).some((d) => d === currentDevice);
			// 匹配则显示，否则隐藏
			el.style.display = isMatch ? "" : "none";
		};

		// 执行
		checkDevice();
	},
};
