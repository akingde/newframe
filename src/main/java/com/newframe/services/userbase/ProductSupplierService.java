package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.ProductSupplier;
import org.springframework.data.domain.Page;

/**
 * @author WangBin
 */
public interface ProductSupplierService {

    ProductSupplier insert(ProductSupplier productSupplier);

    int modify(ProductSupplier productSupplier);

    Page<ProductSupplier> findAll(Long uid, PageSearchDTO pageSearchDTO);

    int revoke(Long uid, Long productId);
}
