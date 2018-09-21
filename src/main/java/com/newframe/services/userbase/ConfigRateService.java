package com.newframe.services.userbase;

import com.newframe.entity.user.ConfigRate;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
public interface ConfigRateService {

    BigDecimal getRate();

    ConfigRate insert(BigDecimal rate);
}
