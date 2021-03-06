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
import org.springframework.data.domain.PageRequest;
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
                .category(Arrays.asList("??????", "??????")).build();
        shopRepository.save(shop);

        Product product1 = Product.builder()
                .shop(shop).name("????????????").price(10000L).discountedPrice(9000L)
                .image("https://diefqsnmvol80.cloudfront.net/productDefault.png").build();
        productRepository.save(product1);
        highestRateProductRepository.save(HighestRateProduct.builder().shop(shop).product(product1).build());
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(product1).build());

        searchService.searchProductsByShop(user.getId(), "shop", PageRequest.of(0, 10));
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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/search?keyword=??????&page=0")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("search/products",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("????????? Access Token")),
                        requestParameters(
                                parameterWithName("keyword").description("?????? ??????, ????????????"),
                                parameterWithName("page").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.products.[].shopId").description("?????? ?????? ?????? ??????"),
                                fieldWithPath("data.products.[].shopName").description("?????? ?????? ??????"),
                                fieldWithPath("data.products.[].shopCategory").description("?????? ?????? ????????????"),
                                fieldWithPath("data.products.[].id").description("?????? ?????? ??????"),
                                fieldWithPath("data.products.[].name").description("?????? ??????"),
                                fieldWithPath("data.products.[].price").description("?????? ??????"),
                                fieldWithPath("data.products.[].discountedPrice").description("?????? ????????????"),
                                fieldWithPath("data.products.[].image").description("?????? ?????????"),
                                fieldWithPath("data.totalNum").description("?????? ??????")
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
                        requestHeaders(headerWithName("Authorization").description("????????? Access Token")),
                        responseFields(
                                fieldWithPath("data").description("?????? ?????????")
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
                        requestHeaders(headerWithName("Authorization").description("????????? Access Token")),
                        requestParameters(
                                parameterWithName("keyword").description("?????? ?????????")
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
                        requestHeaders(headerWithName("Authorization").description("????????? Access Token"))
                ))
                .andExpect(status().isNoContent());
    }
}