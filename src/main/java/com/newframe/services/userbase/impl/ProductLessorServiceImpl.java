package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.ProductLessor;
import com.newframe.repositories.dataMaster.user.ProductLessorMaster;
import com.newframe.repositories.dataQuery.user.ProductLessorQuery;
import com.newframe.repositories.dataSlave.user.ProductLessorSlave;
import com.newframe.services.userbase.ProductLessorService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangBin
 */
@Service
public class ProductLessorServiceImpl implements ProductLessorService {

    @Autowired
    private ProductLessorMaster productLessorMaster;
    @Autowired
    private ProductLessorSlave productLessorSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    @Override
    public ProductLessor insert(ProductLessor productLessor) {
        if(productLessor == null){
            return null;
        }
        productLessor.setId(idGlobalGenerator.getSeqId(ProductLessor.class));
        return productLessorMaster.save(productLessor);
    }

    @Override
    public int modify(ProductLessor productLessor) {
        if (productLessor == null || productLessor.getId() == null){
            return 0;
        }
        ProductLessorQuery query = new ProductLessorQuery();
        query.setId(productLessor.getId());
        query.setUid(productLessor.getSupplierId());
        List<String> updateFields = Lists.newArrayList();
        if (StringUtils.isNotEmpty(productLessor.getBrand())){
            updateFields.add("brand");
        }
        if(StringUtils.isNotEmpty(productLessor.getColor())){
            updateFields.add("color");
        }
        if(StringUtils.isNotEmpty(productLessor.getModel())){
            updateFields.add("model");
        }
        if(StringUtils.isNotEmpty(productLessor.getSpecification())){
            updateFields.add("specification");
        }
        if(productLessor.getGuidePrice() != null){
            updateFields.add("guidePrice");
        }
        if(productLessor.getSupplyPrice() != null){
            updateFields.add("supplyPrice");
        }
        if(productLessor.getSurplusStock() != null){
            updateFields.add("surplusStock");
        }
        if(productLessor.getBrokenScreenRisks() != null){
            updateFields.add("brokenScreenRisks");
        }
        String[] array =new String[updateFields.size()];
        updateFields.toArray(array);
        return productLessorMaster.update(productLessor, query, array);
    }

    @Override
    public Page<ProductLessor> findAll(Long uid, PageSearchDTO pageSearchDTO) {
        ProductLessorQuery query = new ProductLessorQuery();
        query.setUid(uid);
        Pageable pageable = PageRequest.of(pageSearchDTO.getCurrentPage() - 1, pageSearchDTO.getPageSize());
        return productLessorSlave.findAll(query, pageable);
    }

    @Override
    public int revoke(Long uid, Long productId) {
        ProductLessorQuery query = new ProductLessorQuery();
        query.setUid(uid);
        query.setId(productId);
        ProductLessor productLessor = new ProductLessor();
        productLessor.setIsDelated(1);
        return productLessorMaster.update(productLessor, query, "isDelated");
    }
}
