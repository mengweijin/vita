export default {
  path: '/',
  name: 'Layout',
  component: () => import('@/layout/LayIndex.vue'),
  redirect: '/home',
  children: [
    {
      path: '/home',
      name: 'HomeView',
      component: () => import('@/views/home/HomeView.vue'),
      meta: {
        title: '首页',
      },
    },
  ],
}
