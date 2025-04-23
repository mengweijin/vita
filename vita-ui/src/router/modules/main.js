export default {
  path: '/',
  name: 'Layout',
  component: () => import('@/layout/lay-index.vue'),
  redirect: '/home',
  children: [
    {
      path: '/home',
      name: 'HomeView',
      component: () => import('@/views/home/home-view.vue'),
      meta: {
        title: '首页',
      },
    },
  ],
}
