<script setup>
import { useAppStore } from "@/store/app-store";
import TabsView from "./components/tabs-view.vue";
import LayFooter from "./lay-footer.vue";
import LayHeader from "./lay-header.vue";
import LaySide from "./lay-side.vue";

const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const asideWidth = computed(() => (sideMenuOpened.value ? "200px" : "64px"));
</script>

<template>
  <el-container>
    <el-header>
      <LayHeader />
    </el-header>
    <el-container>
      <el-aside :width="asideWidth" style="transition: width 0.3s;">
        <LaySide />
      </el-aside>
      <el-container>
        <el-main>
          <TabsView />
        </el-main>
        <el-footer>
          <LayFooter />
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-header {
  --el-header-padding: 0px 0px 0px 0px;
}

.el-main {
  padding: 0px;
  background-color: var(--vt-main-background-color);
}
</style>
