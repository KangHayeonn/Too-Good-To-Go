package com.toogoodtogo.web;

import com.toogoodtogo.application.search.SearchService;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;

    //TODO : 검색어 저장 기능? search:keyword ?
    //DONE : 검색어 Shop 카테고리에도 매칭 --> 카테고리는 정확히 일치해야 가능, 구현 방식 저게 맞는지..
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> searchProductsByShop(@CurrentUser User user, @RequestParam String keyword) {
        return new ApiResponse<>(searchService.searchProductsByShop(user != null ? user.getId() : null, keyword));
    }

    @GetMapping("/search/keywords")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<String>> recentKeywords(@CurrentUser User user) {
        return new ApiResponse<>(searchService.recentKeywords(user.getId()));
    }

    @DeleteMapping("/search/keywords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecentKeyword(@CurrentUser User user, @RequestParam String keyword) {
        searchService.deleteRecentKeyword(user.getId(), keyword);
    }

    @DeleteMapping("/search/keywords/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecentKeywordAll(@CurrentUser User user) {
        searchService.deleteRecentKeywordAll(user.getId());
    }
}
