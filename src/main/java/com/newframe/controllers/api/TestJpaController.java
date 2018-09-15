package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.entity.test.TestUser;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.test.TestManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wangdong
 * @description:测试Jpa和druid是否配置成功的类
 */
@RestController
@RequestMapping("/api/testJpa/")
public class TestJpaController extends BaseController {

    @Autowired
    private TestManageService testManageService;

    @Autowired
    private AccountManageService accountManageServicel;

    /**
     * 接收实体类，对应于数据库的表
     * 通过主库进行数据库操作
     *
     * @param testUser
     * @return
     */
    @RequestMapping("saveTestUserByMaster")
    private JsonResult saveTestUserByMaster(TestUser testUser) {

        OperationResult<TestUser> result = testManageService.saveTestUserByMaster(testUser);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 通过从库进行数据库的操作
     *
     * @param testUser
     * @return
     */
    @RequestMapping("saveTestUserBySlave")
    private JsonResult saveTestUserBySlave(TestUser testUser) {

        OperationResult<TestUser> result = testManageService.saveTestUserBySlave(testUser);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }


    /**
     * 单条件查询，根据条件，利用Query进行查询
     *
     * @param age
     * @return
     */
    @RequestMapping("listTestUserByAge")
    private JsonResult listTestUserByAge(Integer age) {

        OperationResult<List<TestUser>> result = testManageService.listTestUserByAge(age);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 根据多条件查询
     *
     * @param age
     * @param name
     * @return
     */
    @RequestMapping("listTestUserByAgeAndName")
    private JsonResult listTestUserByAgeAndName(Integer age, String name) {

        OperationResult<List<TestUser>> result = testManageService.listTestUserByAgeAndName(age, name);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 分页查询
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("listTestUser")
    private JsonResult listTestUser(String name, Integer currentPage, Integer pageSize) {

        OperationResult<Page<TestUser>> result = testManageService.listTestUser(name, currentPage, pageSize);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());
    }

    /**
     * 更新，根据主键Id进行单个更新
     * @param uid
     * @return
     */
    @RequestMapping("updateTestUserByUid")
    private JsonResult updateTestUserByUid(Long uid){

        OperationResult<Boolean> result = testManageService.updateTestUserByUid(uid);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());
    }

    /**
     * 根据查询条件
     * 进行批量更新
     */
    @RequestMapping("updateTestUserByAge")
    private JsonResult updateTestUserByAge(Integer age) {
        OperationResult<Boolean> result = testManageService.updateTestUserByAge(age);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());
    }

    /**
     * 查询其中的一个
     * @param uid
     */
    @RequestMapping("getTestUserByUid")
    public JsonResult getTestUserByUid(Long uid){

        OperationResult<TestUser> result = testManageService.getTestUserByUid(uid);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());
    }

    @RequestMapping("getTestUserByQuery")
    public JsonResult getTestUserByQuery(Long uid){

        OperationResult<TestUser> result = testManageService.getTestUserByQuery(uid);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());
    }


    @RequestMapping("saveAccountRenterRentDetail")
    public JsonResult saveAccountRenterRentDetail(Long uid, Long orderId, String associatedOrderId, String productBrand, String productModel, String productColour, String productStorage, String productMemory, BigDecimal totalRentAccount, Integer monthNumber, BigDecimal payedAccount, BigDecimal unpayedAccount){

        OperationResult<Boolean> result = accountManageServicel.saveAccountRenterRentDetail(uid,orderId,associatedOrderId,productBrand,productModel,productColour,productStorage,productMemory,totalRentAccount,monthNumber,payedAccount,unpayedAccount);

        if (result.getSucc()){
            return success(result.getEntity());
        }

        return error(result.getCode(),result.getMessage());

    }
}
