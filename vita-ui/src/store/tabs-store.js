import utils from '@/utils/utils.js';

const { VITE_APP_PREFIX } = import.meta.env;

export const useTabsStore = defineStore(
  `${VITE_APP_PREFIX}-tabs`,
  () => {
    // state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
    // 标签页列表
    const tabsList = ref([]);

    // 当前激活的标签页
    const activeTab = ref('');

    // method 可直接解构

    /**
     * 添加标签页
     * @param {Object} route
     */
    const addTab = (route) => {
      const { name, path, meta } = route;
      const tab = {
        name,
        path,
        title: meta.title || '',
      };

      // 检查是否已存在
      const exists = tabsList.value.some((item) => item.name === name);
      if (!exists) {
        tabsList.value.push(tab);
      }

      // 设置当前激活的标签页
      activeTab.value = name;
    };

    const removeTab = (name) => {
      const tabs = tabsList.value;

      if (activeTab.value === name) {
        tabs.forEach((tab, index) => {
          if (tab.name === name) {
            const nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeTab.value = nextTab.name;
              utils.remove(tabsList.value, (item) => item.name === name);
              return;
            }
          }
        });
      }
    };

    return { tabsList, activeTab, addTab, removeTab };
  },
  {
    persist: {
      storage: sessionStorage, // 默认为 localStorage，也可以指定为 sessionStorage
    },
  },
);
