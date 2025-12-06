import http from "@/utils/http.js";

const URL_PREFIX = "/system/role";

export const roleApi = {
	addUsers: (roleId, userIds) => http.post(`${URL_PREFIX}/add-users/${roleId}/${userIds}`),

	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	getDefaultRole: () => http.get(`${URL_PREFIX}/get-default-role`, {}),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	removeByRoleIdInUserIds: (roleId, userIds) =>
		http.post(`${URL_PREFIX}/removeByRoleIdInUserIds/${roleId}/${userIds}`, null, { loading: false }),

	setPermission: (id, menuIdList = []) =>
		http.post(`${URL_PREFIX}/set-permission`, { menuIds: menuIdList, roleId: id }),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
