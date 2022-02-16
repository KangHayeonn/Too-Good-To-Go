package com.toogoodtogo.domain.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayProductRepository extends JpaRepository<DisplayProduct, Long> {
    DisplayProduct findByShopId(Long shopId);
    void deleteByShopId(Long shopId);
}
