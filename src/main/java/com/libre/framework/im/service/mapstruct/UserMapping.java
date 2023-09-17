package com.libre.framework.im.service.mapstruct;

import com.libre.framework.im.pojo.LibreUser;
import com.libre.framework.im.pojo.vo.UserVO;
import com.libre.toolkit.mapstruct.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/6/21 9:52 PM
 */
@Mapper
public interface UserMapping extends BaseMapping<LibreUser, UserVO> {

	UserMapping INSTANCE = Mappers.getMapper(UserMapping.class);

}
