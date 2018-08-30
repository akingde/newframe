package com.newframe.dto.account;

import com.newframe.entity.account.AccountRenterRent;
import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class AccountRenterRentInfo {

    private List<AccountRenterRent> list;

    private Integer total;
}
