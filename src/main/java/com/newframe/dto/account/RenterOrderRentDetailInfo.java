package com.newframe.dto.account;

import com.newframe.entity.account.AccountRenterFinancing;
import com.newframe.entity.account.AccountRenterRentDetail;
import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class RenterOrderRentDetailInfo {

    private List<AccountRenterRentDetail> list;

    private Long total;
}
