package com.newframe.services.userbase;

import com.newframe.entity.user.Area;

import java.util.List;

/**
 *
 *  地址 服务
 *
 * This class corresponds to the database table area
 *
 * @mbggenerated do_not_delete_during_merge
 */
public interface AreaService {

    /**
     * 查询省信息和省信息
     * @param
     * @return
     */
    List<Area> findAreaProvinceList();

    /**
     * 根据省的areaCode查询市和区信息
     *
     * @param areaCode
     * @return
     */
    List<Area> findAreaCityAndCountylist(Integer areaCode);

    /**
     *  根据areacode找地址
     * @param areaCode
     * @return
     */
    List<Area> findAreaByAreaCode(List<Integer> areaCode);
}
