package com.toogoodtogo.domain.shop;

import com.toogoodtogo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    //List<Shop> findByUserid(Long userId);
    List<Shop> findByUser(User user); //findAll??

    @Query("select s from Shop s where s.user.id = ?1 and s.id = ?2")
    Optional<Shop> findByUserIdAndId(Long memberId, Long shopId);

    @Query("select s from Shop s where s.address = ?1 and s.name = ?2")
    Optional<Shop> findByAddressAndName(String address, String name);

    List<Shop> findByNameContaining(String keyword);
}
