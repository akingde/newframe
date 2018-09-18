package com.newframe.services.bank.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 实时交易返回结果
 */
@Setter
@Getter
@ToString
public class BankRealTimeResultBean {
    private String code;
    private String message;
    private BankPlatformBean platformToken;
    private List<BankFlowDataBean> data;

    @Setter
    @Getter
    @ToString
    public class BankPlatformBean {
        private String platformtoken;
        private boolean state;
        private String accountid;
        private String facedesc;
    }

    @Setter
    @Getter
    @ToString
    public static class BankFlowDataBean implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private String platformtoken;
        private String accountid;
        private String tran_FLOW;//交易流水号
        private String bflow;    //企业支付流水号
        private String amt;    //发生额
        private String amt1;    //余额
        private String accno2;    //对方账号
        private String acc_NAME1;//对方户名
        private String cadbank_Nm;//对方账户开户行名称
        private String creattime;//交易日期
        private String flag1;//借贷标志  0, 借  1，贷
        private String rltv_ACCNO;//关联账号
        private int stata;
        private String det;//备注
    }

    public Set<String> getTranNoSet() {
        Set<String> set = new HashSet<>();
        if (null == data || data.isEmpty()) {
            return set;
        }
        for (Iterator<BankFlowDataBean> iter = data.iterator(); iter.hasNext(); ) {
            BankRealTimeResultBean.BankFlowDataBean bean = iter.next();
            if (StringUtils.isEmpty(bean.getBflow())) {
                continue;//去掉企业支付流水号为空
            }
            set.add(bean.getTran_FLOW());//银行流水(用于查询数据库)
        }
        return set;
    }

    public List<String> getTranNoList() {
        Set<String> set = getTranNoSet();
        List<String> list = new ArrayList();
        list.addAll(set);
        return list;
    }

    public Set<String> getEnterpriseNameSet() {
        Set<String> set = new HashSet<>();
        if (null == data || data.isEmpty()) {
            return set;
        }
        for (Iterator<BankFlowDataBean> iter = data.iterator(); iter.hasNext(); ) {
            BankRealTimeResultBean.BankFlowDataBean bean = iter.next();
            if (StringUtils.isEmpty(bean.getBflow())) {
                continue;//去掉企业支付流水号为空
            }
            set.add(bean.getAcc_NAME1());//企业名称(用于查询数据库)
        }
        return set;
    }

    /**
     * 企业银行卡号
     *
     * @return
     */
    public Set<String> getEnterpriseBankCardSet() {
        Set<String> set = new HashSet<>();
        if (null == data || data.isEmpty()) {
            return set;
        }
        for (Iterator<BankFlowDataBean> iter = data.iterator(); iter.hasNext(); ) {
            BankRealTimeResultBean.BankFlowDataBean bean = iter.next();
            if (StringUtils.isEmpty(bean.getBflow())) {
                continue;//去掉企业支付流水号为空
            }
            set.add(bean.getAccno2());//企业银行卡号(用于查询数据库)
        }
        return set;
    }

    public List<String> getEnterpriseNameList() {
        Set<String> set = getEnterpriseNameSet();
        List<String> list = new ArrayList();
        list.addAll(set);
        return list;
    }

}