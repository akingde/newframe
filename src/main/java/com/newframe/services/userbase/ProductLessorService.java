package com.newframe.services.userbase;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.ProductLessor;
import com.newframe.entity.user.ProductSupplier;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author WangBin
 */
public interface ProductLessorService {

    ProductLessor insert(ProductLessor productLessor);

    int modify(ProductLessor productLessor);

    Page<ProductLessor> findAll(Long uid, PageSearchDTO pageSearchDTO);

    int revoke(Long uid, Long productId);
}
