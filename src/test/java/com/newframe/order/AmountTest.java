package com.newframe.order;

import com.google.gson.Gson;
import com.newframe.NewFrameApplicationTests;
import com.newframe.dto.order.response.SupplierInfoDTO;
import com.newframe.services.order.FormulaService;
import com.newframe.services.order.impl.FormulaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Finance;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author kfm
 * @date 2018.09.27 17:56
 */
@Slf4j
public class AmountTest {



    @Test
    public void rentPrice(){
        Integer buyPrice = 10000;
        DecimalFormat format = new DecimalFormat("#.##");
        System.out.println(format.format(Finance.pmt(0.15/12,24,Double.valueOf(buyPrice),0,1)));
    }

    @Test
    public void testFormula(){
        FormulaService formulaService = new FormulaServiceImpl();
        BigDecimal financeAmount = BigDecimal.valueOf(6200);
        double rate = 0.15;
        Integer periods = 10;
        BigDecimal monthPayment = BigDecimal.valueOf(200);
        SupplierInfoDTO supplierInfoDTO = new SupplierInfoDTO();
        supplierInfoDTO.setDeposit(BigDecimal.valueOf(6500*0.15));
        supplierInfoDTO.setMonthPayment(monthPayment);
        supplierInfoDTO.setDownPayment(monthPayment);
        supplierInfoDTO.setAccidentBenefit(BigDecimal.ZERO);
        supplierInfoDTO.setFinancingAmount(financeAmount);
        supplierInfoDTO.setSupplierName("哦买噶");
        supplierInfoDTO.setSupplierId(1L);
        supplierInfoDTO.setAveragePrincipal(formulaService.getAveragePrincipal(rate,periods,monthPayment));
        supplierInfoDTO.setOnePrincipal(formulaService.getOnePrincipal(financeAmount,supplierInfoDTO.getAveragePrincipal()));
        supplierInfoDTO.setSumAmount(formulaService.getSumAmount(financeAmount,supplierInfoDTO.getAveragePrincipal(),rate,periods));
        log.info(new Gson().toJson(supplierInfoDTO));

    }
}
