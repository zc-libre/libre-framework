package com.libre.admin.project.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.admin.project.system.entity.SysUser;
import com.libre.admin.project.system.mapper.UserMapper;
import com.libre.admin.project.system.service.SysUserService;


/**
 * @author zhao.cheng
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements SysUserService {

    @Override
    @Cacheable(cacheNames = "user", key = "#id")
    public SysUser findUserById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "sys:user#10m", key = "#username")
    public SysUser getByUsername(String username) {
        return this.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username));
    }
}
