<script setup>
const { menuList } = defineProps({
  menuList: {
    type: Array,
    default: () => [],
  },
});

const handleClick = (menu) => {
  if ('IFRAME' === menu.type) {

  } else if ('URL' === menu.type) {
    window.open(menu.component, '_blank');
  }
};
</script>

<template>
  <template v-for="item in menuList" :key="item.routePath">
    <!-- 有子菜单时渲染 el-sub-menu -->
    <el-sub-menu v-if="item.children?.length" :index="item.routePath">
      <template #title>
        <Icon v-if="item.icon" :icon="item.icon" width="24" height="24" />
        <span>{{ item.title }}</span>
      </template>
      <!-- 递归调用自身 -->
      <MenuTree :menu-list="item.children" />
    </el-sub-menu>

    <!-- 无子菜单 el-menu-item -->
    <el-menu-item v-else :index="item.routePath" @click="handleClick(item)">
      <Icon v-if="item.icon" :icon="item.icon" width="24" height="24" />
      <span>{{ item.title }}</span>
    </el-menu-item>
  </template>
</template>

<style scoped>
.el-menu-item>span,
.el-sub-menu__title>span {
  padding-left: 5px;
}
</style>
