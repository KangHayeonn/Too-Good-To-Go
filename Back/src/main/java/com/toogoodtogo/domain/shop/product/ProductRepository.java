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

    @Query("select p from Product p where p.shop.user.id = ?1 and p.id = ?2")
    Optional<Product> findByUserIdAndId(Long memberId, Long productId);

    @Modifying
    @Query("delete from Product p where p.shop.id = ?1")
    void deleteByShopId(Long shopId);
}
