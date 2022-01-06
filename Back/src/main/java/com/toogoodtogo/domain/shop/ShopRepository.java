package com.toogoodtogo.domain.shop;

import com.toogoodtogo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    //List<Shop> findByUserid(Long userId);
    List<Shop> findByUser(User user);
}
