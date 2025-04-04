export default {
  path: '/login',
  name: 'LoginView',
  component: () => import('@/views/login/LoginView.vue'),
  meta: {
    title: '登录',
  },
}
