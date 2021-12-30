package com.toogoodtogo.domain.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByShopId(Long ShopId);
    List<Product> findAll();
}
