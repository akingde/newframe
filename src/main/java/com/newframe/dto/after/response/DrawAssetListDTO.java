package com.newframe.dto.after.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.CapitalFlow;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class DrawAssetListDTO {

    private Long total;
    private List<DrawAssetDTO> result;

    public DrawAssetListDTO(Page<CapitalFlow> capitalFlows) {
        this.total = capitalFlows.getTotalElements();
        List<CapitalFlow> content = capitalFlows.getContent();
        List<DrawAssetDTO> result = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(content)){
            for (CapitalFlow capitalFlow : content) {
                result.add(new DrawAssetDTO(capitalFlow));
            }
        }
        this.result = result;
    }
}
