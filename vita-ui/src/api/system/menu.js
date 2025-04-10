import http from '@/utils/http.js'

const BASE_URL = '/system/menu'

export const getMenuPage = (args) => http.get(`${BASE_URL}/page`, { params: args })

export const getMenuList = (args) => http.get(`${BASE_URL}/list`, { params: args })

export const createMenu = (data) => http.post(`${BASE_URL}/create`, data)

export const updateMenu = (data) => http.post(`${BASE_URL}/update`, data)

export const deleteMenu = (ids) => http.post(`${BASE_URL}/delete/${ids}`)
