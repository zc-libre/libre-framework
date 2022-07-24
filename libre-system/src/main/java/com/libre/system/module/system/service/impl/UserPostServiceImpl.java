package com.libre.system.module.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.libre.system.module.system.entity.SysUserPost;
import com.libre.system.module.system.mapper.UserPostMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.system.module.system.service.SysUserPostService;

import java.util.Collection;
import java.util.List;

/**
 * @author zhao.cheng
 */
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, SysUserPost> implements SysUserPostService {

	@Override
	public List<SysUserPost> getListByUserId(Long userId) {
		return super.list(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getUserId, userId));
	}

	@Override
	public List<SysUserPost> getListByUserIds(Collection<Long> userIds) {
		return this.list(Wrappers.<SysUserPost>lambdaQuery().in(SysUserPost::getUserId, userIds));
	}

}