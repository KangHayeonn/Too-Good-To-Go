package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.domain.shop.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopsController.class)
class ShopsControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ShopUseCase shopUseCase;

    @Test
    @DisplayName("가게 목록을 조회할 수 있다")
    void findShops() throws Exception {
        List<Shop> shops = new ArrayList<>();
        shops.add(Shop.builder().name("shop1").build());
        shops.add(Shop.builder().name("shop2").build());
        given(shopUseCase.findAllShops()).willReturn(shops);

        mvc.perform(get("/api/shops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name")
                        .value("shop1"))
                .andExpect(jsonPath("$.data[1].name")
                        .value("shop2"));
    }
}
