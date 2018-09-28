package com.newframe.enums.order;

/**
 * @author kfm
 * @date 2018.08.30 20:44
 */

public enum PayStatusEnum {
    NORMAL(1, "正常"),
    OVERDUE(2, "逾期")
    ;

    private Integer code;
    private String message;

    private PayStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }


    public static PayStatusEnum getEnum(Integer vCodeType) {
        for (PayStatusEnum orderStatusEnum: PayStatusEnum.values()) {
            if (orderStatusEnum.getCode().equals(vCodeType)) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
