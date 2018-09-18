package com.newframe.services.bank.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * 银行交易处理返回结果(序列号)
 */
@Getter
@Setter
@ToString
public class BankDealResultBean {
    private Data data;

    public final static Set<String> okSet = new HashSet();
    public final static Set<String> noSet = new HashSet();

    static {
        okSet.add("2");
        okSet.add("3");
        okSet.add("4");

        noSet.add("5");
        noSet.add("A");
    }

    @Getter
    @Setter
    @ToString
    public class Data {
//        private String code;
        private String dealresult;
        private String serialnumber;
    }

    public String getDealresult() {
        return data.getDealresult();
    }
//    public String getCode() {
//        return data.getCode();
//    }

    public String getSerialnumber() {
        return data.getSerialnumber();
    }

}
