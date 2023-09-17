package com.libre.framework.im.core.message.handler;

import com.libre.framework.im.core.exception.LibreImException;
import com.libre.framework.im.core.message.MediaMessage;
import com.libre.framework.im.core.message.Message;
import com.libre.framework.im.core.message.enums.MessageBodyType;
import com.libre.framework.im.core.proto.TextMessageProto;
import com.libre.framework.im.core.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author ZC
 * @date 2021/8/7 14:07
 */
@Slf4j
@Component
public class MediaMessageHandler extends AbstractMessageHandler<MediaMessage> {
    private final SessionManager sessionManager;
    public MediaMessageHandler(SessionManager sessionManager) {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }

    @Override
    public MessageBodyType getMessageType() {
        return MessageBodyType.MEDIA;
    }

    @Override
    public MediaMessage newInstance() {
        return new MediaMessage();
    }

    @Override
    public TextMessageProto.TextMessage getMessage(Message message) {
        if (ObjectUtils.nullSafeEquals(getMessageType().getCode(), message.getMessageBodyType())) {
            MediaMessage mediaMessage = (MediaMessage) message;
        }
        throw new LibreImException("messageBodyType match failed");
    }

    @Override
    protected void sendMessage(Message message) {

    }
}
