package com.newframe.dto.account;

import com.newframe.entity.account.AccountRenterOverdueDetail;
import com.newframe.entity.account.AccountRenterRentDetail;
import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class RenterOrderOverdueDetailInfo {

    private List<AccountRenterOverdueDetail> list;

    private Long total;
}
