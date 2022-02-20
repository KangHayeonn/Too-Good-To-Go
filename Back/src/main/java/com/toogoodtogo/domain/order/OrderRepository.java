package com.toogoodtogo.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT distinct o FROM Order o " +
            "join fetch o.shop " +
            "join fetch o.user " +
            "join fetch o.orderProducts op " +
            "join fetch op.product " +
            "where o.user.id = :userId " +
            "order by o.id desc")
    List<Order> findAllByUserId(Long userId);

    Optional<Order> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT distinct o FROM Order o " +
            "join fetch o.shop s " +
            "join fetch o.user " +
            "join fetch o.orderProducts op " +
            "join fetch op.product " +
            "where s.id = :shopId " +
            "order by o.id desc")
    List<Order> findAllByShopId(Long shopId);
}
