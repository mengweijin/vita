// 按需导入 lodash
import groupBy from 'lodash/groupBy'

/**
 * 使用：
 *  let result = tree(dataList);
 *  let result = tree(dataList, '0');
 *  let result = tree(dataList, '0', 'id', 'pid', 'children');
 *
 * @param {Array} dataList Mandatory
 * @param {String} rootId Optional. Default: '0'
 * @param {String} idPropName Optional. Default: 'id'
 * @param {String} parentIdPropName Optional. Default: 'parentId'
 * @param {String} childrenPropName Optional. Default: 'children'
 * @returns
 */
export const toTree = (
  dataList,
  rootId = '0',
  idPropName = 'id',
  parentIdPropName = 'parentId',
  childrenPropName = 'children',
) => {
  let groupedList = groupBy(dataList, parentIdPropName)
  for (let i in dataList) {
    let row = dataList[i]
    row[childrenPropName] = groupedList[row[idPropName]]
  }
  return groupedList[rootId]
}
