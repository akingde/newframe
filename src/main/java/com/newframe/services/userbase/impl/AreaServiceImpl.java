package com.newframe.services.userbase.impl;

import com.newframe.entity.user.Area;
import com.newframe.services.userbase.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *  地址 服务
 *
 * This class corresponds to the database table area
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class AreaServiceImpl implements AreaService {

    /**
     * 查询省信息和省下面的市和区信息
     *
     * @param
     * @return
     */
    @Override
    public List<Area> findAreaProvinceList() {
        return null;
    }

    /**
     * 查询市和区信息
     *
     * @param area
     * @return
     */
    @Override
    public List<Area> findAreaCityAndCountylist(Area area) {
        return null;
    }

    /**
     * 根据areacode找地址
     *
     * @param areaCode
     * @return
     */
    @Override
    public List<Area> findAreaByAreaCode(List<Integer> areaCode) {
        return null;
    }
}
