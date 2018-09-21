package com.newframe.services.userbase.impl;

import com.newframe.entity.user.ConfigRate;
import com.newframe.repositories.dataMaster.user.ConfigRateMaster;
import com.newframe.repositories.dataQuery.user.ConfigRateQuery;
import com.newframe.repositories.dataSlave.user.ConfigRateSlave;
import com.newframe.services.userbase.ConfigRateService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class ConfigRateServiceImpl implements ConfigRateService {

    @Autowired
    private ConfigRateMaster configRateMaster;
    @Autowired
    private ConfigRateSlave configRateSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    @Override
    public BigDecimal getRate() {
        ConfigRateQuery query = new ConfigRateQuery();
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable page = PageRequest.of(0, 1, sort);
        Page<ConfigRate> rates = configRateSlave.findAll(query, page);
        List<ConfigRate> content = rates.getContent();
        if (CollectionUtils.isNotEmpty(content)){
            return content.get(0).getRate();
        }
        return insert(BigDecimal.valueOf(0.15)).getRate();
    }

    @Override
    public ConfigRate insert(BigDecimal rate) {
        ConfigRate configRate = new ConfigRate();
        configRate.setId(idGlobalGenerator.getSeqId(ConfigRate.class));
        configRate.setRate(rate);
        return configRateMaster.save(configRate);
    }
}
