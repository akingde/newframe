package com.newframe.user;

import com.newframe.NewFrameApplicationTests;
import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.blockchain.util.KeyUtil;
import com.newframe.enums.user.UserSMSEnum;
import com.newframe.services.after.AfterService;
import com.newframe.services.block.BlockChainService;
import com.newframe.services.user.SessionService;
import com.newframe.services.user.UserService;
import com.newframe.services.userbase.ConfigRateService;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.PPMT;
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
    private BlockChainService blockChainService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ConfigRateService configRateService;

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
        blockChainService.funderApply(1234L, "", "test");
    }

    @Test
    public void redistest(){
        String mobile = "18939166685";
        Integer type = UserSMSEnum.REGISTER.getCodeType();
        String code = "1234";
        sessionService.saveCode(mobile, type, code);
        sessionService.checkCode(mobile, type, code);
    }

    @Test
    public void passBig (){
        ResponseChain chain = blockChainService.t1MerchantApply(1535433927623099L,
                "4fbc5efe1e6cfdc87f405dfc29d6eeff782123df68a6597ea0bcc14bffc1109d",
                "诺基亚板砖租赁店");
        System.out.println(chain.isSuccess());
    }

    @Test
    public void passSmall (){
        ResponseChain chain = blockChainService.t2MerchantApply(1535433927623096L,
                1535433927623101L,
                "e6e2b630e127c0a302e8a01cc76c09c58566c783e77a2889deecbbd497de58bf",
                "易租小B0");
        System.out.println(chain.isSuccess());
    }

    @Test
    public void passFunder(){
        ResponseChain chain = blockChainService.funderApply(1537516469945230L,
                "fb824b3d1144e15999c717e4c3c7c651864c54d8b94f2453698dbcd108c0a687",
                "易组资金方");
        System.out.println(chain.isSuccess());
    }

    @Test
    public void passLessot(){
        ResponseChain chain = blockChainService.lessorApply(1535433927623105L,
                "94d96e1c88cb891e31b198e0d28204309072e9c05548623dde94c574d7a4f108",
                "易组出租方");
        System.out.println(chain.isSuccess());
    }

    @Test
    public void passSupplier(){
        ResponseChain chain = blockChainService.supplierApply(1537516469945228L,
                "63bd01fdd70abd939469c523b6f288d987e0d4eaeffa7265a027f9b841d85b28",
                "易组供应商");
        System.out.println(chain.isSuccess());
    }

    public static void main(String[] args) {
        String prikey = KeyUtil.privateKey();
        System.out.println(prikey);
        System.out.println(KeyUtil.publicKey(prikey));
    }
}
