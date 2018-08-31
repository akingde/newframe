package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterAppointSupplier {
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * uid
     */
    private Long uid;
    /**
     * 供应商的UID
     */
    private Long supplierUid;
    /**
     * 供应商的名字
     */
    private String supplierName;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;
}
