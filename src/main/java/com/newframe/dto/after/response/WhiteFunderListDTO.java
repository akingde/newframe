package com.newframe.dto.after.response;

import lombok.Data;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class WhiteFunderListDTO {

    private Long total;
    private List<FunderDTO> result;
}
