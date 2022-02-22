package com.toogoodtogo.web;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.ChoiceProduct;
import com.toogoodtogo.domain.shop.product.HighestRateProduct;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.TokenRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchControllerTest extends ControllerTest {
    private User user;
    private TokenDto token;

    @BeforeEach
    public void setUp() {
        highestRateProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAllInBatch();

        user = userRepository.save(User.builder()
                .email("searchUser@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_USER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("searchUser@email.com").password("password").build());

        User manager = userRepository.save(User.builder()
                .email("searchManager@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());

        Shop shop = Shop.builder()
                .user(manager).name("shop1").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop);

        Product product1 = Product.builder()
                .shop(shop).name("김치찌개").price(10000L).discountedPrice(9000L)
                .image("https://diefqsnmvol80.cloudfront.net/productDefault.png").build();
        productRepository.save(product1);
        highestRateProductRepository.save(HighestRateProduct.builder().shop(shop).product(product1).build());
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(product1).build());

        searchService.searchProductsByShop(user.getId(), "shop");
    }

    @AfterEach
    public void setDown() {
        signService.logout(new TokenRequest(token.getAccessToken(), token.getRefreshToken()));
        highestRateProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAllInBatch();
    }

    @Test
    void searchProductsByShop() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/search?keyword=한식")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("search/products",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token")),
                        requestParameters(
                                parameterWithName("keyword").description("가게 이름, 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
                                fieldWithPath("data.[].shopCategory").description("상품 가게 카테고리"),
                                fieldWithPath("data.[].id").description("상품 고유 번호"),
                                fieldWithPath("data.[].name").description("상품 이름"),
                                fieldWithPath("data.[].price").description("상품 가격"),
                                fieldWithPath("data.[].discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.[].image").description("상품 이미지")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void recentKeywords() throws Exception {
        //then
        mockMvc.perform(get("/api/search/keywords")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("search/recentKeywords",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token")),
                        responseFields(
                                fieldWithPath("data").description("최근 검색어")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRecentKeyword() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/search/keywords?keyword=shop")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("search/deleteKeyword",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token")),
                        requestParameters(
                                parameterWithName("keyword").description("삭제 키워드")
                        )
                ))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecentKeywordAll() throws Exception {
        //then
        mockMvc.perform(delete("/api/search/keywords/all")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("search/deleteAllKeywords",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token"))
                ))
                .andExpect(status().isNoContent());
    }
}