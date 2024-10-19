import "./reset.css";
import dayjs from "dayjs";
import roleForm from "../form/role.vue";
import editForm from "../form/index.vue";
import { zxcvbn } from "@zxcvbn-ts/core";
import { handleTree } from "@/utils/tree";
import userAvatar from "@/assets/user.jpg";
import { usePublicHooks } from "../../hooks";
import { addDialog } from "@/components/ReDialog";
import type { PaginationProps } from "@pureadmin/table";
import ReCropperPreview from "@/components/ReCropperPreview";
import type {
  FormItemProps,
  RoleFormItemProps,
  UserAvatarBO,
  UserVO
} from "../utils/types";
import {
  getKeyList,
  isAllEmpty,
  deviceDetection,
  cloneDeep
} from "@pureadmin/utils";
import { getDeptList } from "@/api/system/dept";
import {
  getUserPage,
  getSensitiveUserById,
  createUser,
  updateUser,
  resetUserPassword,
  setUserAvatar,
  setUserRoles,
  deleteUser
} from "@/api/system/user";
import { getRoleIdsByUserId, getAllRoleList } from "@/api/system/role";
import {
  ElForm,
  ElInput,
  ElFormItem,
  ElProgress,
  ElMessageBox
} from "element-plus";
import {
  type Ref,
  h,
  ref,
  toRaw,
  watch,
  computed,
  reactive,
  onMounted
} from "vue";
import ReDictTag from "@/components/ReDictTag";

export function useUser(tableRef: Ref, treeRef: Ref) {
  const form = reactive<UserVO>({});
  const formRef = ref();
  const ruleFormRef = ref();
  const dataList = ref([]);
  const loading = ref(true);
  // 上传头像信息
  const avatarInfo = ref();
  const switchLoadMap = ref({});
  const { switchStyle } = usePublicHooks();
  const higherDeptOptions = ref();
  const treeData = ref([]);
  const treeLoading = ref(true);
  const selectedNum = ref(0);
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true,
    pageSizes: [10, 20, 30, 50]
  });
  const columns: TableColumnList = [
    {
      label: "勾选列", // 如果需要表格多选，此处label必须设置
      type: "selection",
      fixed: "left",
      reserveSelection: true // 数据刷新后保留选项
    },
    {
      label: "用户编号",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "用户头像",
      prop: "avatar",
      cellRenderer: ({ row }) => (
        <el-image
          fit="cover"
          preview-teleported={true}
          src={row.avatar || userAvatar}
          preview-src-list={Array.of(row.avatar || userAvatar)}
          class="w-[24px] h-[24px] rounded-full align-middle"
        />
      ),
      width: 90
    },
    {
      label: "用户账号",
      prop: "username",
      minWidth: 130
    },
    {
      label: "用户昵称",
      prop: "nickname",
      minWidth: 130
    },
    {
      label: "性别",
      prop: "gender",
      minWidth: 90,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_user_gender"
          value={row.gender}
        ></ReDictTag>
      )
    },
    {
      label: "部门",
      prop: "deptName",
      minWidth: 100
    },
    {
      label: "电子邮箱",
      prop: "email",
      minWidth: 90
      //formatter: ({ email }) => hideTextAtIndex(email, { start: 1, end: 4 })
    },
    {
      label: "手机号码",
      prop: "mobile",
      minWidth: 90
      //formatter: ({ phone }) => hideTextAtIndex(phone, { start: 3, end: 6 })
    },
    {
      label: "状态",
      prop: "disabled",
      minWidth: 90,
      cellRenderer: scope => (
        <el-switch
          size={scope.props.size === "small" ? "small" : "default"}
          loading={switchLoadMap.value[scope.index]?.loading}
          v-model={scope.row.disabled}
          active-value={"N"}
          inactive-value={"Y"}
          active-text="已启用"
          inactive-text="已停用"
          inline-prompt
          style={switchStyle.value}
          before-change={() => beforeDisabledChange(scope as any)}
          // onChange={() => onChange(scope as any)}
        />
      )
    },
    {
      label: "简介/备注",
      prop: "remark",
      minWidth: 160,
      hide: true
    },
    {
      label: "创建者",
      minWidth: 100,
      prop: "createByName",
      hide: true
    },
    {
      label: "创建时间",
      minWidth: 160,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: true
    },
    {
      label: "更新者",
      minWidth: 100,
      prop: "updateByName",
      hide: true
    },
    {
      label: "更新时间",
      minWidth: 160,
      prop: "updateTime",
      formatter: ({ updateTime }) =>
        dayjs(updateTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: true
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];
  const buttonClass = computed(() => {
    return [
      "!h-[20px]",
      "reset-margin",
      "!text-gray-500",
      "dark:!text-white",
      "dark:hover:!text-primary"
    ];
  });

  function beforeDisabledChange({ row, index }) {
    ElMessageBox.confirm(
      `确认要<strong>【${
        row.disabled === "Y" ? "启用" : "停用"
      }】用户 </strong><strong style='color:var(--el-color-primary)'>${
        row.nickname
      }（${row.username}）</strong>吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    )
      .then(() => {
        switchLoadMap.value[index] = Object.assign(
          {},
          switchLoadMap.value[index],
          {
            loading: true
          }
        );
      })
      .then(() =>
        updateUser({
          id: row.id,
          disabled: row.disabled === "Y" ? "N" : "Y"
        } as FormItemProps).then(() => {
          row.disabled = row.disabled === "Y" ? "N" : "Y";
        })
      )
      .finally(() => {
        switchLoadMap.value[index] = Object.assign(
          {},
          switchLoadMap.value[index],
          {
            loading: false
          }
        );
      });
    return false;
  }

  // 重置的新密码
  const pwdForm = reactive({
    username: "",
    password: "",
    newPassword: ""
  });
  const pwdProgress = [
    { color: "#e74242", text: "非常弱" },
    { color: "#EFBD47", text: "弱" },
    { color: "#ffa500", text: "一般" },
    { color: "#1bbf1b", text: "强" },
    { color: "#008000", text: "非常强" }
  ];
  // 当前密码强度（0-4）
  const curScore = ref();
  const roleOptions = ref([]);

  function handleUpdate(row) {
    console.log(row);
  }

  function handleDelete(row) {
    deleteUser(row.id).then(r => {
      if (r.code === 200) {
        onSearch();
      }
    });
  }

  function handleSizeChange(val: number) {
    pagination.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    pagination.currentPage = val;
    onSearch();
  }

  /** 当CheckBox选择项发生变化时会触发该事件 */
  function handleSelectionChange(val) {
    selectedNum.value = val.length;
    // 重置表格高度
    tableRef.value.setAdaptive();
  }

  /** 取消选择 */
  function onSelectionCancel() {
    selectedNum.value = 0;
    // 用于多选表格，清空用户的选择
    tableRef.value.getTableRef().clearSelection();
  }

  /** 批量删除 */
  function onBatchDel() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteUser(ids).then(r => {
      if (r.code === 200) {
        tableRef.value.getTableRef().clearSelection();
        onSearch();
      }
    });
  }

  async function onSearch() {
    loading.value = true;
    form.current = pagination.currentPage;
    form.size = pagination.pageSize;
    const page = await getUserPage(toRaw(form));
    dataList.value = page.records;
    pagination.total = page.total;
    pagination.pageSize = page.size;
    pagination.currentPage = page.current;

    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    form.deptId = "";
    treeRef.value.onTreeReset();
    onSearch();
  };

  function onTreeSelect({ id, selected }) {
    form.deptId = selected ? id : "";
    onSearch();
  }

  function formatHigherDeptOptions(treeList) {
    // 根据返回数据的status字段值判断追加是否禁用disabled字段，返回处理后的树结构，用于上级部门级联选择器的展示（实际开发中也是如此，不可能前端需要的每个字段后端都会返回，这时需要前端自行根据后端返回的某些字段做逻辑处理）
    if (!treeList || !treeList.length) return;
    const newTreeList = [];
    for (let i = 0; i < treeList.length; i++) {
      treeList[i].disabled = treeList[i].status === 0 ? true : false;
      formatHigherDeptOptions(treeList[i].children);
      newTreeList.push(treeList[i]);
    }
    return newTreeList;
  }

  async function openDialog(title = "新增", row?: FormItemProps) {
    let newRow = cloneDeep(row);
    if (row?.id) {
      const user = await getSensitiveUserById(row.id);
      newRow.mobile = user?.mobile;
      newRow.email = user?.email;
    }
    addDialog({
      title: `${title}用户`,
      props: {
        formInline: {
          id: newRow?.id ?? null,
          deptId: newRow?.deptId ?? 1,
          nickname: newRow?.nickname ?? "",
          username: newRow?.username ?? "",
          password: newRow?.password ?? null,
          gender: newRow?.gender ?? "",
          mobile: newRow?.mobile ?? null,
          email: newRow?.email ?? null,
          disabled: newRow?.disabled ?? "N",
          remark: newRow?.remark ?? ""
        },
        higherDeptOptions: formatHigherDeptOptions(higherDeptOptions.value)
      },
      width: "46%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () =>
        h(editForm, {
          ref: formRef,
          formInline: null,
          higherDeptOptions: null
        }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            if (curData.id) {
              updateUser(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            } else {
              createUser(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            }
          }
        });
      }
    });
  }

  const cropRef = ref();
  /** 上传头像 */
  function handleUpload(row) {
    addDialog({
      title: "裁剪、上传头像",
      width: "40%",
      closeOnClickModal: false,
      fullscreen: deviceDetection(),
      contentRenderer: () =>
        h(ReCropperPreview, {
          ref: cropRef,
          imgSrc: row.avatar || userAvatar,
          onCropper: info => (avatarInfo.value = info)
        }),
      beforeSure: done => {
        const userAvatar = {
          userId: row.id,
          avatar: avatarInfo.value.base64
        } as UserAvatarBO;
        setUserAvatar(userAvatar).then(r => {
          if (r.code == 200) {
            done(); // 关闭弹框
            onSearch(); // 刷新表格数据
          }
        });
      },
      closeCallBack: () => cropRef.value.hidePopover()
    });
  }

  watch(
    pwdForm,
    ({ newPassword }) =>
      (curScore.value = isAllEmpty(newPassword)
        ? -1
        : zxcvbn(newPassword).score)
  );

  /** 重置密码 */
  function handleReset(row) {
    addDialog({
      title: `重置【${row.nickname} （${row.username}）】用户的密码`,
      width: "30%",
      draggable: true,
      closeOnClickModal: false,
      fullscreen: deviceDetection(),
      contentRenderer: () => (
        <>
          <ElForm ref={ruleFormRef} model={pwdForm}>
            <ElFormItem
              prop="newPassword"
              rules={[
                {
                  required: true,
                  message: "请输入新密码",
                  trigger: "blur"
                }
              ]}
            >
              <ElInput
                clearable
                show-password
                autocomplete="new-password"
                type="password"
                v-model={pwdForm.newPassword}
                placeholder="请输入新密码"
              />
            </ElFormItem>
          </ElForm>
          <div class="mt-4 flex">
            {pwdProgress.map(({ color, text }, idx) => (
              <div
                class="w-[19vw]"
                style={{ marginLeft: idx !== 0 ? "4px" : 0 }}
              >
                <ElProgress
                  striped
                  striped-flow
                  duration={curScore.value === idx ? 6 : 0}
                  percentage={curScore.value >= idx ? 100 : 0}
                  color={color}
                  stroke-width={10}
                  show-text={false}
                />
                <p
                  class="text-center"
                  style={{ color: curScore.value === idx ? color : "" }}
                >
                  {text}
                </p>
              </div>
            ))}
          </div>
        </>
      ),
      closeCallBack: () => (pwdForm.newPassword = ""),
      beforeSure: done => {
        ruleFormRef.value.validate(valid => {
          if (valid) {
            pwdForm.username = row.username;
            pwdForm.password = pwdForm.newPassword;
            resetUserPassword(pwdForm).then(r => {
              if (r.code === 200) {
                done(); // 关闭弹框
                onSearch(); // 刷新表格数据
              }
            });
          }
        });
      }
    });
  }

  /** 分配角色 */
  async function handleRole(row) {
    // 选中的角色列表
    const roleIds = (await getRoleIdsByUserId(row.id)) ?? [];
    addDialog({
      title: `分配 ${row.username} 用户的角色`,
      props: {
        formInline: {
          userId: row?.id ?? "",
          nickname: row?.nickname ?? "",
          roleIds: roleIds
        },
        roleOptions: roleOptions.value ?? []
      },
      width: "400px",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () =>
        h(roleForm, {
          formInline: null,
          roleOptions: null
        }),
      beforeSure: (done, { options }) => {
        const curData = options.props.formInline as RoleFormItemProps;
        setUserRoles(curData).then(r => {
          if (r.code == 200) {
            done(); // 关闭弹框
          }
        });
      }
    });
  }

  onMounted(async () => {
    treeLoading.value = true;
    onSearch();

    // 归属部门
    const data = await getDeptList();
    higherDeptOptions.value = handleTree(data);
    treeData.value = handleTree(data);
    treeLoading.value = false;

    // 角色列表
    roleOptions.value = await getAllRoleList();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    treeData,
    treeLoading,
    selectedNum,
    pagination,
    buttonClass,
    deviceDetection,
    onSearch,
    resetForm,
    onBatchDel,
    openDialog,
    onTreeSelect,
    handleUpdate,
    handleDelete,
    handleUpload,
    handleReset,
    handleRole,
    handleSizeChange,
    onSelectionCancel,
    handleCurrentChange,
    handleSelectionChange
  };
}
