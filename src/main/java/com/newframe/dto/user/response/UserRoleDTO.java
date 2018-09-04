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
    private String legalEntity;//法人
    private String legalEntityIdNuber;//法人身份证号
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
        private Boolean appoint;    //是否指定供应商

        public RentMechant(UserRentMerchant userRentMerchant) {
            this.setUid(userRentMerchant.getUid());
            this.setRoleId(userRentMerchant.getRoleId());
            this.setName(userRentMerchant.getMerchantName());
            this.setLegalEntity(userRentMerchant.getLegalEntity());
            this.setLegalEntityIdNuber(userRentMerchant.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userRentMerchant.getBusinessLicenseNumber());
            this.setBusinessListen(userRentMerchant.getBusinessLicenseFile().split(","));
            this.setStatus(userRentMerchant.getRoleStatus());
            this.highestDegreeDiploma = StringUtils.isNotEmpty(userRentMerchant.getHighestDegreeDiplomaFile())?
                    userRentMerchant.getHighestDegreeDiplomaFile().split(","):
                    null;
            this.drivingLicense = StringUtils.isNotEmpty(userRentMerchant.getDrivingLicenseFile())?
                    userRentMerchant.getDrivingLicenseFile().split(","):
                    null;
            this.houseProprietaryCertificate = StringUtils.isNotEmpty(userRentMerchant.getHouseProprietaryCertificateFile())?
                    userRentMerchant.getHouseProprietaryCertificateFile().split(","):
                    null;
            this.appoint = userRentMerchant.getAppoint();
        }
    }

    @Data
    public static class SmallRentMechant extends RentMechant{
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
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
        private String[] letterOfAttorney;//委托授权书
        private String[] businessQualification;//经营资质

        public Funder(UserFunder userFunder) {
            this.setUid(userFunder.getUid());
            this.setRoleId(RoleEnum.FUNDER.getRoleId());
            this.setName(userFunder.getMerchantName());
            this.setLegalEntity(userFunder.getLegalEntity());
            this.setLegalEntityIdNuber(userFunder.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userFunder.getBusinessLicenseNumber());
            this.setBusinessListen(userFunder.getBusinessLicenseFile().split(","));
            this.setStatus(userFunder.getRoleStatus());
            this.topContacts = userFunder.getTopContacts();
            this.topContactsPhoneNumber = userFunder.getTopContactsPhoneNumber();
            this.relationship = userFunder.getRelationship();
            this.letterOfAttorney = StringUtils.isNotEmpty(userFunder.getLetterOfAttorneyFile())?
                    userFunder.getLetterOfAttorneyFile().split(","):
                    null;
            this.businessQualification = StringUtils.isNotEmpty(userFunder.getBusinessQualificationFile())?
                    userFunder.getBusinessQualificationFile().split(","):
                    null;
        }
    }

    /**
     * 出租方
     */
    @Data
    public static class Hirer extends UserRoleDTO {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系

        public Hirer(UserHirer userHirer) {
            this.setUid(userHirer.getUid());
            this.setRoleId(RoleEnum.HIRER.getRoleId());
            this.setName(userHirer.getMerchantName());
            this.setLegalEntity(userHirer.getLegalEntity());
            this.setLegalEntityIdNuber(userHirer.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userHirer.getBusinessLicenseNumber());
            this.setBusinessListen(userHirer.getBusinessLicenseFile().split(","));
            this.setStatus(userHirer.getRoleStatus());
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
        public Supplier(UserSupplier userSupplier) {
            this.setUid(userSupplier.getUid());
            this.setRoleId(RoleEnum.SUPPLIER.getRoleId());
            this.setName(userSupplier.getMerchantName());
            this.setLegalEntity(userSupplier.getLegalEntity());
            this.setLegalEntityIdNuber(userSupplier.getLegalEntityIdNumber());
            this.setBusinessListenNumber(userSupplier.getBusinessLicenseNumber());
            this.setBusinessListen(userSupplier.getBusinessLicenseFile().split(","));
            this.setStatus(userSupplier.getRoleStatus());
        }
    }
}
