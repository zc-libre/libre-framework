package com.libre.framework.system.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.*;
import com.libre.framework.system.module.security.pojo.dto.UserInfo;
import com.libre.framework.system.module.system.pojo.entity.SysUser;
import com.libre.framework.system.module.system.service.SysUserService;
import com.libre.mybatis.util.PageUtil;
import com.libre.toolkit.exception.LibreException;
import com.libre.framework.system.module.system.pojo.dto.UserCriteria;
import com.libre.framework.system.module.system.pojo.entity.SysRole;
import com.libre.framework.system.module.system.pojo.entity.SysUserRole;
import com.libre.framework.system.module.system.mapper.SysUserMapper;
import com.libre.framework.system.module.system.service.SysRoleService;
import com.libre.framework.system.module.system.service.SysUserRoleService;
import com.libre.framework.system.module.system.service.mapstruct.SysUserMapping;
import com.libre.framework.system.module.system.pojo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.libre.framework.common.constant.CacheConstants.SYS_USER_CACHE;

/**
 * @author zhao.cheng
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = SYS_USER_CACHE)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final SysRoleService roleService;

	private final SysUserRoleService userRoleService;


	@Override
	public PageDTO<UserVO> findByPage(Page<SysUser> page, UserCriteria userParam) {
		Page<SysUser> userPage = this.page(page, getQueryWrapper(userParam));
		List<SysUser> records = userPage.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return null;
		}

		Multimap<Long, Long> userIdRoleMap = LinkedHashMultimap.create();
		Set<Long> roleIdSet = Sets.newHashSet();

		Set<Long> userIdSet = records.stream().map(SysUser::getId).collect(Collectors.toSet());
		List<SysUserRole> userRoleList = userRoleService.getListByUserIds(userIdSet);

		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (SysUserRole sysUserRole : userRoleList) {
				userIdRoleMap.put(sysUserRole.getUserId(), sysUserRole.getRoleId());
				roleIdSet.add(sysUserRole.getRoleId());
			}
		}

		Map<Long, SysRole> roleMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(roleIdSet)) {
			Map<Long, SysRole> map = roleService.listByIds(roleIdSet).stream()
					.collect(Collectors.toMap(SysRole::getId, Function.identity()));
			roleMap.putAll(map);
		}

		List<UserVO> vos = Lists.newArrayList();
		for (SysUser sysUser : records) {
			UserVO userVO = SysUserMapping.INSTANCE.sourceToTarget(sysUser);

			// 角色
			Collection<Long> roleIds = userIdRoleMap.get(sysUser.getId());
			List<SysRole> roleList = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(roleIds)) {
				roleIds.forEach(roleId -> roleList.add(roleMap.get(roleId)));
			}
			userVO.setRoles(roleList);

			// 权限
			List<String> permissions = roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList());
			userVO.setPermissions(permissions);

			vos.add(userVO);
		}
		return PageUtil.toPage(userPage, vos);
	}

	@Override
	@Cacheable(key = "#id")
	public SysUser findUserById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	@Cacheable(key = "#username")
	public SysUser getByUsername(String username) {
		return this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true)
	public boolean updateByUsername(String username, SysUser sysUser) {
		LambdaUpdateWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUsername, username);
		return this.update(sysUser, wrapper);
	}

	@Override
	@Cacheable(key = "'info-'+ #username")
	public UserInfo findUserInfoByUsername(String username) {
		SysUser sysUser = Optional.ofNullable(this.getByUsername(username))
				.orElseThrow(() -> new LibreException(String.format("用户不存在, username,: [%s]", username)));

		List<SysRole> roles = roleService.getListByUserId(sysUser.getId());
		List<String> permissions = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(roles)) {
			 permissions = roles.stream().map(SysRole::getPermission).collect(Collectors.toList());
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setAvatar(sysUser.getAvatar());
		userInfo.setPermissions(permissions);
		userInfo.setIsAdmin(sysUser.getIsAdmin());
		userInfo.setRoles(roles);
		return userInfo;
	}

	@Override
	@CacheEvict(allEntries = true)
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}


	private Wrapper<SysUser> getQueryWrapper(UserCriteria param) {
		String blurry = param.getBlurry();
		LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().nested(param.isBlurryQuery(),
				q -> q.like(SysUser::getEmail, blurry).or().like(SysUser::getEmail, blurry).or()
						.like(SysUser::getNickName, blurry));
		if (param.haveTime()) {
			wrapper.between(SysUser::getGmtCreate, param.getStartTime(), param.getEndTime());
		}
		return wrapper;
	}

}
