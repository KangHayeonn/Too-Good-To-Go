package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductPriorityRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductsControllerTest extends ControllerTest {
    private static Long shopId;
    private static Long productId;
    private static User manager;
    private TokenDto token;

    @BeforeEach
    public void setUp() {
        choiceProductRepository.deleteAllInBatch();
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
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(product1).build());
    }

    @AfterEach
    public void setDown() {
        signService.logout(manager.getId());
        choiceProductRepository.deleteAllInBatch();
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
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
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
    void findProducts() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/shops/{shopId}/products/all", shopId))
                .andDo(print())
                .andDo(document("products/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
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
    void addProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(AddProductRequest.builder()
                .name("food")
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

        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .fileUpload("/api/manager/shops/{shopId}/products", shopId)
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
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data.shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.shopName").description("상품 가게 이름"),
                                fieldWithPath("data.id").description("상품 고유 번호"),
                                fieldWithPath("data.name").description("상품 이름"),
                                fieldWithPath("data.price").description("상품 가격"),
                                fieldWithPath("data.discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.image").description("상품 이미지")
                        )
                ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("food"));
    }

    @Test
    public void updateProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductRequest.builder()
                .name("chicken")
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

        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .fileUpload("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
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
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호"),
                                parameterWithName("productId").description("상품 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data.shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.shopName").description("상품 가게 이름"),
                                fieldWithPath("data.id").description("상품 고유 번호"),
                                fieldWithPath("data.name").description("상품 이름"),
                                fieldWithPath("data.price").description("상품 가격"),
                                fieldWithPath("data.discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.image").description("상품 이미지")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("chicken"))
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
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .patch("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
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
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호"),
                                parameterWithName("productId").description("상품 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data").description("가게의 상품 우선순위")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("products/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호"),
                                parameterWithName("productId").description("상품 고유 번호")
                        )
                ))
                .andExpect(status().isNoContent());
    }

    @Test
    public void choiceProduct() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("products/choice",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호"),
                                parameterWithName("productId").description("상품 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data.shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.shopName").description("상품 가게 이름"),
                                fieldWithPath("data.id").description("상품 고유 번호"),
                                fieldWithPath("data.name").description("상품 이름"),
                                fieldWithPath("data.price").description("상품 가격"),
                                fieldWithPath("data.discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.image").description("상품 이미지")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    void recommendProducts() throws Exception {
        //then
        mockMvc.perform(get("/api/products/recommend"))
                .andDo(print())
                .andDo(document("products/recommend",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
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
    void productsPerCategory() throws Exception {
        String category = "한식";
        String method = "/rate";
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/category/{category}/products", category, method))
                .andDo(print())
                .andDo(document("products/category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("category").description("가게 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
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
    void findProductsPerShopSortByCategory() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/shops/{shopId}/products", shopId))
                .andDo(print())
                .andDo(document("products/shop",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
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
    void findProductsBySearch() throws Exception {
        String keyword = "김치";
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/search/products/{keyword}", keyword))
                .andDo(print())
                .andDo(document("products/search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("keyword").description("검색 키워드")
                        ),
                        responseFields(
                                fieldWithPath("data.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.[].shopName").description("상품 가게 이름"),
                                fieldWithPath("data.[].id").description("상품 고유 번호"),
                                fieldWithPath("data.[].name").description("상품 이름"),
                                fieldWithPath("data.[].price").description("상품 가격"),
                                fieldWithPath("data.[].discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.[].image").description("상품 이미지")
                        )
                ))
                .andExpect(status().isOk());
    }
}