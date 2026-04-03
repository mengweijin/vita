import http from "@/utils/http.js";
import qs from "qs";

const URL_PREFIX = "/generator";

export const generatorApi = {
  /**
   * @typedef {Object} GeneratorBO
   * @property {string} templateId - 模板ID
   * @property {string} tableName - 表名
   * @property {string} tablePrefix - 表前缀
   * @property {string} packages - 包名
   * @property {string} moduleName - 模块名
   * @property {string} author - 作者
   */

  /**
   * 下载代码
   * @param {GeneratorBO} params - 下载参数
   * @param {string} [fileName] - 文件名
   */
  download: (params, fileName = undefined) => {
    // 对象转 URL 参数
    const query = qs.stringify(params);
    http.download(`${URL_PREFIX}/download?${query}`, fileName);
  },
  listTableInfo: (tableName) =>
    http.get(`${URL_PREFIX}/list/tableInfo`, { params: { name: tableName } }),

  listTemplate: () => http.get(`${URL_PREFIX}/list/template`),

  queryDefaultArgs: () => http.get(`${URL_PREFIX}/query/args/default`),

  run: (data) => http.post(`${URL_PREFIX}/run`, data),
};
