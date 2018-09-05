package com.newframe.dto.message;

import com.newframe.entity.account.AccountRenterRent;
import com.newframe.entity.message.UserMessage;
import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class UserMessageInfo {

    private List<UserMessage> list;

    private Long total;
}
