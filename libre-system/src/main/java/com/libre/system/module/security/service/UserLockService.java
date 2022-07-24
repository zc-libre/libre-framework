package com.libre.system.module.security.service;

import com.libre.system.module.security.service.dto.AuthUserDTO;

public interface UserLockService {

	/**
	 * 锁定用户
	 *
	 * @param authUser AuthUser
	 * @return {boolean}
	 */
	boolean updateLockUser(AuthUserDTO authUser);

}
