package com.newframe.enums.bank;

/**
 * 审核状态
 * <p>
 * 充值记录(划拔，退款)
 * <p>
 * <p>
 */
public enum BankMoneyFlowStatus {

    IN_READY(10, "充值待处理"),
    IN_SUCCESS(11, "充值处理完成"),

    OUT_AUDIT_READY(60, "提现待审核"),
    OUT_AUDIT_FAIL(61, "提现拒绝"),
    OUT_AUDIT_SUCCESS(62, "提现银行处理中"),
    OUT_BANK_SUCCESS(63, "提现银行处理成功"),
    OUT_BANK_FAIL(64, "提现银行处理失败");

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
