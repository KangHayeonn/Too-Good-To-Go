package com.toogoodtogo.domain.shop;

import com.toogoodtogo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByUser(User user);
    Optional<Shop> findByUserIdAndId(Long memberId, Long shopId);
    Optional<Shop> findByAddressAndName(String address, String name);
    List<Shop> findByNameContaining(String keyword);
}
