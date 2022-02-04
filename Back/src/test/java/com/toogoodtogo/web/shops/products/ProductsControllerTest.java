package com.toogoodtogo.web.shops.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.AmazonS3MockConfig;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.DisplayProduct;
import com.toogoodtogo.domain.shop.product.DisplayProductRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductPriorityRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@Import(AmazonS3MockConfig.class)
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
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private DisplayProductRepository displayProductRepository;

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
        displayProductRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();

        manager = userRepository.save(User.builder()
                .email("productTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("productTest@email.com").password("password").build());

        Shop shop = Shop.builder()
                .user(manager).name("shop1").image("test1").category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop);
        shopId = shop.getId();

        Product product1 = Product.builder()
                .shop(shop).name("김치찌개").price(10000L).discountedPrice(9000L).image("test1").build();
        Product product2 = Product.builder()
                .shop(shop).name("된장찌개").price(11000L).discountedPrice(10000L).image("test2").build();
        Product product3 = Product.builder()
                .shop(shop).name("마라탕").price(12000L).discountedPrice(11000L).image("test3").build();
        Product product4 = Product.builder()
                .shop(shop).name("짜글이").price(13000L).discountedPrice(12000L).image("test4").build();
        Product product5 = Product.builder()
                .shop(shop).name("오뎅탕").price(14000L).discountedPrice(13000L).image("test5").build();
        Product save = productRepository.save(product1);
        Product save1 = productRepository.save(product2);
        Product save2 = productRepository.save(product3);
        Product save3 = productRepository.save(product4);
        Product save4 = productRepository.save(product5);
        productId = product1.getId();

        displayProductRepository.save(DisplayProduct.builder()
                .shop(shop).priority(new ArrayList<String>(Arrays.asList(
                        String.valueOf(save.getId()), String.valueOf(save1.getId()),
                        String.valueOf(save2.getId()), String.valueOf(save3.getId()), String.valueOf(save4.getId())))).build());
//        displayProductRepository.save(DisplayProduct.builder()
//                .shop(shop).priority(tmp).build());
    }

    @AfterEach
    public void setDown() {
        signService.logout(manager.getId());
        productRepository.deleteAllInBatch();
    }

    @Test
    void findAllProducts() throws Exception {
        //then
        mockMvc.perform(get("/api/products/all", shopId))
                .andDo(print())
                .andDo(document("products/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.[].shopId").description("product shop id"),
                                fieldWithPath("data.[].shopName").description("product shop name"),
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
    void findProducts() throws Exception {
        //then
        mockMvc.perform(get("/api/shops/{shopId}/products/all", shopId))
                .andDo(print())
                .andDo(document("products/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.[].shopId").description("product shop id"),
                                fieldWithPath("data.[].shopName").description("product shop name"),
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
                .build());
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", object.getBytes());

        //when
//        ResultActions actions = mockMvc.perform(post("/api/manager/shop/{shopId}/product", shopId)
//                .header("Authorization", "Bearer " + token.getAccessToken())
//                .content(object)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions = mockMvc.perform(multipart("/api/manager/shops/{shopId}/products", shopId)
                .file(new MockMultipartFile("file", null, null, (InputStream) null))
//                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\박수호\\Desktop\\test.png")))
                .file(request).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("products/add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.shopId").description("product shop id"),
                                fieldWithPath("data.shopName").description("product shop name"),
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
    public void updateProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductRequest.builder()
                .name("북어국")
                .price(8000L)
                .discountedPrice(7000L)
                .build());
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", object.getBytes());

        //when
//        ResultActions actions = mockMvc.perform(patch("/api/manager/shop/{shopId}/product/{productId}", shopId, productId)
//                .header("Authorization", "Bearer " + token.getAccessToken())
//                .content(object)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions = mockMvc.perform(multipart("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .file(new MockMultipartFile("file", null, null, (InputStream) null))
//                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\박수호\\Desktop\\test.png")))
                .file(request).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("products/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.shopId").description("product shop id"),
                                fieldWithPath("data.shopName").description("product shop name"),
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
                .andExpect(jsonPath("$.data.discountedPrice").value(7000L));
    }

    @Test
    public void updatePriorityProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductPriorityRequest.builder()
                .productsId(new ArrayList<>(Arrays.asList("4", "2", "1", "3", "5")))
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("products/updatePriority",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        //then
        mockMvc.perform(delete("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("products/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("success message")
                        )
                ))
                .andExpect(status().isOk());
    }

//    @Test
//    void recommendProducts() throws Exception {
//        //then
//        mockMvc.perform(get("/api/products/recommend"))
//                .andDo(print())
//                .andDo(document("products/recommend",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("data").description("product data"),
//                                fieldWithPath("data.[].shopId").description("product shop id"),
//                                fieldWithPath("data.[].shopName").description("product shop name"),
//                                fieldWithPath("data.[].id").description("product id"),
//                                fieldWithPath("data.[].name").description("product name"),
//                                fieldWithPath("data.[].price").description("product image"),
//                                fieldWithPath("data.[].discountedPrice").description("shop discountedPrice"),
//                                fieldWithPath("data.[].rate").description("shop rate"),
//                                fieldWithPath("data.[].image").description("product image")
//                        )
//                ))
//                .andExpect(status().isOk());
//    }

    @Test
    void productsPerCategory() throws Exception {
        String category = "한식";
        String method = "/rate";
        //then
        mockMvc.perform(get("/api/category/{category}/products", category, method))
                .andDo(print())
                .andDo(document("products/category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.[].shopId").description("product shop id"),
                                fieldWithPath("data.[].shopName").description("product shop name"),
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
    void findProductsPerShopSortByCategory() throws Exception {
        //then
        mockMvc.perform(get("/api/shops/{shopId}/products", shopId))
                .andDo(print())
                .andDo(document("products/shop",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("product data"),
                                fieldWithPath("data.[].shopId").description("product shop id"),
                                fieldWithPath("data.[].shopName").description("product shop name"),
                                fieldWithPath("data.[].id").description("product id"),
                                fieldWithPath("data.[].name").description("product name"),
                                fieldWithPath("data.[].price").description("product image"),
                                fieldWithPath("data.[].discountedPrice").description("shop discountedPrice"),
                                fieldWithPath("data.[].image").description("product image")
                        )
                ))
                .andExpect(status().isOk());
    }
}