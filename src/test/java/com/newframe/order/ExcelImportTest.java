package com.newframe.order;

import com.google.gson.Gson;
import com.newframe.NewFrameApplicationTests;
import com.newframe.dto.order.request.DeliverInfoDTO;
import com.newframe.services.order.OrderBaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author kfm
 * @date 2018.10.03 15:22
 */
@Slf4j
public class ExcelImportTest extends NewFrameApplicationTests {

    @Autowired
    OrderBaseService orderBaseService;

    @Test
    public void importFileTest() throws FileNotFoundException {
        String path = "C:\\Users\\33\\Desktop\\批量发货.xls";
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        List<DeliverInfoDTO> deliverInfoDTOS = orderBaseService.wrapBatchDeliver(inputStream);
        log.info(new Gson().toJson(deliverInfoDTOS));
    }
}
