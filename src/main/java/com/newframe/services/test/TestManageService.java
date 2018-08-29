package com.newframe.services.test;

import com.newframe.dto.OperationResult;
import com.newframe.entity.test.TestUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:wangdong
 * @description:外层的Service
 */
public interface TestManageService {

    /**
     * 保存TestUser的操作
     * @param testUser
     * @return
     */
    OperationResult<TestUser> saveTestUser(TestUser testUser);

    /**
     * 根据Id获取
     * @param uid
     * @return
     */
    OperationResult<TestUser> getTestUser(Long uid);

    /**
     * 操作主库
     * @param testUser
     * @return
     */
    OperationResult<TestUser> saveTestUserByMaster(TestUser testUser);

    /**
     * 操作从库
     * @param testUser
     * @return
     */
    OperationResult<TestUser> saveTestUserBySlave(TestUser testUser);

    /**
     * 根据Query查询
     * @param age
     * @return
     */
    OperationResult<List<TestUser>> listTestUserByAge(Integer age);

    /**
     * 多条件，根据Query查询
     * @param age
     * @param name
     * @return
     */
    OperationResult<List<TestUser>> listTestUserByAgeAndName(Integer age, String name);

    /**
     * 分页查询
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<Page<TestUser>> listTestUser(String name, Integer currentPage, Integer pageSize);

    /**
     * 更新
     * 根据主键Id进行单个更新
     * @return
     * @param uid
     */
    OperationResult<Boolean> updateTestUserByUid(Long uid);

    /**
     * 根据数据库中的某个条件进行更新
     * @param age
     * @return
     */
    OperationResult<Boolean> updateTestUserByAge(Integer age);

    /**
     * 单个账号的推送
     * 根据用户的uid给用户推送消息
     * @param uid
     * @param orderId
     * @param messTitle
     * @param messType 消息的类型：1:融资类消息，2:租机类消息，3:发货申请类的消息
     * @param messContent
     * @return
     */
    OperationResult<Boolean> sendMessToAllByUid(Long uid, Long orderId, String messTitle, Integer messType, String messContent);


    /**
     * 给安卓设备推送消息
     * @param uid
     * @param orderId
     * @param messTitle
     * @param messType
     * @param messContent
     * @return
     */
    OperationResult<Boolean> andSendMessageByUid(Long uid, Long orderId, String messTitle, Integer messType, String messContent);
}
