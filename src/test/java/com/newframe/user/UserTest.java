package com.newframe.user;

import com.newframe.NewFrameApplicationTests;
import com.newframe.enums.user.UserSMSEnum;
import com.newframe.services.after.AfterService;
import com.newframe.services.block.BlockChain;
import com.newframe.services.user.SessionService;
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
    @Autowired
    private SessionService sessionService;

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
        afterService.passDrawAssetCheck(-1L, 1537167000850150L);
    }

    @Test
    public void failDrawAssetCheck(){
        afterService.failDrawAssetCheck(-1L, 1537167000850144L, "");
    }

    @Test
    public void blockTest(){
        blockChain.funderApply(1234L, "", "test");
    }

    @Test
    public void redistest(){
        String mobile = "18939166685";
        Integer type = UserSMSEnum.REGISTER.getCodeType();
        String code = "1234";
        sessionService.saveCode(mobile, type, code);
        sessionService.checkCode(mobile, type, code);
    }

//    @Test
//    public
}
