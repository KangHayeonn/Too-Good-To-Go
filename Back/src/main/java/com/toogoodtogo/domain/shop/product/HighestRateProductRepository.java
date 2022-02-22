package com.toogoodtogo.domain.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HighestRateProductRepository extends JpaRepository<HighestRateProduct, Long> {
    @Modifying(clearAutomatically = true)
    @Query("delete from HighestRateProduct p where p.shop.id = ?1")
    void deleteByShopId(Long shopId);
    HighestRateProduct findByShopId(Long shopId);
    HighestRateProduct findByShopIdAndProductId(Long shopId, Long productId);
}
