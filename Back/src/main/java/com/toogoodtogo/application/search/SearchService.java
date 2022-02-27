package com.toogoodtogo.application.search;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.ProductSearchDto;
import com.toogoodtogo.web.shops.products.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final StringRedisTemplate redisTemplate;
    private final ShopUseCase shopUseCase;
    private final ShopRepository shopRepository;
    private final ChoiceProductRepository choiceProductRepository;
    private final ProductRepositorySupport productRepositorySupport;
    private final ProductRepository productRepository;
    private final JdbcTemplateProductRepository jdbcTemplateProductRepository;

    private String REDIS_KEY = "recentKeywords:";

    public SearchResult searchProductsByShop(Long userId, String keyword, Pageable pageable) {
        if(userId != null) { // User 면 최근 검색어 저장
            ZSetOperations<String, String> redisRecentSearch = redisTemplate.opsForZSet();
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")); // timestamp로!!
            redisRecentSearch.add(REDIS_KEY + userId, keyword, Double.parseDouble(time));
            redisRecentSearch.removeRange(REDIS_KEY + userId, -(10 + 1), -(10 + 1));
        }
        return SearchResult.builder()
                .products(jdbcTemplateProductRepository.searchProductsByShopCategoryOrShopName(keyword, pageable))
                .totalNum(jdbcTemplateProductRepository.searchProductsCountByShopCategoryOrShopName(keyword)).build();
    }

    public List<String> recentKeywords(Long userId) {
        return new ArrayList<>(Objects.requireNonNull(
                redisTemplate.opsForZSet().reverseRange(REDIS_KEY + userId, 0, -1)));
    }

    public void deleteRecentKeyword(Long userId, String keyword) {
        redisTemplate.opsForZSet().remove(REDIS_KEY + userId, keyword);
    }

    public void deleteRecentKeywordAll(Long userId) {
        redisTemplate.delete(REDIS_KEY + userId);
    }
}
