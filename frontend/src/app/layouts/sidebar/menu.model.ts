export interface MenuItem {
    id?: number;
    label?: string;
    icon?: string;
    link?: string;
    roles?: string;
    subItems?: any;
    isTitle?: boolean
    parentId?: number;
    isUiElement?: boolean;
}
