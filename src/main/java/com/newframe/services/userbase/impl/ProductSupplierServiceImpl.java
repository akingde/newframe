package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.ProductSupplier;
import com.newframe.repositories.dataMaster.user.ProductSupplierMaster;
import com.newframe.repositories.dataQuery.user.ProductSupplierQuery;
import com.newframe.repositories.dataSlave.user.ProductSupplierSlave;
import com.newframe.services.userbase.ProductSupplierService;
import com.newframe.utils.BigDecimalUtils;
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
public class ProductSupplierServiceImpl implements ProductSupplierService {

    @Autowired
    private ProductSupplierMaster productSupplierMaster;
    @Autowired
    private ProductSupplierSlave productSupplierSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    @Override
    public ProductSupplier insert(ProductSupplier productSupplier) {
        if(productSupplier == null){
            return null;
        }
        productSupplier.setId(idGlobalGenerator.getSeqId(ProductSupplier.class));
        return productSupplierMaster.save(productSupplier);
    }

    @Override
    public int modify(ProductSupplier productSupplier) {
        if (productSupplier == null || productSupplier.getId() == null){
            return 0;
        }
        ProductSupplierQuery query = new ProductSupplierQuery();
        query.setId(productSupplier.getId());
        query.setUid(productSupplier.getSupplierId());
        List<String> updateFields = Lists.newArrayList();
        if (StringUtils.isNotEmpty(productSupplier.getBrand())){
            updateFields.add("brand");
        }
        if(StringUtils.isNotEmpty(productSupplier.getColor())){
            updateFields.add("color");
        }
        if(StringUtils.isNotEmpty(productSupplier.getModel())){
            updateFields.add("model");
        }
        if(StringUtils.isNotEmpty(productSupplier.getSpecification())){
            updateFields.add("specification");
        }
        if(BigDecimalUtils.compareTo(productSupplier.getGuidePrice())){
            updateFields.add("guidePrice");
        }
        if(BigDecimalUtils.compareTo(productSupplier.getSupplyPrice())){
            updateFields.add("supplyPrice");
        }
        if(productSupplier.getSurplusStock() != null && productSupplier.getSurplusStock() > 0){
            updateFields.add("surplusStock");
        }
        String[] array =new String[updateFields.size()];
        updateFields.toArray(array);
        return productSupplierMaster.update(productSupplier, query, array);
    }

    @Override
    public Page<ProductSupplier> findAll(Long uid, PageSearchDTO pageSearchDTO) {
        ProductSupplierQuery query = new ProductSupplierQuery();
        query.setUid(uid);
        query.setIsDelated(0);
        Pageable pageable = PageRequest.of(pageSearchDTO.getCurrentPage() - 1, pageSearchDTO.getPageSize());
        return productSupplierSlave.findAll(query, pageable);
    }

    @Override
    public int revoke(Long uid, Long productId) {
        ProductSupplierQuery query = new ProductSupplierQuery();
        query.setUid(uid);
        query.setId(productId);
        ProductSupplier productLessor = new ProductSupplier();
        productLessor.setIsDelated(1);
        return productSupplierMaster.update(productLessor, query, "isDelated");
    }
}
