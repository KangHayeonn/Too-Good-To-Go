package com.toogoodtogo.domain.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoiceProductRepository  extends JpaRepository<ChoiceProduct, Long> {
    void deleteByProductId(Long productId);
    void deleteByShopId(Long shopId);
    ChoiceProduct findByShopId(Long shopId);
    @Query(value = "select o from ChoiceProduct o inner join o.shop shop where find_in_set(?1, shop.category) > 0")
    List<ChoiceProduct> findProductsByShopCategory(String category);
}
