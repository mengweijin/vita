const { VITE_APP_PREFIX } = import.meta.env;

/**
 * 工作流表单页面数据
 * 以下几个字段需要在使用是全部明确赋值，以保证不被历史数据影响。
 */
export const useWorkflowFormPageStore = defineStore(
  `${VITE_APP_PREFIX}-workflow-form-page`,
  () => {
    const flowCode = ref(null);

    const tabTitle = ref("");

    const businessId = ref(null);

    const readonly = ref(false);

    const loading = ref(false);

    /**
     * 设置数据。
     * 使用 { flowCode: valFlowCode } 语法避免变量名冲突。
     * @param {Object} params - 配置对象
     * @param {String} params.flowCode - 流程编码 (必填)
     * @param {String} params.tabTitle - tab 页签标题 (必填)
     * @param {String} [params.businessId=null] - 业务 ID
     * @param {Boolean} [params.readonly=false] - 是否表单只读
     * @param {Boolean} [params.loading=false] - loading 状态
     */
    const setData = ({
      flowCode: valFlowCode,
      tabTitle: valTabTitle,
      businessId: valBusinessId = null,
      readonly: valReadonly = false,
      loading: valLoading = true,
    }) => {
      // 简单校验，防止关键数据缺失导致页面状态异常
      if (!valFlowCode || !valTabTitle) {
        console.error("WorkflowFormPageStore: flowCode and tabTitle are required.");
        return;
      }
      // 将传入的值赋给 store 中的 ref 变量
      flowCode.value = valFlowCode;
      tabTitle.value = valTabTitle;
      businessId.value = valBusinessId;
      readonly.value = valReadonly;
      loading.value = valLoading;
    };

    return { flowCode, tabTitle, businessId, readonly, loading, setData };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
