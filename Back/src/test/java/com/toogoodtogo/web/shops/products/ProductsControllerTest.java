package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.shops.products.dto.AddChoiceProductRequest;
import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductPriorityRequest;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import com.toogoodtogo.web.users.sign.dto.TokenRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductsControllerTest extends ControllerTest {
    private static Long shopId;
    private static Long productId;
    private static String product1Id;
    private static String product2Id;
    private static String product3Id;
    private static String product4Id;
    private static String product5Id;
    private TokenDto token;

    @BeforeEach
    public void setUp() {
        highestRateProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        displayProductRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        User manager = userRepository.save(User.builder()
                .email("productTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("productTest@email.com").password("password").build());

        Shop shop = Shop.builder()
                .user(manager).name("shop1").image("https://diefqsnmvol80.cloudfront.net/test.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop);
        shopId = shop.getId();

        Product product1 = Product.builder()
                .shop(shop).name("김치찌개").price(10000L).discountedPrice(9000L)
                .image("https://diefqsnmvol80.cloudfront.net/test.png").build();
        Product product2 = Product.builder()
                .shop(shop).name("된장찌개").price(11000L).discountedPrice(10000L).image("test2").build();
        Product product3 = Product.builder()
                .shop(shop).name("마라탕").price(12000L).discountedPrice(11000L).image("test3").build();
        Product product4 = Product.builder()
                .shop(shop).name("짜글이").price(13000L).discountedPrice(12000L).image("test4").build();
        Product product5 = Product.builder()
                .shop(shop).name("오뎅탕").price(50000L).discountedPrice(1000L).image("test5").build();
        Product save = productRepository.save(product1);
        Product save1 = productRepository.save(product2);
        Product save2 = productRepository.save(product3);
        Product save3 = productRepository.save(product4);
        Product save4 = productRepository.save(product5);
        productId = product1.getId();
        product1Id = String.valueOf(product1.getId());
        product2Id = String.valueOf(product2.getId());
        product3Id = String.valueOf(product3.getId());
        product4Id = String.valueOf(product4.getId());
        product5Id = String.valueOf(product5.getId());

        displayProductRepository.save(DisplayProduct.builder()
                .shop(shop).priority(new ArrayList<String>(Arrays.asList(
                        String.valueOf(save.getId()), String.valueOf(save1.getId()),
                        String.valueOf(save2.getId()), String.valueOf(save3.getId()), String.valueOf(save4.getId())))).build());

        highestRateProductRepository.save(HighestRateProduct.builder()
                .shop(shop).product(productRepositorySupport.choiceHighestRateProductPerShop(shopId)).build());
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(product1).build());
    }

    @AfterEach
    public void setDown() {
        signService.logout(new TokenRequest(token.getAccessToken(), token.getRefreshToken()));
        highestRateProductRepository.deleteAllInBatch();
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
                        requestParts(
                                partWithName("file").description("상품 이미지"),
                                partWithName("request").description("상품 정보")
                        ),
                        requestPartFields("request",
                                fieldWithPath("name").description("상품 이름"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("discountedPrice").description("상품 할인가격")
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
    void updateProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductRequest.builder()
                .name("chicken").price(8000L).discountedPrice(7000L).build());
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/manager/shops/{shopId}/products/{productId}", shopId, productId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
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
                        requestFields(
                                fieldWithPath("name").description("상품 이름"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("discountedPrice").description("상품 할인가격")
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
    void deleteProduct() throws Exception {
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
    void updateProductImage() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                fileUpload("/api/manager/shops/{shopId}/products/{productId}/image", shopId, productId)
                .file(new MockMultipartFile("file", "test.png", "image/png", "test".getBytes()))
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("products/updateImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호"),
                                parameterWithName("productId").description("상품 고유 번호")
                        ),
                        requestParts(partWithName("file").description("상품 이미지")),
                        responseFields(
                                fieldWithPath("data").description("상품 이미지")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductImage() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/manager/shops/{shopId}/products/{productId}/image", shopId, productId)
                        .header("Authorization", "Bearer " + token.getAccessToken()));
        //then
        actions
                .andDo(print())
                .andDo(document("products/deleteImage",
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
    void updatePriorityProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateProductPriorityRequest.builder()
                .productsId(new ArrayList<>(Arrays.asList(product2Id, product3Id, product1Id, product5Id, product4Id)))
                .build());

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/manager/shops/{shopId}/products", shopId)
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
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        requestFields(
                                fieldWithPath("productsId").description("가게의 상품 우선순위")
                        ),
                        responseFields(
                                fieldWithPath("data").description("가게의 상품 우선순위")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void choiceProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(AddChoiceProductRequest.builder()
                .productId(productId).build());
        //then
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/manager/shops/{shopId}/choice", shopId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //when
        actions.andDo(print())
                .andDo(document("products/choice",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        requestFields(
                                fieldWithPath("productId").description("추천 상품 번호")
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
    void deleteChoiceProduct() throws Exception {
        //then
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .delete("/api/manager/shops/{shopId}/choice", shopId)
                .param("product", product1Id)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        actions.andDo(print())
                .andDo(document("products/deleteChoice",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
                        requestParameters(
                                parameterWithName("product").description("상품 고유 번호")
                        )
                ))
                .andExpect(status().isNoContent());
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
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/products?category=한식&method=rate&page=0"))
                .andDo(print())
                .andDo(document("products/category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("category").description("가게 카테고리"),
                                parameterWithName("method").description("정렬 방법"),
                                parameterWithName("page").description("페이지 숫자")
                        ),
                        responseFields(
                                fieldWithPath("data.products.[].shopId").description("상품 가게 고유 번호"),
                                fieldWithPath("data.products.[].shopName").description("상품 가게 이름"),
                                fieldWithPath("data.products.[].id").description("상품 고유 번호"),
                                fieldWithPath("data.products.[].name").description("상품 이름"),
                                fieldWithPath("data.products.[].price").description("상품 가격"),
                                fieldWithPath("data.products.[].discountedPrice").description("상품 할인가격"),
                                fieldWithPath("data.products.[].image").description("상품 이미지"),
                                fieldWithPath("data.totalNum").description("상품 개수")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void findProductsPerShopSortByPriority() throws Exception {
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
}