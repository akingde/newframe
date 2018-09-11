package com.newframe.entity.message;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 资金方账户表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class UserMessage {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 用户的Uid
     */
    private Long uid;

    /**
     * 角色的Id
     */
    private Integer roleId;
    /**
     * 平台自己的订单ID
     */
    private Long orderId;
    /**
     * 关联订单的ID
     */
    private Long associatedOrderId;
    /**
     * 消息的标题
     */
    private String messTitle;
    /**
     * 消息的类型：1:融资类消息，2:租机类消息，3:发货申请类的消息
     */
    private Integer messType;
    /**
     * 消息的内容
     */
    private String messContent;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

    public UserMessage(Long uid, Integer roleId, Long orderId, Long associatedOrderId, String messTitle, Integer messType, String messContent) {
        this.uid = uid;
        this.roleId = roleId;
        this.orderId = orderId;
        this.associatedOrderId = associatedOrderId;
        this.messTitle = messTitle;
        this.messType = messType;
        this.messContent = messContent;
    }
}
