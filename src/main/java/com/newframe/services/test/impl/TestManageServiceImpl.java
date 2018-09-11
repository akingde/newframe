package com.newframe.services.test.impl;

import com.newframe.dto.OperationResult;
import com.newframe.entity.message.UserMessage;
import com.newframe.entity.test.TestUser;
import com.newframe.enums.BizErrorCode;
import com.newframe.services.common.CommonService;
import com.newframe.services.test.TestManageService;
import com.newframe.services.test.TestService;
import com.newframe.utils.cache.IdGlobalGenerator;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author:wangdong
 * @description:外层的Service
 */
@Service
public class TestManageServiceImpl implements TestManageService {

    @Autowired
    private TestService testService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IdGlobalGenerator idGlobal;

    /**androidAccessId*/
    @Value("${android.accessId}")
    private Long andAccessId;

    /**androidsecretKey*/
    @Value("${android.secretKey}")
    private String andSecretKey;

    @Value("${ios.accessId}")
    private Long iosAccessId;

    @Value("${ios.secretKey}")
    private String iosSecretKey;

    /**超时时间*/
    @Value("${message.expireTime}")
    private Integer expireTime;

    /**默认值为0,无需配置和更改*/
    private Integer deviceType = 0;

    /**
     * 保存TestUser的操作
     *
     * @param testUser
     * @return
     */
    @Override
    public OperationResult<TestUser> saveTestUser(TestUser testUser) {
        if (null == testUser) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        TestUser result = testService.saveTestUserByMaster(testUser);
        Optional<TestUser> optionalUser = Optional.ofNullable(testUser);

        if (!optionalUser.isPresent()) {
            return new OperationResult<>(BizErrorCode.SAVE_INFO_ERROR);
        }

        return new OperationResult<>(result);
    }

    /**
     * 根据Id获取
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<TestUser> getTestUser(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        Optional<TestUser> testUser = testService.getTestUser(uid);

        return new OperationResult<>(testUser.get());
    }

    @Override
    public OperationResult<TestUser> saveTestUserByMaster(TestUser testUser) {
        if (null == testUser) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        TestUser result = testService.saveTestUserByMaster(testUser);
        Optional<TestUser> optionalUser = Optional.ofNullable(testUser);

        if (!optionalUser.isPresent()) {
            return new OperationResult<>(BizErrorCode.SAVE_INFO_ERROR);
        }

        return new OperationResult<>(result);
    }

    @Override
    public OperationResult<TestUser> saveTestUserBySlave(TestUser testUser) {
        if (null == testUser) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        TestUser result = testService.saveTestUserBySlave(testUser);
        Optional<TestUser> optionalUser = Optional.ofNullable(testUser);

        if (!optionalUser.isPresent()) {
            return new OperationResult<>(BizErrorCode.SAVE_INFO_ERROR);
        }

        return new OperationResult<>(result);
    }

    /**
     * 根据Query查询
     *
     * @param age
     * @return
     */
    @Override
    public OperationResult<List<TestUser>> listTestUserByAge(Integer age) {
        if (null == age){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        List<TestUser> testUsers = testService.listTestUserByAge(age);

        return new OperationResult<>(testUsers);
    }

    /**
     * 多条件，根据Query查询
     *
     * @param age
     * @param name
     * @return
     */
    @Override
    public OperationResult<List<TestUser>> listTestUserByAgeAndName(Integer age, String name) {

        if (null == age || StringUtils.isEmpty(name)){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        List<TestUser> testUsers = testService.listTestUserByAgeAndName(age,name);

        return new OperationResult<>(testUsers);
    }

    /**
     * 分页查询
     *
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<Page<TestUser>> listTestUser(String name, Integer currentPage, Integer pageSize) {

        if (null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        Page<TestUser> testUserPage = testService.listTestUser(name,currentPage,pageSize);

        return new OperationResult<>(testUserPage);
    }

    /**
     * 更新
     * 根据主键Id进行单个更新
     * @return
     * @param uid
     */
    @Override
    public OperationResult<Boolean> updateTestUserByUid(Long uid) {

        Boolean result = testService.updateTestUser(uid);

        return new OperationResult<>(result);
    }

    /**
     * 根据数据库中的某个条件进行更新
     *
     * @param age
     * @return
     */
    @Override
    public OperationResult<Boolean> updateTestUserByAge(Integer age) {

        if (null == age){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        Boolean result = testService.updateTestUserByAge(age);

        return new OperationResult<>(result);
    }

    /**
     * 根据Uid查询
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<TestUser> getTestUserByUid(Long uid) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        TestUser testUser = testService.getTestUserByUid(uid);

        return new OperationResult<>(testUser);
    }

    /**
     * 根据Query查询单个数据
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<TestUser> getTestUserByQuery(Long uid) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        TestUser testUser = testService.getTestUserByQuery(uid);

        return new OperationResult<>(testUser);
    }

    /**
     * 单个账号的推送
     * 根据用户的uid给用户推送消息
     *
     * @param uid
     * @param roleId
     * @param associatedOrderId
     * @param orderId
     * @param messTitle
     * @param messType    消息的类型：1:融资类消息，2:租机类消息，3:发货申请类的消息
     * @param messContent
     * @return
     */
    @Override
    public OperationResult<Boolean> sendMessToAllByUid(Long uid, Integer roleId, Long associatedOrderId, Long orderId, String messTitle, Integer messType, String messContent) {
        if (null ==uid || null == orderId || StringUtils.isEmpty(messTitle) || null == messType ||
                StringUtils.isEmpty(messContent) ){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        OperationResult<Boolean> result = andSendMessageByUid(uid,orderId,messTitle,messType,messContent);
        OperationResult<Boolean> result1 = iosSendMessageByUid(uid,orderId,messTitle,messType,messContent);
        UserMessage userMessage = new UserMessage(uid,roleId,orderId,associatedOrderId,messTitle,messType,messContent);
        userMessage.setId(idGlobal.getSeqId(UserMessage.class));

        OperationResult<Boolean> result2 = commonService.saveUserMessage(userMessage);

        return new OperationResult<>(true);
    }

    /**
     * 给安卓设备推送消息
     *
     * @param uid
     * @param orderId
     * @param messTitle
     * @param messType
     * @param messContent
     * @return
     */
    @Override
    public OperationResult<Boolean> andSendMessageByUid(Long uid, Long orderId, String messTitle, Integer messType, String messContent) {
        if (null == uid || StringUtils.isEmpty(messTitle) || null == messType ||
                StringUtils.isEmpty(messContent)){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        XingeApp xingeApp = new XingeApp(andAccessId, andSecretKey);
        Message message = new Message();
        message.setType(Message.TYPE_MESSAGE);
        message.setExpireTime(expireTime);

        message.setTitle(messTitle);
        message.setContent(messContent);

        //附加参数
        Map<String,String> paramMap = new HashMap<>();
        //一定要是String类型的
        paramMap.put("uid",uid.toString());
        paramMap.put("orderId",orderId.toString());
        paramMap.put("messType",messType.toString());

        JSONObject ret = xingeApp.pushSingleAccount(deviceType, uid.toString(), message);
        System.out.println("发送消息成功");

        return new OperationResult<>(true);
    }

    /**
     * 给Ios设备推送消息
     *
     * @param uid
     * @param orderId
     * @param messTitle
     * @param messType
     * @param messContent
     * @return
     */
    @Override
    public OperationResult<Boolean> iosSendMessageByUid(Long uid, Long orderId, String messTitle, Integer messType, String messContent) {
        if (null == uid || StringUtils.isEmpty(messTitle) || null == messType ||
                StringUtils.isEmpty(messContent)){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        XingeApp xingeApp = new XingeApp(iosAccessId,iosSecretKey);
        MessageIOS messageIOS = new MessageIOS();
        messageIOS.setExpireTime(expireTime);
        messageIOS.setAlert(messTitle);
        messageIOS.setBadge(1);
        messageIOS.setSound("beep.wav");
        TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        messageIOS.addAcceptTime(acceptTime1);
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("messType", messType);
        custom.put("messContent", messContent);
        messageIOS.setCustom(custom);

        JSONObject ret = xingeApp.pushSingleAccount(0, uid.toString(), messageIOS, XingeApp.IOSENV_DEV);

        return new OperationResult<>(true);

    }


}
