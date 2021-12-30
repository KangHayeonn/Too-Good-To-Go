package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ProductsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private static Long shopId;

    @BeforeEach
    public void setUp() {
        Shop shop = Shop.builder().name("shop1").image("test1").category("한식").build();
        shopRepository.save(shop);
        shopId = shop.getId();
        productRepository.save(Product.builder().shop(shop).name("김치찌개").price(6000L).discountedPrice(5000L).image("test1").build());
        productRepository.save(Product.builder().shop(shop).name("된장찌개").price(7000L).discountedPrice(6000L).image("test2").build());
    }

    @AfterEach
    public void setDown() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void findProducts() throws Exception {
        //then
        mockMvc.perform(get("/api/shops/{shopId}/products", shopId))
                .andDo(print())
                .andDo(document("products/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.[].id").description("product id"),
                                fieldWithPath("data.[].name").description("product name"),
                                fieldWithPath("data.[].price").description("product image"),
                                fieldWithPath("data.[].discountedPrice").description("shop discountedPrice"),
                                fieldWithPath("data.[].image").description("product image")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("김치찌개"))
                .andExpect(jsonPath("$.data[1].name").value("된장찌개"));
    }

}