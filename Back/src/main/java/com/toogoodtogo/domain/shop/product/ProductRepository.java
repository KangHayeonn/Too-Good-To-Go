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
    List<Product> findAllByShopId(Long ShopId);
    List<Product> findAll();

    @Query("select p from Product p where p.shop.id = ?1 and p.id = ?2") // 수정 해야 할듯? join
    Optional<Product> findByShopIdAndId(Long shopId, Long productId);

    @Query("select p from Product p where p.shop.id = ?1 and p.name = ?2") // 수정 해야 할듯?
    Optional<Product> findByShopIdAndName(Long shopId, String name);

    @Modifying
    @Query("delete from Product p where p.shop.id = ?1") // 수정 해야 할듯?
    void deleteByShopId(Long shopId);

    @Query("select count(p) from Product p where p.shop.id = ?1")
    Long countProductByShopId(Long shopId);
}
