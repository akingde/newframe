package com.newframe.services.after.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.after.request.DrawAssetSearchDTO;
import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.dto.after.request.RoleListSearchDTO;
import com.newframe.dto.after.response.*;
import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.entity.user.CapitalFlow;
import com.newframe.entity.user.UserFunder;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.user.AssetStatusEnum;
import com.newframe.enums.user.AssetTypeEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.after.AfterService;
import com.newframe.services.bank.BankMoneyFlowOutService;
import com.newframe.services.user.RoleBaseService;
import com.newframe.services.userbase.CapitalFlowService;
import com.newframe.services.userbase.UserFunderService;
import com.newframe.services.userbase.UserRoleApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.newframe.enums.account.AccountTypeEnum.FROZENASSETS;
import static com.newframe.enums.account.AccountTypeEnum.USEABLEASSETS;
import static com.newframe.enums.account.DealTypeEnum.WITHDRAW;

/**
 * @author WangBin
 */
@Service
public class AfterServiceImpl implements AfterService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private RoleBaseService roleBaseService;
    @Autowired
    private UserFunderService userFunderService;
    @Autowired
    private CapitalFlowService capitalFlowService;
    @Autowired
    private AccountManageService accountManageService;
    @Autowired
    private BankMoneyFlowOutService bankMoneyFlowOutService;

    /**
     * 后台登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public OperationResult<UserDTO> login(String userName, String password) {
        String name = "admin", pwd = "nimda";
        if(StringUtils.isEmpty(userName) || !userName.equals(name)){
            return new OperationResult(RequestResultEnum.LOGIN_ERROR);
        }
        if(StringUtils.isEmpty(password) || !password.equals(pwd)){
            return new OperationResult(RequestResultEnum.LOGIN_ERROR);
        }
        return new OperationResult(new UserDTO());
    }

    /**
     * 获取角色列表
     *
     * @param condition
     * @return
     */
    @Override
    public OperationResult<RoleListDTO> getMerchantList(RoleListSearchDTO condition) {
        Page<UserRoleApply> page = userRoleApplyService.findAll(condition);
        return new OperationResult(new RoleListDTO(page));
    }

    /**
     * 获取角色申请详细信息
     *
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<RoleApplyInfoDTO> getMerchantInfo(Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        return roleApply == null ? new OperationResult() : new OperationResult(new RoleApplyInfoDTO(roleApply));
    }

    /**
     * 审核通过
     *
     * @param uid
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(Long uid, Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        if(roleApply == null || !roleApply.getApplyStatus().equals(RoleStatusEnum.UNDER_REVIEW.getRoleStatue())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        return roleBaseService.passRoleApply(roleApply);
    }

    /**
     * 审核失败
     *
     * @param uid
     * @param roleApplyId
     * @param remarks
     * @return
     */
    @Override
    public OperationResult<Boolean> failCheck(Long uid, Long roleApplyId, String remarks) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        if(roleApply == null || !roleApply.getApplyStatus().equals(RoleStatusEnum.UNDER_REVIEW.getRoleStatue())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        roleApply.setApplyStatus(RoleStatusEnum.REVIEW_FAILURE.getRoleStatue());
        roleApply.setRemarks(remarks);
        roleApply.setCheckUid(uid);
        roleApply.setCheckPerson(new UserDTO().getUserName());
        userRoleApplyService.updateByRoleApplyId(roleApply);
        return new OperationResult(true);
    }

    /**
     * 获取白名单列表
     *
     * @param condiiton
     * @return
     */
    @Override
    public OperationResult<WhiteFunderListDTO> getWhiteList(FunderSearchDTO condiiton) {
        Page<UserFunder> page = userFunderService.findAll(condiiton);
        return new OperationResult(new WhiteFunderListDTO(page));
    }

    /**
     * 获取非白名单列表
     *
     * @return
     */
    @Override
    public OperationResult<List<FunderDTO>> getBlackFunderList() {
        List<UserFunder> funders = userFunderService.findAll();
        List<FunderDTO> list = Lists.newArrayList();
        for (UserFunder funder : funders) {
            list.add(new FunderDTO(funder));
        }
        return new OperationResult(list);
    }

    /**
     * 添加资金方
     *
     * @param uid
     * @param funderUids
     * @return
     */
    @Override
    public OperationResult<Boolean> addFunder(Long uid, List<Long> funderUids) {
        if(funderUids == null || funderUids.size() == 0){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        List<UserFunder> funders = userFunderService.findAll(funderUids);
        if (funderUids.size() != funders.size()) {
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        userFunderService.update(true, funderUids);
        return new OperationResult(true);
    }

    /**
     * 删除资金方
     *
     * @param uid
     * @param funderUid
     * @return
     */
    @Override
    public OperationResult<Boolean> removeFunder(Long uid, Long funderUid) {
        if(funderUid == null){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        UserFunder userFunder = userFunderService.findOne(funderUid);
        if (userFunder == null || !userFunder.getIsWhite()) {
            return new OperationResult(RequestResultEnum.INVALID_ACCESS , false);
        }
        userFunderService.update(false, funderUid);
        return new OperationResult(true);
    }

    /**
     * 获取资金方信息
     *
     * @param funderUid
     * @return
     */
    @Override
    public OperationResult<FunderDTO> getFunderInfo(Long funderUid) {
        UserFunder funder = userFunderService.findOne(funderUid);
        return funder == null ? new OperationResult(): new OperationResult(new FunderDTO(funder));
    }

    /**
     * 获取资金提取列表
     *
     * @param uid
     * @param drawAssetSearchDTO
     * @return
     */
    @Override
    public OperationResult<DrawAssetListDTO> getDrawAssetList(Long uid, DrawAssetSearchDTO drawAssetSearchDTO) {
        Page<CapitalFlow> flows = capitalFlowService.findAll(drawAssetSearchDTO);
        return new OperationResult(new DrawAssetListDTO(flows));
    }

    /**
     * 资金提取审核通过
     *
     * @param uid
     * @param orderId
     * @return
     */
    @Override
    public OperationResult<Boolean> passDrawAssetCheck(Long uid, Long orderId) {
        CapitalFlow capitalFlow = capitalFlowService.findOne(orderId);
        if(capitalFlow == null){
            return new OperationResult(RequestResultEnum.MODIFY_ERROR, false);
        }
        if (!capitalFlow.getOrderStatus().equals(AssetStatusEnum.CHECKING.getOrderStatus())){
            return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
        }

        BankMoneyFlow bankMoneyFlow = new BankMoneyFlow();
        bankMoneyFlow.setAmount(capitalFlow.getAmount());
        bankMoneyFlow.setBankCard(capitalFlow.getUserBankNumber());
        bankMoneyFlow.setBankCardHolder(capitalFlow.getUserName());
        bankMoneyFlow.setBankName(capitalFlow.getUserBankName());
        bankMoneyFlow.setSubBankName(capitalFlow.getUserBankDetailedName());
        BankMoneyFlow withdraw = new BankMoneyFlow();
        try {
            withdraw = bankMoneyFlowOutService.withdraw(bankMoneyFlow);
        }catch (Exception e){
            return new OperationResult(RequestResultEnum.MODIFY_ERROR, false);
        }
        capitalFlow.setOrderStatus(AssetStatusEnum.BANK_PROCESSING.getOrderStatus());
        capitalFlow.setCheckUid(new UserDTO().getUid());
        capitalFlow.setCheckName(new UserDTO().getUserName());
        capitalFlow.setBankMoneyFlowId(withdraw.getId());
        capitalFlowService.update(capitalFlow);
        return new OperationResult(true);
    }

    /**
     * 资金提取审核不通过
     *
     * @param uid
     * @param orderId
     * @return
     */
    @Override
    public OperationResult<Boolean> failDrawAssetCheck(Long uid, Long orderId, String remaks) {
        CapitalFlow capitalFlow = capitalFlowService.findOne(orderId);
        if(capitalFlow == null){
            return new OperationResult(RequestResultEnum.MODIFY_ERROR, false);
        }
        if (!capitalFlow.getOrderStatus().equals(AssetStatusEnum.CHECKING.getOrderStatus())){
            return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
        }
        capitalFlow.setOrderStatus(AssetStatusEnum.CHECK_ERROR.getOrderStatus());
        capitalFlow.setRemarks(remaks);
        capitalFlowService.update(capitalFlow);
        BigDecimal amount = capitalFlow.getAmount();
        accountManageService.saveAccountStatement(capitalFlow.getUid(), WITHDRAW, USEABLEASSETS, amount, BigDecimal.ZERO);
        accountManageService.saveAccountStatement(capitalFlow.getUid(), WITHDRAW, FROZENASSETS, amount.negate(), BigDecimal.ZERO);
        return new OperationResult(true);
    }
}
