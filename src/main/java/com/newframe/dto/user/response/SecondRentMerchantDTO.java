package com.newframe.dto.user.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.UserRentMerchant;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *  小B分页查询
 * @author WangBin
 */
@Data
public class SecondRentMerchantDTO {

    private Long total;
    private List<UserRoleDTO.SmallRentMechant> result;

    public SecondRentMerchantDTO(Page<UserRentMerchant> merchants) {
        if (merchants == null){
            total = 0L;
        }else{
            total = merchants.getTotalElements();
            List<UserRentMerchant> content = merchants.getContent();
            List<UserRoleDTO.SmallRentMechant> list = Lists.newArrayList();
            for (UserRentMerchant userRentMerchant : content) {
                list.add(new UserRoleDTO.SmallRentMechant(userRentMerchant));
            }
            this.result = list;
        }
    }
}
