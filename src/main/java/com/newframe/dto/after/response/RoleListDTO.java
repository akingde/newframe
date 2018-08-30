package com.newframe.dto.after.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.UserRoleApply;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class RoleListDTO {

    private Long total;
    private List<RoleApplyListDTO> result;

    public RoleListDTO(Page<UserRoleApply> page) {
        List<UserRoleApply> content = page.getContent();
        List<RoleApplyListDTO> list = Lists.newArrayList();
        if (page == null){
            this.total = 0L;
            this.result = list;
        }else {
            this.total = page.getTotalElements();
            for (UserRoleApply userRoleApply : content) {
                list.add(new RoleApplyListDTO(userRoleApply));
            }
            this.result = list;
        }
    }
}
