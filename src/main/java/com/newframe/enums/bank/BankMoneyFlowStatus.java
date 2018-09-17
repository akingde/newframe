package com.newframe.enums.bank;

/**
 * 审核状态
 * <p>
 * 充值记录(划拔，退款)
 * <p>
 * <p>
 */
public enum BankMoneyFlowStatus {
    OUT_AUDIT_READY(60, "待审核"),
    OUT_AUDIT_FAIL(61, "审核拒绝"),
    OUT_AUDIT_SUCCESS(62, "银行处理中"),
    OUT_AUDIT_BANK_SUCCESS(63, "银行处理成功"),
    OUT_AUDIT_BANK_FAIL(64, "银行处理失败");

    private int value;
    private String desc;

    BankMoneyFlowStatus(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getIntValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    public static BankMoneyFlowStatus get(int v) {
        String str = String.valueOf(v);
        return get(str);
    }

    public static BankMoneyFlowStatus get(String str) {
        for (BankMoneyFlowStatus e : values()) {
            if (e.toString().equals(str)) {
                return e;
            }
        }
        return null;
    }


}
