export default {
  path: '/login',
  name: 'login-view',
  component: () => import('@/views/login/login-view.vue'),
  meta: {
    title: '登录',
  },
}
