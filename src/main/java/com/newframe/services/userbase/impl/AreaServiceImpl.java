package com.newframe.services.userbase.impl;

import com.newframe.entity.user.Area;
import com.newframe.repositories.dataQuery.user.AreaQuery;
import com.newframe.repositories.dataSlave.user.AreaSlave;
import com.newframe.services.userbase.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址 服务
 * <p>
 * This class corresponds to the database table area
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaSlave areaSlave;

    /**
     * 查询省信息
     *
     * @param
     * @return
     */
    @Override
    public List<Area> findAreaProvinceList() {
        AreaQuery query = new AreaQuery();
        query.setAreaLevel(1);
        return areaSlave.findAll(query);
    }

    /**
     * 根据省的areaCode查询市和区信息
     *
     * @param areaCode
     * @return
     */
    @Override
    public List<Area> findAreaCityAndCountylist(Integer areaCode) {
        AreaQuery query = new AreaQuery();
        query.setMinAreaCode(areaCode);
        query.setMaxAreaCode(areaCode + 10000);
        return areaSlave.findAll(query);
    }

    /**
     * 根据areacode找地址
     *
     * @param areaCode
     * @return
     */
    @Override
    public List<Area> findAreaByAreaCode(List<Integer> areaCode) {
        AreaQuery query = new AreaQuery();
        query.setAreaCode(areaCode);
        return areaSlave.findAll(query);
    }
}
