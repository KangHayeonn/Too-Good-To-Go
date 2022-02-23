package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByShopId(Long shopId);
    Optional<Product> findByShopIdAndId(Long shopId, Long productId);
    Optional<Product> findByShopIdAndName(Long shopId, String name);
    void deleteByShopId(Long shopId);
    Long countByShopId(Long shopId);
}
