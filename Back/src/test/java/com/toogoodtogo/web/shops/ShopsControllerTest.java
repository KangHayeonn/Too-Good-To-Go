package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.response.ResponseService;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.application.shop.product.ProductService;
import com.toogoodtogo.application.user.UserService;
import com.toogoodtogo.configuration.security.CustomAccessDeniedHandler;
import com.toogoodtogo.configuration.security.CustomAuthenticationEntryPoint;
import com.toogoodtogo.configuration.security.JwtTokenProvider;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
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
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ShopsControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    private ShopRepository shopRepository;

    @BeforeEach
    public void setUp() {
        shopRepository.save(Shop.builder().name("shop1").image("test1").category("한식").build());
        shopRepository.save(Shop.builder().name("shop2").image("test2").category("중식").build());
    }

    @AfterEach
    public void setDown() {
        shopRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void findShops() throws Exception {
        //then
        mvc.perform(get("/api/common/shops"))
                .andDo(print())
                .andDo(document("shops/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").description("shop data"),
                                fieldWithPath("code").description("shop data"),
                                fieldWithPath("msg").description("shop data"),
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.[].id").description("shop id"),
                                fieldWithPath("data.[].name").description("shop name"),
                                fieldWithPath("data.[].image").description("shop image"),
                                fieldWithPath("data.[].category").description("shop category")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data[0].name").value("shop1"))
                .andExpect(jsonPath("$.data[1].name").value("shop2"));
    }
}