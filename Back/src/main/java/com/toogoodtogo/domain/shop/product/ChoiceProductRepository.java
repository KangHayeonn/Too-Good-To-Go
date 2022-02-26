package com.toogoodtogo.domain.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceProductRepository  extends JpaRepository<ChoiceProduct, Long> {
    void deleteByProductId(Long productId);
    void deleteByShopId(Long shopId);
    ChoiceProduct findByShopId(Long shopId);
    ChoiceProduct findByShopIdAndProductId(Long shopId, Long productId);
    void deleteByShopIdAndProductId(Long shopId, Long productId);
}
