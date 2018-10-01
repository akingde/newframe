package com.newframe.services.account;

/**
 * 账户统计
 */
public interface AccountStatisService {

    /**
     * 1.租赁商账户统计
     *
     * @param uid
     */
    void statisAccountRenter(long uid);

    /**
     * 2.资金方账统计
     *
     * @param uid
     */
    void statisAccountFunding(long uid);

    /**
     * 3.出租方账户统计
     *
     * @param uid
     */
    void statisAccountLessor(long uid);

    /**
     * 4.供应商账户统计
     *
     * @param uid
     */
    void statisAccountSupplier(long uid);

}
