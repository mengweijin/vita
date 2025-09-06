import { isEmpty } from 'xe-utils';
import { useUserStore } from '@/store/user-store';

/**
 * 使用（在元素上增加指令）：
 *
 * 指定权限单个字符串：v-permission="'system:user:create'"
 * 指定权限字符串数组：v-permission="['system:user:create','system:user:update']"
 * 指定权限字符串数组（用户需要拥有指定数组的所有权限才能显示）：v-permission.all="['system:user:create','system:user:update']"
 * 指定权限字符串（用户拥有指定数组的任意一个权限即可显示）：v-permission.any="['system:user:create','system:user:update']"
 *
 * 当数组不指定 modifiers 时，默认使用 all 方式。
 *
 * 若同时存在 v-if 与 v-permission 指令，则 v-if 优先级更高。
 * 此时，需要把 v-if 单独提到最外层，用 template 标签包裹起来，比如：
 * <template v-if="true/false">
 *     <el-button v-permission="'system:user:create'">新增按钮</el-button>
 * </template>
 */
export default {
  mounted(el, binding) {
    // 这里使用 modifiers，则使用时应为：v-permission.all（需要拥有所有） 或者：v-permission.any（拥有任意一个即可）
    const modifiers = binding.modifiers;
    const { value } = binding;

    const userStore = useUserStore();

    const hasPermission = () => {
      const permissionList = userStore.getPermissions() || [];

      const valueArray = Array.isArray(value) ? value : [value];

      if (isEmpty(modifiers) || modifiers.all) {
        return valueArray.every((perm) => permissionList.includes(perm));
      } else if (modifiers.any) {
        return valueArray.some((perm) => permissionList.includes(perm));
      }

      return false;
    };

    if (value) {
      !hasPermission(value) && el.parentNode?.removeChild(el);
    } else {
      throw new Error(
        "[Directive: v-permission]: need permission! Like v-permission=\"['system:user:create','system:user:update']\"",
      );
    }
  },
};
