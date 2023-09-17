package com.libre.framework.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.ArrayListMultimap;
import com.libre.framework.im.pojo.ChatMessage;

import java.util.Collection;

/**
 * @author: Libre
 * @Date: 2022/5/23 10:54 PM
 */
public interface MessageService extends IService<ChatMessage> {

    ArrayListMultimap<Long, ChatMessage> findMessagesBySendUserIds(Collection<Long> userIds);
}
