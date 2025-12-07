<script setup>
import { computed } from "vue";
import { useTabsStore } from "@/store/tabs-store.js";

const props = defineProps({
	currentTab: Object,
	menuStyle: Object,
	visible: Boolean,
});

const emit = defineEmits(["close-current", "close-others", "close-all", "close-left", "close-right"]);

const tabsStore = useTabsStore();

// 计算属性
const isOnlyHome = computed(() => {
	return tabsStore.tabsList.length === 1; // 只有首页
});

const isOnlyHomeAndCurrent = computed(() => {
	return (
		tabsStore.tabsList.length <= 2 && // 只有首页和当前页签
		props.currentTab?.name !== "Home"
	);
});

const isFirstTab = computed(() => {
	if (!props.currentTab) return true;
	const index = tabsStore.tabsList.findIndex((tab) => tab.name === props.currentTab.name);
	return index <= 1; // 首页(0)或第一个可关闭页签(1)
});

const isLastTab = computed(() => {
	if (!props.currentTab) return true;
	const index = tabsStore.tabsList.findIndex((tab) => tab.name === props.currentTab.name);
	return index === tabsStore.tabsList.length - 1;
});

// 事件处理
const handleCloseCurrent = () => {
	if (props.currentTab?.closable) {
		emit("close-current");
	}
};

const handleCloseOthers = () => {
	if (!isOnlyHomeAndCurrent.value) {
		emit("close-others");
	}
};

const handleCloseAll = () => {
	if (!isOnlyHome.value) {
		emit("close-all");
	}
};

const handleCloseLeft = () => {
	if (!isFirstTab.value && props.currentTab?.closable) {
		emit("close-left");
	}
};

const handleCloseRight = () => {
	if (!isLastTab.value && props.currentTab?.closable) {
		emit("close-right");
	}
};
</script>

<template>
  <div v-if="visible" class="context-menu" :style="menuStyle" @click.stop>
    <div class="context-menu-item" :class="{ disabled: !currentTab.closable }" @click="handleCloseCurrent">
      关闭当前
    </div>
    <div class="context-menu-item" :class="{ disabled: isOnlyHomeAndCurrent }" @click="handleCloseOthers">
      关闭其他
    </div>
    <div class="context-menu-item" :class="{ disabled: isOnlyHome }" @click="handleCloseAll">
      关闭所有
    </div>
    <div class="context-menu-divider"></div>
    <div class="context-menu-item" :class="{ disabled: isFirstTab || !currentTab.closable }" @click="handleCloseLeft">
      关闭左侧
    </div>
    <div class="context-menu-item" :class="{ disabled: isLastTab || !currentTab.closable }" @click="handleCloseRight">
      关闭右侧
    </div>
  </div>
</template>

<style scoped>
.context-menu {
  position: fixed;
  z-index: 9999;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 5px 0;
  min-width: 120px;
}

.context-menu-item {
  padding: 8px 16px;
  cursor: pointer;
  font-size: 14px;
  color: #606266;
}

.context-menu-item:hover:not(.disabled) {
  background-color: #ecf5ff;
  color: #409eff;
}

.context-menu-item.disabled {
  color: #c0c4cc;
  cursor: not-allowed;
  background: none;
}

.context-menu-divider {
  height: 1px;
  background: #e4e7ed;
  margin: 5px 0;
}
</style>
