<script setup>
const props = defineProps({
  /** 要加载的目标路由路径，例如 /user/profile */
  routePath: {
    type: String,
    required: true,
  },
  /** 传递给目标组件的 readonly 属性 */
  readonly: {
    default: false,
    type: Boolean,
  },
  /** 业务数据 ID */
  businessId: {
    type: String,
    required: false,
    default: null,
  },
});

const router = useRouter();

const resolvedComp = ref(null);

/**
 * 根据 routePath 解析出最终要渲染的组件（异步组件也可直接使用）
 */
const resolveComponent = async () => {
  if (!props.routePath) {
    console.warn("[VtRoutePageLoader] 未指定要加载的路由路径");
    return null;
  }

  const resolved = router.resolve(props.routePath);
  const matched = resolved.matched;

  // 没有匹配到任何路由记录
  if (!matched.length) {
    console.warn(`[VtRoutePageLoader] 未匹配到路由记录：${props.routePath}`);
    return null;
  }

  // 通常嵌套路由下，最后一条 matched 记录包含页面组件
  const lastRecord = matched[matched.length - 1];
  let component = lastRecord.components?.default ?? null;

  if (!component) {
    console.warn(`[VtRoutePageLoader] 路由记录中未找到默认组件：${props.routePath}`);
    return null;
  }

  // 如果组件是一个函数（懒加载），需要调用它获取实际组件
  if (typeof component === "function") {
    try {
      const module = await component();
      // 兼容 ES Module default export 和 CommonJS module.exports
      component = module.default || module;
    } catch (error) {
      console.error(`[VtRoutePageLoader] 组件加载失败：${props.routePath}`, error);
      return null;
    }
  }

  // 返回一个 markRaw 对象，避免 Vue 尝试将组件转为代理对象
  return markRaw(component);
};

// 监听 routePath 变化，重新解析组件
watch(
  () => props.routePath,
  async (newPath) => {
    resolvedComp.value = null; // 先清空，避免显示旧组件
    if (newPath) {
      resolvedComp.value = await resolveComponent();
    }
  },
  { immediate: true },
);
</script>

<template>
  <!-- 
    1. 添加 key：确保 routePath 变化时，即使组件类型相同，也会强制重新创建实例（重置内部状态）
    2. v-if：仅在组件解析成功后渲染
  -->
  <component
    v-if="resolvedComp"
    :is="resolvedComp"
    :readonly="props.readonly"
    :businessId="props.businessId"
  />
</template>
