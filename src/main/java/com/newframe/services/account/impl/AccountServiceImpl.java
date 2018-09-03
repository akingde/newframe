package com.newframe.services.account.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.account.response.*;
import com.newframe.entity.account.*;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderSupplier;
import com.newframe.enums.SystemCode;
import com.newframe.repositories.dataQuery.account.*;
import com.newframe.repositories.dataQuery.order.OrderFunderQuery;
import com.newframe.repositories.dataSlave.account.*;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.repositories.dataSlave.order.OrderSupplierSlave;
import com.newframe.services.account.AccountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author:zww 31个接口
 * @description:账户相关模块的接口实现
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountFundingSlave accountFundingSlave;
    @Autowired
    AccountFundingFinanceAssetSlave accountFundingFinanceAssetSlave;
    @Autowired
    AccountFundingOverdueAssetSlave accountFundingOverdueAssetSlave;

    @Autowired
    AccountLessorMatterAssetSlave accountLessorMatterAssetSlave;
    @Autowired
    AccountLessorMatterAssetViewSlave accountLessorMatterAssetViewSlave;
    @Autowired
    AccountLessorOverdueAssetSlave accountLessorOverdueAssetSlave;
    @Autowired
    AccountLessorSlave accountLessorSlave;

    @Autowired
    AccountSupplierSlave accountSupplierSlave;
    @Autowired
    AccountSupplierSellSlave accountSupplierSellSlave;


    @Autowired
    OrderFunderSlave orderFunderSlave;
    @Autowired
    OrderHirerSlave orderHirerSlave;
    @Autowired
    OrderSupplierSlave orderSupplierSlave;

    @Autowired
    private AccountRenterSlave accountRenterSlave;

    @Autowired
    private AccountRenterRentSlave accountRenterRentSlave;

    @Autowired
    private AccountRenterAppointSupplierSlave accountRenterAppointSupplierSlave;

    @Autowired
    private AccountRenterFinancingMachineSlave accountRenterFinancingMachineSlave;

    @Autowired
    private AccountRenterFinancingSlave accountRenterFinancingSlave;

    @Autowired
    private AccountRenterRepaySlave accountRenterRepaySlave;

    @Autowired
    private AccountRenterRentMachineSlave accountRenterRentMachineSlave;

    @Override
    public JsonResult recharge(BigDecimal amount) {
        return null;
    }

    @Override
    public JsonResult withdrawDeposit() {
        return null;
    }

    @Override
    public JsonResult getRenterAccountInfo() {
        return null;
    }

    @Override
    public JsonResult getRenterAssetAccount() {
        return null;
    }

    @Override
    public JsonResult getRenterRentDetail(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderFinanceAccount() {
        return null;
    }

    @Override
    public JsonResult listRenterOrderFinance(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderFinanceDetail(Long orderId) {
        return null;
    }

    /**
     * 获取租赁商租赁账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     *
     * @return
     */
    @Override
    public JsonResult getRenterOrderRentAccount() {
        return null;
    }

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listRenterOrderRentAccount(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getRenterOrderRentDetail(Long orderId) {
        return null;
    }

    /**
     * 获取租赁商订单逾期账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getRenterOrderOverdueAccount() {
        return null;
    }

    /**
     * 租赁商订单逾期账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listRenterOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 租赁商订单逾期账户下
     * 逾期订单租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getRenterOrderOverdueDetail(Long orderId) {
        return null;
    }


    /**
     * 15.资金方获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金余额
     * 5、保证金垫付金额
     * 6、代收金额
     * 7、本月应收
     *
     * @return
     */
    @Override
    public JsonResult getFunderAssetAccount(Long uid) {
        AccountFunding entity = accountFundingSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        //DueAmount 转化成 DueInAmount
        AccountFundingDTO dto = new AccountFundingDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setDueInAmount(entity.getDueAmount());
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 16.获取资金方金融资产账户
     * 涉及到
     * 1、投资回报率
     * 2、市场平均投资回报率
     *
     * @return
     */
    @Override
    public JsonResult getFunderOrderFinancialAssets(Long uid) {
        AccountFundingFinanceAsset entity = accountFundingFinanceAssetSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountFundingFinanceAssetDTO dto = new AccountFundingFinanceAssetDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 17.获取资金方金融资产下
     * 投资明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listFunderOrderInvestment(Long uid, Integer currentPage, Integer pageSize) {
        if (null == currentPage || currentPage <= 1) {
            currentPage = 1;
        }
        if (null == pageSize || pageSize <= 1) {
            pageSize = 1;
        }
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        AccountFundingFinanceAssetQuery query = new AccountFundingFinanceAssetQuery();
        query.setUid(uid);
        Page<AccountFundingFinanceAsset> page = accountFundingFinanceAssetSlave.findAll(query, pageable);

        List<AccountFundingFinanceAssetListDTO> dtoList = new ArrayList<>();
        for (AccountFundingFinanceAsset entity : page.getContent()) {
            AccountFundingFinanceAssetListDTO dto = new AccountFundingFinanceAssetListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setInvestMonth(entity.getInvestDeadline());
            dto.setEarningsRate(entity.getYieldRate());
            dto.setOrderStatus(1);
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());
    }

    /**
     * 18.获取资金方金融资产下
     * 获取资金方金融资产下
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getFunderOrderInvestmentDetail(Long uid, Long orderId) {
        OrderFunderQuery query = new OrderFunderQuery();
        query.setFunderId(uid);
        query.setOrderId(orderId);
        query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
        List<OrderFunder> entitys = orderFunderSlave.findAll(query);
        if (null == entitys || entitys.isEmpty()) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountOrderFundingDTO dto = new AccountOrderFundingDTO();
        BeanUtils.copyProperties(entitys.get(0), dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 19.获取资金方逾期资产账户
     * 涉及到
     * 1、逾期金融合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getFunderOrderOverdueAssets(Long uid) {
        AccountFundingOverdueAsset entity = accountFundingOverdueAssetSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountFundingOverdueAssetDTO dto = new AccountFundingOverdueAssetDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 20.获取资金方逾期资产
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listFunderOrderOverdue(Long uid, Integer currentPage, Integer pageSize) {
        if (null == currentPage || currentPage <= 1) {
            currentPage = 1;
        }
        if (null == pageSize || pageSize <= 1) {
            pageSize = 1;
        }
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        AccountFundingOverdueAssetQuery query = new AccountFundingOverdueAssetQuery();
        query.setUid(uid);
        Page<AccountFundingOverdueAsset> page = accountFundingOverdueAssetSlave.findAll(query, pageable);

        List<AccountFundingOverdueAssetListDTO> dtoList = new ArrayList<>();
        for (AccountFundingOverdueAsset entity : page.getContent()) {
            AccountFundingOverdueAssetListDTO dto = new AccountFundingOverdueAssetListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setInvestType(entity.getInvestWay());
            dto.setInvestMonth(entity.getInvestDeadline());
            dto.setPayedAmount(entity.getRepayAmount());
            dto.setUnpayAmount(entity.getDueAmount());
            dto.setOverdueDays(entity.getOverdueDay());
            dto.setPayType(entity.getRepayWay());
            dto.setOrderStatus(1);
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());
    }

    /**
     * 21.获取资金方逾期资产
     * 逾期明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getFunderOrderOverdueDetail(Long uid, Long orderId) {
        //查询相同数据表，返回结果相同
        return getFunderOrderInvestmentDetail(uid, orderId);
    }

    /**
     * 22.供应商获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     *
     * @return
     */
    @Override
    public JsonResult getSupplierAssetAccount(Long uid) {
        AccountSupplier entity = accountSupplierSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountSupplierDTO dto = new AccountSupplierDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setUseableAmount(entity.getUseableAmount());
        dto.setFrozenAssets(entity.getFrozenAsset());
        dto.setTotalAssets(entity.getTotalAsset());
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 23.获取供应商销售账户
     * 涉及到
     * 1、累计营收
     * 2、累计销售数量
     * 3、待发货数量
     *
     * @return
     */
    @Override
    public JsonResult getSupplierOrderSellAssets(Long uid) {
        AccountSupplierSell entity = accountSupplierSellSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountSupplierSellDTO dto = new AccountSupplierSellDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 24.获取供应商销售账户下
     * 销售明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listSupplierOrderSell(Long uid, Integer currentPage, Integer pageSize) {
        if (null == currentPage || currentPage <= 1) {
            currentPage = 1;
        }
        if (null == pageSize || pageSize <= 1) {
            pageSize = 1;
        }
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        Page<OrderSupplier> page = orderSupplierSlave.findAll(pageable);

        List<AccountSupplierSellListDTO> dtoList = new ArrayList<>();
        for (OrderSupplier entity : page.getContent()) {
            AccountSupplierSellListDTO dto = new AccountSupplierSellListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setProductMemory(entity.getProductRandomMemory());
            dto.setRenterId(entity.getMerchantId());
            dto.setRenterName(entity.getMerchantName());
            dto.setUserId(entity.getUid());
            dto.setUserName(entity.getReceiverName());
            dto.setDeliverTime(entity.getExpressTime());
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());
    }

    /**
     * 25.出租方获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金
     * 5、代收金额
     * 6、本月应收
     *
     * @return
     */
    @Override
    public JsonResult getHirerAssetAccount(Long uid) {
        AccountLessor entity = accountLessorSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountLessorDTO dto = new AccountLessorDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, entity);
    }

    /**
     * 26.获取出租方实物资产账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     * 5、投资回报率
     * 6、市场平均投资回报率
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderMaterialAssets(Long uid) {
        AccountLessorMatterAssetView entity = accountLessorMatterAssetViewSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountLessorMatterAssetViewDTO dto = new AccountLessorMatterAssetViewDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 27.获取出租方实物资产账户下
     * 实物明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listHirerOrderMaterial(Long uid, Integer currentPage, Integer pageSize) {
        if (null == currentPage || currentPage <= 1) {
            currentPage = 1;
        }
        if (null == pageSize || pageSize <= 1) {
            pageSize = 1;
        }
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        Page<AccountLessorMatterAsset> page = accountLessorMatterAssetSlave.findAll(pageable);

        List<AccountLessorMatterAssetListDTO> dtoList = new ArrayList<>();
        for (AccountLessorMatterAsset entity : page.getContent()) {
            AccountLessorMatterAssetListDTO dto = new AccountLessorMatterAssetListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setUserId(entity.getRenterId());
            dto.setUserName(entity.getRenterName());
            dto.setDeliverTime(entity.getRentTime());
            dto.setPurchaseAmount(entity.getMatterPrice());
            dto.setRentMonth(entity.getRentDeadline());
            dto.setTotalRentAmount(entity.getTotalAmount());
            dto.setOrderStatus(1);
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());
    }

    /**
     * 28.获取出租方实物资产账户下
     * 实物明细列表
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderMaterialDetail(Long uid, Long orderId) {
        OrderFunderQuery query = new OrderFunderQuery();
        query.setOrderId(orderId);
        List<OrderHirer> entitys = orderHirerSlave.findAll(query);
        if (null == entitys || entitys.isEmpty()) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountOrderFundingDTO dto = new AccountOrderFundingDTO();
        BeanUtils.copyProperties(entitys.get(0), dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 29.获取出租方逾期资产账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderOverdueAssets(Long uid) {
        AccountLessorOverdueAsset entity = accountLessorOverdueAssetSlave.findOne(uid);
        if (null == entity) {
            return new JsonResult(SystemCode.SUCCESS404, null);
        }
        AccountLessorOverdueAssetDTO dto = new AccountLessorOverdueAssetDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 30.获取出租方逾期资产账户下
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listHirerOrderOverdue(Long uid, Integer currentPage, Integer pageSize) {
        if (null == currentPage || currentPage <= 1) {
            currentPage = 1;
        }
        if (null == pageSize || pageSize <= 1) {
            pageSize = 1;
        }
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        Page<AccountLessorOverdueAsset> page = accountLessorOverdueAssetSlave.findAll(pageable);

        List<AccountLessorOverdueAssetListDTO> dtoList = new ArrayList<>();
        for (AccountLessorOverdueAsset entity : page.getContent()) {
            AccountLessorOverdueAssetListDTO dto = new AccountLessorOverdueAssetListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setInvestType(entity.getInvestWay());
            dto.setOverdueDays(entity.getOverdueDay());
            dto.setPayType(entity.getRepayWay());
            dto.setOrderStatus(entity.getOverdueStatus());
            dto.setInvestMonth(entity.getInvestDeadline());
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());

    }

    /**
     * 31.获取出租方逾期资产账户下
     * 逾期明细列表
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderOverdueDetail(Long uid, Long orderId) {
        return getHirerOrderMaterialDetail(uid, orderId);
    }

    /**
     * 获取租赁商账户资产
     *
     * @param uid
     * @return
     */
    @Override
    public AccountRenter getAccountRenter(Long uid) {
        if (null == uid) {
            return null;
        }

        Optional<AccountRenter> result = accountRenterSlave.findById(uid);
        if (!result.isPresent()) {
            return null;
        }
        return result.get();
    }

    /**
     * 获取租赁商账户资产下的租赁明细表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<AccountRenterRent> getAccountRenterRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid || null == currentPage || null == pageSize) {
            return null;
        }

        AccountRenterRentQuery query = new AccountRenterRentQuery();
        query.setUid(uid);
        query.setOrderStatus(orderStatus);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<AccountRenterRent> rents = accountRenterRentSlave.findAll(pageRequest);
        return rents;
    }

    /**
     * 查询租赁商关联的供应商
     *
     * @param uid
     * @return
     */
    @Override
    public List<AccountRenterAppointSupplier> listAccountRenterAppointSupplier(Long uid) {
        if (null == uid) {
            return Collections.EMPTY_LIST;
        }
        AccountRenterAppointSupplierQuery query = new AccountRenterAppointSupplierQuery();
        query.setUid(uid);
        List<AccountRenterAppointSupplier> result = accountRenterAppointSupplierSlave.findAll(query);
        return CollectionUtils.isEmpty(result) ? Collections.EMPTY_LIST : result;
    }

    /**
     * 获取租赁商订单融资账户
     *
     * @param uid
     * @return
     */
    @Override
    public AccountRenterFinancingMachine getAccountRenterFinancingMachine(Long uid) {
        if (null == uid) {
            return null;
        }
        Optional<AccountRenterFinancingMachine> result = accountRenterFinancingMachineSlave.findById(uid);
        if (!result.isPresent()) {
            return null;
        }
        return result.get();
    }

    /**
     * 我是租赁商订单融资账户订单融资列表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<AccountRenterFinancing> getAccountRenterFinancing(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid || null == currentPage || null == pageSize) {
            return null;
        }

        AccountRenterFinancingQuery query = new AccountRenterFinancingQuery();
        query.setUid(uid);
        query.setOrderStatus(orderStatus);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, sort);

        return accountRenterFinancingSlave.findAll(pageRequest);
    }

    /**
     * 我是租赁商订单融资账户订单融资列表查看订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public List<AccountRenterRepay> listAccountRenterRepay(Long orderId) {
        if (null == orderId) {
            return null;
        }

        AccountRenterRepayQuery query = new AccountRenterRepayQuery();
        query.setOrderId(orderId);
        List<AccountRenterRepay> accountRenterRepays = accountRenterRepaySlave.findAll(query);
        return CollectionUtils.isEmpty(accountRenterRepays) ? Collections.EMPTY_LIST : accountRenterRepays;
    }

    /**
     * 获取租赁商租赁账户
     *
     * @param uid
     * @return
     */
    @Override
    public AccountRenterRentMachine getAccountRenterRentMachine(Long uid) {
        if (null == uid) {
            return null;
        }
        Optional<AccountRenterRentMachine> result = accountRenterRentMachineSlave.findById(uid);
        if (!result.isPresent()) {
            return null;
        }
        return result.get();
    }
}
