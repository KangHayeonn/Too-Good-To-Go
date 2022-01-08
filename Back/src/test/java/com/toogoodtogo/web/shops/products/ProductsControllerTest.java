package com.toogoodtogo.web.shops.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.users.sign.TokenDto;
import com.toogoodtogo.web.users.sign.UserLoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignService signService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private static Long shopId;

    @Autowired
    private static Long productId;

    @Autowired
    private static User manager;

    private TokenDto token;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        manager = userRepository.save(User.builder()
                .email("productTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("010-0000-0000")
                .role("ROLE_MANAGER")
                .build());

        token = signService.login(UserLoginRequest.builder().email("productTest@email.com").password("password").build());

        Shop shop = Shop.builder().user(manager).name("shop1").image("test1").category(new String[]{"한식"}).build();
        shopRepository.save(shop);
        shopId = shop.getId();

        Product product1 = Product.builder().shop(shop).name("김치찌개").price(6000L).discountedPrice(5000L).image("test1").build();
        Product product2 = Product.builder().shop(shop).name("된장찌개").price(7000L).discountedPrice(6000L).image("test2").build();
        productRepository.save(product1);
        productRepository.save(product2);
        productId = product1.getId();
    }

    @AfterEach
    public void setDown() {
        productRepository.deleteAllInBatch();
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
                .andExpect(status().isOk());
    }

    @Test
    void addProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(AddProductRequest.builder()
                .name("미역국")
                .price(9000L)
                .discountedPrice(8000L)
                .image("image1")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/manager/shops/{shopId}/products", shopId)
                .header("Authorization", token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("products/add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.id").description("product id"),
                                fieldWithPath("data.name").description("product name"),
                                fieldWithPath("data.price").description("product price"),
                                fieldWithPath("data.discountedPrice").description("shop discountedPrice"),
                                fieldWithPath("data.image").description("product image")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("미역국"));
    }

    @Test
//    @WithMockUser(roles = "MANAGER")
    public void updateProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductRequest.builder()
                .name("북어국")
                .price(8000L)
                .discountedPrice(7000L)
                .image("new_image")
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/api/manager/shop/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("products/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.id").description("product id"),
                                fieldWithPath("data.name").description("product name"),
                                fieldWithPath("data.price").description("product price"),
                                fieldWithPath("data.discountedPrice").description("shop discountedPrice"),
                                fieldWithPath("data.image").description("product image")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("북어국"))
                .andExpect(jsonPath("$.data.price").value(8000L))
                .andExpect(jsonPath("$.data.discountedPrice").value(7000L))
                .andExpect(jsonPath("$.data.image").value("new_image"));
    }

    @Test
//    @WithMockUser(roles = "MANAGER")
    public void deleteProduct() throws Exception {
        //then
        mockMvc.perform(delete("/api/manager/shop/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", token.getAccessToken()))
                .andDo(print())
                .andDo(document("products/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data")
                        )
                ))
                .andExpect(status().isOk());
    }
}