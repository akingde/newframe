package com.newframe.user;

import com.newframe.NewFrameApplicationTests;
import com.newframe.blockchain.BlockChain;
import com.newframe.services.after.AfterService;
import com.newframe.services.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
public class UserTest extends NewFrameApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private AfterService afterService;
    @Autowired
    private BlockChain blockChain;

    @Test
    public void addRechargeRecord(){
        String bankNumber = "1";
        userService.addRechargeRecord(bankNumber, BigDecimal.TEN);
    }

    @Test
    public void addDrawRecord(){
        Long uid = 1535433927622902L;
        BigDecimal amount = BigDecimal.TEN;
        userService.addDrawRecord(uid, amount);
    }

    @Test
    public void bankDrawByPass(){
        userService.bankDrawByPass(1537171530299L);
    }

    @Test
    public void bankDrawByFail(){
        userService.bankDrawByFail(2L);
    }

    @Test
    public void passDrawAssetCheck(){
        afterService.passDrawAssetCheck(-1L, 1537167000850149L);
    }

    @Test
    public void failDrawAssetCheck(){
        afterService.failDrawAssetCheck(-1L, 1537167000850148L);
    }

    @Test
    public void blockTest(){
        blockChain.funderCheckPass(123L, "test");
    }
}
