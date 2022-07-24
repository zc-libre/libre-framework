package com.libre.system.module.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.system.module.system.entity.SysUserRole;
import com.libre.system.module.system.mapper.UserRoleMapper;
import com.libre.system.module.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Libre
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<SysUserRole> getListByUserId(Long userId) {
        return this.list(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId));
    }

    @Override
    public List<SysUserRole> getListByUserIds(Collection<Long> userIds) {
        return this.list(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
    }
}