package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.domain.shop.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(ShopsController.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ShopsControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ShopUseCase shopUseCase;

    @Test
    @DisplayName("가게 목록을 조회할 수 있다")
    void findShops() throws Exception {
        List<Shop> shops = new ArrayList<>();
        shops.add(Shop.builder().name("shop1").image("test1").category("한식").build());
        shops.add(Shop.builder().name("shop2").image("test2").category("중식").build());
        given(shopUseCase.findAllShops()).willReturn(shops);

        mvc.perform(get("/api/shops")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("shops/find",
                        responseFields(
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.[].id").description("shop id"),
                                fieldWithPath("data.[].name").description("shop name"),
                                fieldWithPath("data.[].image").description("shop image"),
                                fieldWithPath("data.[].category").description("shop category")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name")
                        .value("shop1"))
                .andExpect(jsonPath("$.data[1].name")
                        .value("shop2"));
    }
}