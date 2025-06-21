import axios from 'axios';
import { stringify } from 'qs';
import router from '@/router/index';

import { useUserStore } from '@/store/user-store';
import { useDictStore } from '@/store/dict-store';
import { isEmpty } from 'xe-utils';

const { VITE_API_BASE } = import.meta.env;

let loadingInstance;

const axiosConfig = {
  // 自定义属性，是否启用全屏 loading
  loading: true,
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
};

// 创建axios实例
let axiosInstance = axios.create(axiosConfig);

// 添加请求拦截器。在发送请求之前做些什么，比如设置 token，权限认证等
axiosInstance.interceptors.request.use(
  (config) => {
    if (config.loading && config.method?.toUpperCase() === 'POST') {
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
      if (response.config.loading) {
        loadingInstance?.close();
      }
      ElMessage.success({ message: '操作成功!', duration: 3000, showClose: true });
    }
    return response.data;
  },

  (error) => {
    if (error.response.status) {
      console.error(error.response.data);
      switch (error.response.status) {
        case 400:
          ElMessage.error({ message: error.response.data?.msg, showClose: true });
          break;
        case 401:
          // 清理前端登录信息残留
          useDictStore().clear();
          useUserStore().clear();
          // 跳转时携带当前页面路径，登录后可返回
          router.push({ path: '/login', query: { redirect: router.currentRoute.value.fullPath } });
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
          ElMessage.error({ message: error.response.data?.msg, showClose: true });
          break;
      }
    }
    loadingInstance?.close();
    return Promise.reject(error);
  },
);

/**
 * 这个 axios 实例没有请求和响应拦截，专门用于文件下载
 */
let downloadInstance = axios.create(axiosConfig);

/**
 * Get Download file
 * @param {String} url
 * @param {String} fileName
 * @returns
 */
axiosInstance.download = function (url, fileName = undefined) {
  const token = useUserStore().getToken();
  // 这里要用 downloadInstance 实例单独实现下载功能，而不用公共的 axiosInstance 实例。
  downloadInstance
    .get(url, { responseType: 'blob', headers: { Authorization: `Bearer ${token}` } })
    .then((response) => {
      // attachment;fileName=%E6%A8%AA%E5%9B%BE_%E4%BA%91%E6%9B%A6_1.jpg
      const contentDisposition = response.headers['content-disposition'];

      if (isEmpty(fileName)) {
        const encodedFileName = contentDisposition
          .split(';')
          .map((item) => item.trim())
          // 获取第一个符合条件的元素
          .find((item) => item.toLowerCase().startsWith('filename='))
          // 截取后半部分
          .slice(9);
        const decodedFileName = decodeURIComponent(encodedFileName);
        fileName = decodedFileName;
      }

      const url = window.URL.createObjectURL(new Blob([response.data], { type: response.data.type }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      // 下载完成移除元素
      document.body.removeChild(link);
      // 释放掉blob对象
      window.URL.revokeObjectURL(url);
    });
};

export default axiosInstance;
