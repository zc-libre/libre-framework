package com.libre.framework.im.core.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author ZC
 * @date 2021/8/6 23:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextMessage extends Message {

    @Override
    public String getBody() {
        return (String) body;
    }

}
