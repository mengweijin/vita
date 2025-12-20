import utils from "@/utils/utils.js";

const { VITE_APP_PREFIX, VITE_TABS_MAX_NUMBER } = import.meta.env;

export const useTabsStore = defineStore(
	`${VITE_APP_PREFIX}-tabs`,
	() => {
		// state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
		// 标签页列表
		const tabsList = ref([
			{
				closable: false,
				name: "HomeView",
				path: "/home",
				title: "首页",
			},
		]);

		// 当前激活的标签页
		const activeTab = ref("");

		// method 可直接解构

		/**
		 * 获取缓存的视图名称（用于 keep-alive）
		 */
		const getCachedViews = () => {
			return tabsList.value.filter((tab) => tab.closable === false).map((tab) => tab.name);
		};

		/**
		 * 获取当前激活的标签页名称
		 * @returns {String}
		 */
		const getActiveTab = () => {
			return activeTab.value;
		};

		/**
		 * 设置当前激活的标签页
		 * @param {String} name
		 */
		const setActiveTab = (name) => {
			activeTab.value = name;
		};

		/**
		 * 添加标签页
		 * @param {Object} route
		 */
		const addTab = (route) => {
			// 超出最大数量，移除最早添加且可关闭的标签页
			if (tabsList.value.length >= VITE_TABS_MAX_NUMBER) {
				const firstClosableTab = tabsList.value.find((tab) => tab.closable);
				if (firstClosableTab) {
					utils.remove(tabsList.value, (tab) => tab.name === firstClosableTab.name);
				}
			}

			const { name, path, meta } = route;
			const tab = {
				closable: meta.closable ?? true, // 默认可关闭
				name,
				path,
				title: meta.title || "",
			};

			// 检查是否已存在
			const exists = tabsList.value.some((item) => item.name === name);
			if (!exists) {
				tabsList.value.push(tab);
			}

			// 设置当前激活的标签页
			activeTab.value = name;
		};

		/**
		 * 移除标签页
		 * @param {String} name
		 */
		const removeTab = (name) => {
			const closedTabIndex = utils.findIndexOf(tabsList.value, (tab) => tab.name === name);
			// 未找到标签页，直接返回
			if (closedTabIndex === -1) {
				return;
			}

			// 如果移除的是当前激活的标签页，切换到下一个标签页或上一个标签页
			if (activeTab.value === name) {
				// 下一标签页或上一标签页
				const nextTab = tabsList.value[closedTabIndex + 1] || tabsList.value[closedTabIndex - 1];
				if (nextTab) {
					activeTab.value = nextTab.name;
				}
			}
			// 移除标签页
			utils.remove(tabsList.value, (tab) => tab.closable && tab.name === name);
		};

		// 移除所有标签页
		const removeAllTabs = () => {
			utils.remove(tabsList.value, (tab) => tab.closable === true);
			activeTab.value = tabsList.value[0]?.name;
		};

		/**
		 * 移除其他标签页
		 */
		const removeOtherTabs = (route) => {
			utils.remove(tabsList.value, (tab) => tab.closable && tab.name !== route.name);
		};

		// 移除左侧标签页
		const removeLeftTabs = (route) => {
			const currentIndex = tabsList.value.findIndex((tab) => tab.name === route.name);
			utils.remove(tabsList.value, (tab, index) => tab.closable && index < currentIndex);
		};

		// 移除右侧标签页
		const removeRightTabs = (route) => {
			const currentIndex = tabsList.value.findIndex((tab) => tab.name === route.name);
			utils.remove(tabsList.value, (tab, index) => tab.closable && index > currentIndex);
		};

		const clear = () => {
			tabsList.value = [];
			activeTab.value = "";
		};

		return {
			activeTab,
			addTab,
			clear,
			getActiveTab,
			getCachedViews,
			removeAllTabs,
			removeLeftTabs,
			removeOtherTabs,
			removeRightTabs,
			removeTab,
			setActiveTab,
			tabsList,
		};
	},
	{
		persist: {
			storage: sessionStorage,
		},
	},
);
