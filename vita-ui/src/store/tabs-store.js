import utils from '@/utils/utils.js';

const { VITE_APP_PREFIX } = import.meta.env;

export const useTabsStore = defineStore(
  `${VITE_APP_PREFIX}-tabs`,
  () => {
    // state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
    // 标签页列表
    const tabsList = ref([
      {
        name: 'HomeView',
        path: '/',
        title: '首页',
        closable: false, // 首页不可关闭
      },
    ]);

    // 当前激活的标签页
    const activeTab = ref('HomeView');

    // method 可直接解构

    /**
     * 获取缓存的视图名称（用于 keep-alive）
     */
    const getCachedViews = () => {
      return tabsList.value
        .filter((tab) => tab.name !== 'HomeView') // 首页不缓存，因为始终存在
        .map((tab) => tab.name);
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
      const { name, path, meta } = route;
      const tab = {
        name,
        path,
        title: meta.title || '',
        closable: true, // 默认可关闭
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
     * 关闭标签页
     * @param {String} name
     */
    const closeTab = (name) => {
      // 首页不可关闭
      if (name === 'HomeView') {
        return;
      }

      const tabs = tabsList.value;

      let closeTabIndex = utils.findIndexOf(tabs, (tab) => tab.name === name);
      // 未找到标签页，直接返回
      if (closeTabIndex === -1) {
        return;
      }

      // 下一标签页或上一标签页
      const nextTab = tabs[closeTabIndex + 1] || tabs[closeTabIndex - 1];
      if (nextTab) {
        utils.remove(tabsList.value, (tab) => tab.name === name && tab.closable);
        activeTab.value = nextTab.name;
        return;
      }
    };

    // 关闭所有标签页
    const closeAllTabs = () => {
      tabsList.value = tabsList.value.filter((tab) => tab.name === 'HomeView');
      activeTab.value = 'HomeView';
    };

    /**
     * 关闭其他标签页
     */
    const closeOtherTabs = (route) => {
      tabsList.value = tabsList.value.filter((tab) => tab.name === 'HomeView' || tab.name === route.name);
    };

    // 关闭左侧标签页
    const closeLeftTabs = (route) => {
      const currentIndex = tabsList.value.findIndex((tab) => tab.name === route.name);
      tabsList.value = tabsList.value.filter((tab, index) => index >= currentIndex || tab.name === 'HomeView');
    };

    // 关闭右侧标签页
    const closeRightTabs = (route) => {
      const currentIndex = tabsList.value.findIndex((tab) => tab.name === route.name);
      tabsList.value = tabsList.value.filter((tab, index) => index <= currentIndex || tab.name === 'HomeView');
    };

    return {
      tabsList,
      activeTab,
      getCachedViews,
      setActiveTab,
      addTab,
      closeTab,
      closeAllTabs,
      closeOtherTabs,
      closeLeftTabs,
      closeRightTabs,
    };
  },
  {
    persist: {
      storage: sessionStorage, // 默认为 localStorage，也可以指定为 sessionStorage
    },
  },
);
