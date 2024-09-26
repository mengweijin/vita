interface FormItemProps {
  id?: string;
  /** 菜单类型（0代表菜单、1代表iframe、2代表外链、3代表按钮）*/
  type?: string;
  parentId?: number;
  title?: string;
  routerName?: string;
  routerPath?: string;
  componentPath?: string;
  seq?: number;
  redirect?: string;
  icon?: string;
  extraIcon?: string;
  enterTransition?: string;
  leaveTransition?: string;
  activePath?: string;
  permission?: string;
  iframeSrc?: string;
  iframeLoading?: boolean;
  keepAlive?: boolean;
  hiddenTag?: boolean;
  fixedTag?: boolean;
  showLink?: boolean;
  showParent?: boolean;
}
interface FormProps {
  formInline: FormItemProps;
  higherMenuOptions: Record<string, unknown>[];
}

interface MenuVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, MenuVO };
