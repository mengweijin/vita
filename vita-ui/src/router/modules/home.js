const Layout = () => import('@/layout/LayIndex.vue')

export default {
  path: '/',
  component: Layout,
  redirect: '/home',
  children: [
    {
      path: '/home',
      name: 'HomeView',
      component: () => import('@/views/HomeView.vue'),
      meta: {
        title: '首页',
      },
    },
    {
      path: '/about',
      name: 'AboutView',
      component: () => import('@/views/AboutView.vue'),
      meta: {
        title: '关于',
      },
    },
  ],
}
