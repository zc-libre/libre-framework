package com.libre.framework.im.core.mapstruct;

import com.libre.framework.im.core.proto.TextMessageProto;
import com.libre.toolkit.mapstruct.BaseMapping;
import com.libre.toolkit.time.DatePattern;
import com.libre.framework.im.core.message.TextMessage;
import com.libre.framework.im.pojo.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: Libre
 * @Date: 2022/5/1 10:08 PM
 */
@Mapper
public interface MessageMapping extends BaseMapping<TextMessageProto.TextMessage, TextMessage> {

    MessageMapping INSTANCE = Mappers.getMapper(MessageMapping.class);

    @Override
    @Mapping(source = "createTime", target = "createTime", dateFormat = DatePattern.NORM_DATETIME_PATTERN)
    TextMessage sourceToTarget(TextMessageProto.TextMessage textMessage);

    @Mapping(source = "body", target = "message")
    ChatMessage convertToChatMessage(TextMessage textMessage);
}
