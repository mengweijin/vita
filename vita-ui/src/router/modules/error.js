export default {
  path: '/error',
  name: 'error',
  redirect: '/error/403',
  children: [
    {
      path: '/error/403',
      name: '403',
      component: () => import('@/views/error/403.vue'),
      meta: {
        title: '禁止访问',
      },
    },
    {
      path: '/error/404',
      name: '404',
      component: () => import('@/views/error/404.vue'),
      meta: {
        title: '页面走丢了',
      },
    },
  ],
}
