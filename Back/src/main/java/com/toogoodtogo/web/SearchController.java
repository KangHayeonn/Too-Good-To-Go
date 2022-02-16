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

    //TODO : 비회원, 회원 검색
    //TODO : 최근 검색어 로그아웃하면 지워짐??
    //TODO : 잘못된 단어 검색 시 비슷한 단어 매칭 검색....음....
    //TODO : 검색어 저장 기능? search:keyword ?
    //DONE : 검색어 Shop 카테고리에도 매칭 --> 카테고리는 정확히 일치해야 가능, 구현 방식 저게 맞는지..
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> searchProductsByShop(@CurrentUser User user, @RequestParam String keyword) {
        return new ApiResponse<>(searchService.searchProductsByShop(user.getId(), keyword));
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

    @DeleteMapping("/search/keywordsAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecentKeywordAll(@CurrentUser User user) {
        searchService.deleteRecentKeywordAll(user.getId());
    }
}
