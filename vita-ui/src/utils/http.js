import axios from 'axios';
import { stringify } from 'qs';
import router from '@/router/index';

import { useUserStore } from '@/store/user-store';
import { useDictStore } from '@/store/dict-store';

const { VITE_API_BASE } = import.meta.env;

let loadingInstance;

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
    });
  },
});

// 添加请求拦截器。在发送请求之前做些什么，比如设置 token，权限认证等
axiosInstance.interceptors.request.use(
  (config) => {
    if (config.method?.toUpperCase() === 'POST') {
      loadingInstance = ElLoading.service({ fullscreen: true });
    }

    const token = useUserStore().getToken();
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 添加响应拦截器
axiosInstance.interceptors.response.use(
  (response) => {
    // 获取请求方式
    const method = response.config.method;
    if (method.toUpperCase() === 'POST') {
      loadingInstance?.close();
      ElMessage.success({ message: '操作成功!', duration: 3000, showClose: true });
    }
    return response.data;
  },

  (error) => {
    if (error.response.status) {
      switch (error.response.status) {
        case 400:
          ElMessage.error({ message: JSON.stringify(error.response.data), showClose: true });
          break;
        case 401:
          // 清理前端登录信息残留
          useDictStore().clear();
          useUserStore().clear();
          // 跳转时携带当前页面路径，登录后可返回
          router.push({ path: '/login', query: { redirect: router.currentRoute.fullPath } });
          break;
        case 403:
          ElMessage.error({ message: error.response.status + ' Forbidden', showClose: true });
          break;
        case 404:
          ElMessage.error({ message: error.response.status + ' Not Found', showClose: true });
          break;
        case 405:
          ElMessage.error({ message: error.response.status + ' Method Not Allowed', showClose: true });
          break;
        case 406:
          ElMessage.error({ message: error.response.status + ' Not Acceptable', showClose: true });
          break;
        case 408:
          ElMessage.error({ message: error.response.status + ' Request Timeout', showClose: true });
          break;
        case 500:
          ElMessage.error({ message: error.response.status + ' Server Error', showClose: true });
          break;
        default:
          ElMessage.error({ message: JSON.stringify(error.response.data), showClose: true });
          break;
      }
    }
    loadingInstance?.close();
    return Promise.reject(error);
  },
);

/**
 * Get Download file
 * @param {String} url
 * @param {String} fileName
 * @returns
 */
axiosInstance.download = async function (url, fileName) {
  const response = await axiosInstance.get(url, { responseType: 'blob' });
  let fileURL = window.URL.createObjectURL(new Blob([response.data]));
  let fileLink = document.createElement('a');
  fileLink.href = fileURL;
  fileLink.setAttribute('download', fileName);
  document.body.appendChild(fileLink);
  fileLink.click();
  fileLink.remove();
};

export default axiosInstance;
