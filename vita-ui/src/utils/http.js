import axios from 'axios'
import { stringify } from 'qs'
import router from '@/router/index.js'

import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/userStore.js'
const userStore = useUserStore()
const { user } = storeToRefs(userStore)

const { VITE_API_BASE } = import.meta.env

// 创建axios实例
let axiosInstance = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分。参考文档 https://cn.vitejs.dev/guide/env-and-mode.html
  baseURL: VITE_API_BASE,
  // 超时，单位：毫秒
  timeout: 50000,
  // 允许携带cookie请求
  withCredentials: true,
  headers: { 'Content-Type': 'application/json;charset=utf-8' },
  paramsSerializer: (params) => {
    return stringify(params, {
      // repeat：数组序列化为重复键名（ids=1&ids=2&ids=3）
      // comma：数组序列化为逗号分隔（ids=1,2,3）
      arrayFormat: 'comma',
      encode: false, // 禁用 URL 编码
      skipNulls: true, // 跳过空值参数
    })
  },
})

// 添加请求拦截器。在发送请求之前做些什么，比如设置 token，权限认证等
axiosInstance.interceptors.request.use(
  (config) => {
    const token = user?.value?.token
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 添加响应拦截器
axiosInstance.interceptors.response.use(
  (response) => {
    // 获取请求方式
    const method = response.config.method
    if (method === 'post') {
      ElMessage.success({ message: '操作成功!', duration: 3000, showClose: true })
    }
    return response.data
  },
  (error) => {
    console.log(error)
    if (error.response.status) {
      switch (error.response.status) {
        case 400:
          ElMessage.error({ message: JSON.stringify(error.response.data), showClose: true })
          break
        case 401:
          // 清理前端登录信息残留
          user.value = null
          // 跳转时携带当前页面路径，登录后可返回
          router.push({ path: '/login', query: { redirect: router.currentRoute.fullPath } })
          // ElMessage.error({
          //   message: error.response.status + ' Unauthorized',
          //   showClose: true,
          // })
          break
        case 403:
          ElMessage.error({ message: error.response.status + ' Forbidden', showClose: true })
          break
        case 404:
          ElMessage.error({ message: error.response.status + ' Not Found', showClose: true })
          break
        case 405:
          ElMessage.error({
            message: error.response.status + ' Method Not Allowed',
            showClose: true,
          })
          break
        case 406:
          ElMessage.error({ message: error.response.status + ' Not Acceptable', showClose: true })
          break
        case 408:
          ElMessage.error({ message: error.response.status + ' Request Timeout', showClose: true })
          break
        case 500:
          ElMessage.error({ message: error.response.status + ' Server Error', showClose: true })
          break
        default:
          ElMessage.error({ message: JSON.stringify(error.response.data), showClose: true })
          break
      }
    }
    return Promise.reject(error)
  },
)

/**
 * Get Download file
 * @param {String} url
 * @param {String} fileName
 * @returns
 */
axiosInstance.download = async function (url, fileName) {
  const response = await axiosInstance.get(url, { responseType: 'blob' })
  let fileURL = window.URL.createObjectURL(new Blob([response.data]))
  let fileLink = document.createElement('a')
  fileLink.href = fileURL
  fileLink.setAttribute('download', fileName)
  document.body.appendChild(fileLink)
  fileLink.click()
  fileLink.remove()
}

export default axiosInstance

/**
 * 说明：
 * 1. 在 vue 中给 data() 中的对象赋值时，下面的 then() 需要用 => 方式，而不能用 function(response) {} 的方式，否则 then 代码块里面的 this 指向的是 axios，而不是 vue。
 *
 * 2. 当使用 get 请求时，参数传递的 key 要用 params
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.get('/doc-namespace/' + namespaceId, { params: requestParams }).then((response) => {
 *    this.namespace = response.data
 * })
 *
 * 3. 当使用除了 get 方式以外的请求类型时，参数传递直接传入即可
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.post('/doc-namespace/', requestParams).then((response) => {
 *    // do something
 * })
 *
 * 4. 这里扩展了一个 loading 全屏遮罩层的 get 方式的方法
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.loading('/doc-namespace/' + namespaceId, { params: requestParams }).then((response) => {
 *    this.namespace = response.data
 * })
 *
 * 5. 这里扩展了一个 download 下载文件的 get 方式的方法
 * 假如: fileName = a.zip
 * this.$http.download('/common/download/' + fileId, fileName)
 */
