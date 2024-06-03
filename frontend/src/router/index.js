// 引入 Vue 3 的路由模块
import {createRouter, createWebHistory} from 'vue-router';

// 定义路由
const routes = [
    {
        path: '/',
        //redirect: '/creditCard/customer/login'
        redirect: '/counter/cashier/login'
    },
    {
        path: '/counter/admin/login',
        name: 'loginAdminOfCashier',
        component:()=>import('../views/Counter/login/AdminLoginView.vue')
    },
    {
        path: '/counter/cashier/login',
        name: 'loginCashier',
        component:()=>import('../views/Counter/login/CashierLoginView.vue')
    },
    {
        path: '/counter/admin/cashier',
        name: 'adminManageCashier',
        component:()=>import('../views/Counter/admin/ManageCashierView.vue')
    },
    {
        path: '/counter/cashier/currentDeposit',
        name: 'cashierCurrentDeposit',
        component:()=>import('../views/Counter/cashier/CurrentDepositView.vue')
    },
    {
        path: '/counter/cashier/currentWithdrawal',
        name: 'cashierCurrentWithdrawal',
        component:()=>import('../views/Counter/cashier/CurrentWithdrawalView.vue')
    },
    {
        path: '/counter/cashier/freeAndUnfreeze',
        name: 'cashierFreeAndUnfreeze',
        component:()=>import('../views/Counter/cashier/FreezeAndUnfreezewView.vue')
    },
    {
        path: '/counter/cashier/lossAndReissue',
        name: 'cashierLossAndReissue',
        component:()=>import('../views/Counter/cashier/LossAndReissueView.vue')
    },
    {
        path: '/counter/cashier/openAccount',
        name: 'cashierOpenAccount',
        component:()=>import('../views/Counter/cashier/OpenAccountView.vue')
    },
    {
        path: '/counter/cashier/timeDeposit',
        name: 'cashierTimeDeposit',
        component:()=>import('../views/Counter/cashier/TimeDepositView.vue')
    },
    {
        path: '/counter/cashier/timeWithdrawal',
        name: 'cashierTimeWithdrawal',
        component:()=>import('../views/Counter/cashier/TimeWithdrawalView.vue')
    },
    {
        path: '/counter/cashier/transferAccount',
        name: 'cashierTransferAccount',
        component:()=>import('../views/Counter/cashier/TransferAccountView.vue')
    }
];

// 创建并配置路由
const router = createRouter({
    history: createWebHistory(),
    routes,  // 等同于 routes: routes
});

export default router;