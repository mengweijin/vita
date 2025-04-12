const Layout = () => import('@/layout/LayIndex.vue')

export default {
  path: '/',
  component: Layout,
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
