import { MenuItem } from './menu.model';

export const MENU: MenuItem[] = [
    {
        id: 1,
        label: 'MENUITEMS.MENU.TEXT',
        isTitle: true,
        roles: 'NONE'
    },
    {
        id: 2,
        label: 'MENUITEMS.DASHBOARD.TEXT',
        icon: 'bx-home-circle',
        link: '/dashboard',
        roles: 'HR|ADMIN|RECEPTION'
    },
    {
        id: 3,
        label: 'MENUITEMS.TEST.TEXT',
        icon: 'bx-home-circle',
        link: '/test',
        roles: 'ADMIN'
    },

    {
        id: 4,
        label: 'MENUITEMS.USERS.TEXT',
        icon: 'bx-body',
        link: '/test',
        roles: 'HR',
        subItems: [
            {
                id: 100,
                label: 'MENUITEMS.USERS.LIST.USER_MANAGEMENT',
                link: '/userList',
                parentId: 4,
            }
        ]
    },

    {
        id: 5,
        label: 'MENUITEMS.SALES.TEXT',
        icon: 'bx-dollar-circle',
        link: '/test',
        roles: 'RECEPTION',
        subItems: [
            {
                id: 101,
                label: 'MENUITEMS.SALES.LIST.SALESMANAGEMENT',
                link: '/rawMaterial',
                parentId: 4,
                roles: 'RECEPTION|ADMIN'
            },
            {
                id: 102,
                label: 'MENUITEMS.SALES.LIST.PRODUCTMANAGEMENT',
                link: '/productList',
                parentId: 4,
                roles: 'RECEPTION|ADMIN'
            },
            {
                id: 103,
                label: 'MENUITEMS.SALES.LIST.SUPPLIERMANAGEMENT',
                link: '/supplierList',
                parentId: 4,
                roles: 'RECETPTION|ADMIN'
            },
            {
                id: 104,
                label: 'MENUITEMS.SALES.LIST.FACTORY',
                link: '/factoryList',
                parentId: 4,
                roles: 'RECETPTION|ADMIN'
            },
            {
                id: 104,
                label: 'MENUITEMS.SALES.LIST.RECEIPT',
                link: '/receiptList',
                parentId: 4,
                roles: 'RECETPTION|ADMIN'
            }
        ]
    },

    {
        id: 6,
        label: 'MENUITEMS.INVOICES.TEXT',
        icon: 'bx-calculator',
        link: '/test',
        roles: 'ADMIN',
        subItems: [
            {
                id: 102,
                label: 'MENUITEMS.INVOICES.LIST.INVOICE_MANAGEMENT',
                link: '/test',
                parentId: 6
            }
        ]
    }
];

