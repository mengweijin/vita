import http from "@/utils/http.js";

const URL_PREFIX = "/system/user";

export const userApi = {
	/**
	 *
	 * @param {_PasswordChangeBO} data
	 * @returns
	 */
	changePassword: (data) => http.post(`${URL_PREFIX}/change-password`, data),

	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	getLoginUserInfo: () => http.get(`${URL_PREFIX}/get-login-user-info`),

	getSensitiveUserById: (id) => http.get(`${URL_PREFIX}/get-sensitive-info/${id}`),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	pageByPost: (postId, args) => http.get(`${URL_PREFIX}/pageByPost/${postId}`, { params: args }),

	pageByRole: (roleId, args) => http.get(`${URL_PREFIX}/pageByRole/${roleId}`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	/**
	 *
	 * @param {_PasswordResetBO} data
	 * @returns
	 */
	resetPassword: (data) => http.post(`${URL_PREFIX}/reset-password`, data),

	/**
	 *
	 * @param {_UserRoleBO} data
	 * @returns
	 */
	setRoles: (data) => http.post(`${URL_PREFIX}/set-roles`, data),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};

class _UserRoleBO {
	userId = null;
	roleIds = [];
}

class _PasswordChangeBO {
	password = null;
	newPassword = null;
}

class _PasswordResetBO {
	username = null;
	password = null;
}
