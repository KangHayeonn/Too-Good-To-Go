package com.toogoodtogo.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT distinct o FROM Order o " +
            "join fetch o.shop " +
            "join fetch o.user " +
            "join fetch o.orderProducts op " +
            "join fetch op.product")
    List<Order> findAllByUserId(Long userId);
}
