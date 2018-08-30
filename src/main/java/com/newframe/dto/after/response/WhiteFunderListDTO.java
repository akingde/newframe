package com.newframe.dto.after.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.UserFunder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class WhiteFunderListDTO {

    private Long total;
    private List<FunderDTO> result;

    public WhiteFunderListDTO(Page<UserFunder> page) {
        if (page == null){
            this.total = page.getTotalElements();
        }else {
            this.total = page.getTotalElements();
            List<UserFunder> content = page.getContent();
            List<FunderDTO> list = Lists.newArrayList();
            for (UserFunder userFunder : content) {
                list.add(new FunderDTO(userFunder));
            }
            this.result = list;
        }
    }
}
