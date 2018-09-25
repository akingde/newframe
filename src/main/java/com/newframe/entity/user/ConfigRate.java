package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
@Entity
public class ConfigRate {

    @Id
    private Long id;

    private BigDecimal rate;

    private Integer ctime;

    private Integer utime;
}
