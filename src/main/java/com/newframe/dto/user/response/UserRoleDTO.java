package com.newframe.dto.user.response;

import com.newframe.entity.user.UserFunder;
import com.newframe.entity.user.UserHirer;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.RoleEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
@Data
public class UserRoleDTO{

    private Long uid;
    private Integer roleId;
    private String name;//商家名称
    private String merchantPhone;//手机号
    private String legalEntity;//法人
    private String legalEntityIdNumber;//法人身份证号
    private String businessListenNumber;//营业执照
    private String[] businessListen;//营业执照文件url
    private Integer status;

    /**
     * 租赁商
     */
    @Data
    public static class RentMechant extends UserRoleDTO {
        private String[] highestDegreeDiploma;//最高学历
        private String[] drivingLicense;//行驶证
        private String[] houseProprietaryCertificate;//房产证

        public RentMechant(UserRentMerchant userRentMerchant) {
            this.setUid(userRentMerchant.getUid());
            this.setRoleId(userRentMerchant.getRoleId());
            this.setName(userRentMerchant.getMerchantName());
            this.setMerchantPhone(userRentMerchant.getMerchantPhoneNumber());
            this.setLegalEntity(userRentMerchant.getLegalEntity());
            this.setLegalEntityIdNumber(userRentMerchant.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userRentMerchant.getBusinessLicenseNumber());
            this.setBusinessListen(StringUtils.split(userRentMerchant.getBusinessLicenseFile(), ","));
            this.setStatus(userRentMerchant.getRoleStatus());
            this.highestDegreeDiploma = StringUtils.split(userRentMerchant.getHighestDegreeDiplomaFile(), ",");
            this.drivingLicense = StringUtils.split(userRentMerchant.getDrivingLicenseFile() , ",");
            this.houseProprietaryCertificate = StringUtils.split(userRentMerchant.getHouseProprietaryCertificateFile(), ",");
        }
    }

    @Data
    public static class BigRentMechant extends RentMechant {
        private String legalEntityIdCardFront; //身份证正面
        private String legalEntityIdCardBack; // 身份证背面
        private String contactsPhoneNumber;//联系人手机号
        private String job;//职位
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//紧急联系人手机号
        private Integer relationship;//关系
        private Boolean appoint;    //是否指定供应商
        public BigRentMechant(UserRentMerchant userRentMerchant) {
            super(userRentMerchant);
            this.legalEntityIdCardFront = userRentMerchant.getIdCardFrontFile();
            this.legalEntityIdCardBack = userRentMerchant.getIdCardBackFile();
            this.contactsPhoneNumber = userRentMerchant.getContactsPhoneNumber();
            this.job = userRentMerchant.getJob();
            this.topContacts = userRentMerchant.getTopContacts();
            this.topContactsPhoneNumber = userRentMerchant.getTopContactsPhoneNumber();
            this.relationship = userRentMerchant.getRelationship();
            this.appoint = userRentMerchant.getAppoint();
        }
    }
    @Data
    public static class SmallRentMechant extends RentMechant{
        private Long sonUid;
        private String rentMerchantAddress;
        private Integer provinceId;
        private String provinceName;
        private Integer cityId;
        private String cityName;
        private Integer countyId;
        private String countyName;
        private String consigneeAddress;

        public SmallRentMechant(UserRentMerchant userRentMerchant) {
            super(userRentMerchant);
            this.sonUid = userRentMerchant.getUid();
            this.rentMerchantAddress = userRentMerchant.getRentMerchantAddress();
            this.provinceId = userRentMerchant.getProvinceId();
            this.provinceName = userRentMerchant.getProvinceName();
            this.cityId = userRentMerchant.getCityId();
            this.cityName = userRentMerchant.getCityName();
            this.countyId = userRentMerchant.getCountyId();
            this.countyName = userRentMerchant.getCountyName();
            this.consigneeAddress = userRentMerchant.getConsigneeAddress();
        }
    }

    /**
     * 资金方
     */
    @Data
    public static class Funder extends UserRoleDTO {
        private String legalEntityIdCardFront; //身份证正面
        private String legalEntityIdCardBack; // 身份证背面
        private String contactsPhoneNumber;//联系人手机号
        private String job;//职位
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
        private String[] letterOfAttorney;//委托授权书
        private String[] businessQualification;//经营资质

        public Funder(UserFunder userFunder) {
            this.setUid(userFunder.getUid());
            this.setRoleId(RoleEnum.FUNDER.getRoleId());
            this.setName(userFunder.getMerchantName());
            this.setMerchantPhone(userFunder.getPhoneNumber());
            this.setLegalEntity(userFunder.getLegalEntity());
            this.setLegalEntityIdNumber(userFunder.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userFunder.getBusinessLicenseNumber());
            this.setBusinessListen(StringUtils.split(userFunder.getBusinessLicenseFile(), ","));
            this.setStatus(userFunder.getRoleStatus());
            this.legalEntityIdCardFront = userFunder.getIdCardFrontFile();
            this.legalEntityIdCardBack = userFunder.getIdCardBackFile();
            this.contactsPhoneNumber = userFunder.getContactsPhoneNumber();
            this.job = userFunder.getJob();
            this.topContacts = userFunder.getTopContacts();
            this.topContactsPhoneNumber = userFunder.getTopContactsPhoneNumber();
            this.relationship = userFunder.getRelationship();
            this.letterOfAttorney = StringUtils.split(userFunder.getLetterOfAttorneyFile(), ",");
            this.businessQualification = StringUtils.split(userFunder.getBusinessQualificationFile(), ",");
        }
    }

    /**
     * 出租方
     */
    @Data
    public static class Hirer extends UserRoleDTO {
        private String legalEntityIdCardFront; //身份证正面
        private String legalEntityIdCardBack; // 身份证背面
        private String contactsPhoneNumber;//联系人手机号
        private String job;//职位
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系

        public Hirer(UserHirer userHirer) {
            this.setUid(userHirer.getUid());
            this.setRoleId(RoleEnum.HIRER.getRoleId());
            this.setName(userHirer.getMerchantName());
            this.setMerchantPhone(userHirer.getPhoneNumber());
            this.setLegalEntity(userHirer.getLegalEntity());
            this.setLegalEntityIdNumber(userHirer.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userHirer.getBusinessLicenseNumber());
            this.setBusinessListen(StringUtils.split(userHirer.getBusinessLicenseFile(), ","));
            this.setStatus(userHirer.getRoleStatus());
            this.legalEntityIdCardFront = userHirer.getIdCardFrontFile();
            this.legalEntityIdCardBack = userHirer.getIdCardBackFile();
            this.contactsPhoneNumber = userHirer.getContactsPhoneNumber();
            this.job = userHirer.getJob();
            this.topContacts = userHirer.getTopContacts();
            this.topContactsPhoneNumber = userHirer.getTopContactsPhoneNumber();
            this.relationship = userHirer.getRelationship();
        }
    }

    /**
     * 供应商
     */
    @Data
    public static class Supplier extends UserRoleDTO {
        private String legalEntityIdCardFront; //身份证正面
        private String legalEntityIdCardBack; // 身份证背面
        private String contactsPhoneNumber;//联系人手机号
        private String job;//职位
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
        public Supplier(UserSupplier userSupplier) {
            this.setUid(userSupplier.getUid());
            this.setRoleId(RoleEnum.SUPPLIER.getRoleId());
            this.setName(userSupplier.getMerchantName());
            this.setMerchantPhone(userSupplier.getPhoneNumber());
            this.setLegalEntity(userSupplier.getLegalEntity());
            this.setLegalEntityIdNumber(userSupplier.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userSupplier.getBusinessLicenseNumber());
            this.setBusinessListen(StringUtils.split(userSupplier.getBusinessLicenseFile(), ","));
            this.setStatus(userSupplier.getRoleStatus());
            this.legalEntityIdCardFront = userSupplier.getIdCardFrontFile();
            this.legalEntityIdCardBack = userSupplier.getIdCardBackFile();
            this.contactsPhoneNumber = userSupplier.getContactsPhoneNumber();
            this.job = userSupplier.getJob();
            this.topContacts = userSupplier.getTopContacts();
            this.topContactsPhoneNumber = userSupplier.getTopContactsPhoneNumber();
            this.relationship = userSupplier.getRelationship();
        }
    }
}
