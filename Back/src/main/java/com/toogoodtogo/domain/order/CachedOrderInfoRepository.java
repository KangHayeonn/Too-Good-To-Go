package com.toogoodtogo.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CachedOrderInfoRepository extends JpaRepository<CachedOrderInfo, Long> {

    Optional<CachedOrderInfo> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
