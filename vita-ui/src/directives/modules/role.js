import { useUserStore } from "@/store/user-store.js";

/**
 * 使用（在元素上增加指令）：
 *
 * 指定角色单个字符串：v-role="'role_admin'"
 * 指定角色字符串数组：v-role="['role_admin','role_guest']"
 * 指定角色字符串数组（用户需要拥有指定数组的所有角色才能显示）：v-role.all="['role_admin','role_guest']"
 * 指定角色字符串（用户拥有指定数组的任意一个角色即可显示）：v-role.any="['role_admin','role_guest']"
 *
 * 当数组不指定 modifiers 时，默认使用 all 方式。
 *
 * 若同时存在 v-if 与 v-role 指令，则 v-if 优先级更高。
 * 此时，需要把 v-if 单独提到最外层，用 template 标签包裹起来，比如：
 * <template v-if="true/false">
 *     <el-button v-role="'role_admin'">新增按钮</el-button>
 * </template>
 */
export default {
  mounted(el, binding) {
    // 这里使用 modifiers，则使用时应为：v-role.all（需要拥有所有） 或者：v-role.any（拥有任意一个即可）
    const modifiers = binding.modifiers;
    const { value } = binding;

    const userStore = useUserStore();

    if (value) {
      const anyMatch = !utils.isEmpty(modifiers) && modifiers.any;
      !userStore.hasRole(value, anyMatch) && el.parentNode?.removeChild(el);
    } else {
      throw new Error(
        "[Directive: v-role]: need roles! Like v-role=\"['role_admin','role_guest']\"",
      );
    }
  },
};
