package com.newframe.dto.account;

import com.google.common.collect.Lists;
import com.newframe.entity.account.AccountRenterFinancing;
import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class RenterOrderFinanceInfo {

    private List<AccountRenterFinancing> list;

    private Integer total;
}
