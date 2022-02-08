package com.toogoodtogo.web.shops;

import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopsControllerTest extends ControllerTest {
    private static int managerId;
    private static int shopId;
    private User manager;
    private TokenDto token;

    @BeforeEach
    public void setUp() {
        displayProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();

        manager = userRepository.save(User.builder()
                .email("shopTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("shopTest@email.com").password("password").build());

        Shop shop1 = Shop.builder()
                .user(manager).name("shop1").image("test1").category(Arrays.asList("한식")).phone("01012345678")
                .address("서울특별시 양천구 목동 1번지").hours(new Hours("10:00", "22:00")).build();
        shopRepository.save(shop1);
        shopRepository.save(Shop.builder()
                .user(manager).name("shop2").image("test2").category(Arrays.asList("중식")).phone("01056789012")
                .address("서울특별시 양천구 목동 2번지").hours(new Hours("11:00", "23:00")).build());
        shopId = Math.toIntExact(shop1.getId());
    }

    @AfterEach
    public void setDown() {
        signService.logout(manager.getId());
        displayProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void findAllShops() throws Exception {
        //then
        mockMvc.perform(get("/api/shops"))
                .andDo(print())
                .andDo(document("shops/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.[].id").description("가게 고유번호"),
                                fieldWithPath("data.[].name").description("가게 이름"),
                                fieldWithPath("data.[].image").description("가게 이미지"),
                                fieldWithPath("data.[].category").description("가게 카테고리"),
                                fieldWithPath("data.[].phone").description("가게 전화번호"),
                                fieldWithPath("data.[].address").description("가게 주소"),
                                fieldWithPath("data.[].hours.open").description("가게 오픈시간"),
                                fieldWithPath("data.[].hours.close").description("가게 마감시간")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("shop1"))
                .andExpect(jsonPath("$.data[1].name").value("shop2"));
    }

    @Test
    void findShops() throws Exception {
        //then
        mockMvc.perform(get("/api/manager/shops")
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("shops/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.[].id").description("가게 고유번호"),
                                fieldWithPath("data.[].name").description("가게 이름"),
                                fieldWithPath("data.[].image").description("가게 이미지"),
                                fieldWithPath("data.[].category").description("가게 카테고리"),
                                fieldWithPath("data.[].phone").description("가게 전화번호"),
                                fieldWithPath("data.[].address").description("가게 주소"),
                                fieldWithPath("data.[].hours.open").description("가게 오픈시간"),
                                fieldWithPath("data.[].hours.close").description("가게 마감시간")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void addShop() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(AddShopRequest.builder()
                .name("shop4").category(new ArrayList<>(Arrays.asList("category"))).phone("01044444444")
                .address("address").open("10:00").close("22:00").build());
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", object.getBytes());

        //when
//        ResultActions actions = mockMvc.perform(post("/api/manager/shop")
//                .header("Authorization", "Bearer " + token.getAccessToken())
//                .content(object)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions = mockMvc.perform(multipart("/api/manager/shops")
                .file(new MockMultipartFile("file", null, null, (InputStream) null))
//                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\박수호\\Desktop\\test.png")))
                .file(request).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("shops/add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
//                        requestParts(partWithName("file").description("가게 이미지")),
//                        requestPartFields("request",
//                                fieldWithPath("name").description("가게 이름"),
//                                fieldWithPath("category").description("가게 카테고리"),
//                                fieldWithPath("phone").description("가게 전화번호"),
//                                fieldWithPath("address").description("가게 주소"),
//                                fieldWithPath("open").description("가게 오픈시간"),
//                                fieldWithPath("close").description("가게 마감시간")
//                        ),
                        responseFields(
                                fieldWithPath("data.id").description("가게 고유번호"),
                                fieldWithPath("data.name").description("가게 이름"),
                                fieldWithPath("data.image").description("가게 이미지"),
                                fieldWithPath("data.category").description("가게 카테고리"),
                                fieldWithPath("data.phone").description("가게 전화번호"),
                                fieldWithPath("data.address").description("가게 주소"),
                                fieldWithPath("data.hours.open").description("가게 오픈시간"),
                                fieldWithPath("data.hours.close").description("가게 마감시간")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateShop() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateShopRequest.builder()
                .name("shop3")
                .image("test3")
                .category(new ArrayList<>(Arrays.asList("category2")))
                .phone("01087654321")
                .address("test3")
                .open("12:00")
                .close("21:00")
                .build());
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", object.getBytes());

        //when
//        ResultActions actions = mockMvc.perform(patch("/api/manager/shop/{shopId}", shopId)
//                .header("Authorization", "Bearer " + token.getAccessToken())
//                .content(object)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .fileUpload("/api/manager/shops/{shopId}", shopId)
                .file(new MockMultipartFile("file", null, null, (InputStream) null))
//                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\박수호\\Desktop\\test.png")))
                .file(request).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("shops/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        ),
//                        requestParts(partWithName("file").description("가게 이미지")),
//                        requestPartFields("request",
//                                fieldWithPath("name").description("가게 이름"),
//                                fieldWithPath("category").description("가게 카테고리"),
//                                fieldWithPath("phone").description("가게 전화번호"),
//                                fieldWithPath("address").description("가게 주소"),
//                                fieldWithPath("open").description("가게 오픈시간"),
//                                fieldWithPath("close").description("가게 마감시간")
//                        ),
                        responseFields(
                                fieldWithPath("data.id").description("가게 고유번호"),
                                fieldWithPath("data.name").description("가게 이름"),
                                fieldWithPath("data.image").description("가게 이미지"),
                                fieldWithPath("data.category").description("가게 카테고리"),
                                fieldWithPath("data.phone").description("가게 전화번호"),
                                fieldWithPath("data.address").description("가게 주소"),
                                fieldWithPath("data.hours.open").description("가게 오픈시간"),
                                fieldWithPath("data.hours.close").description("가게 마감시간")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShop() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/manager/shops/{shopId}", shopId)
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("shops/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("가게 고유 번호")
                        )
                ))
                .andExpect(status().isNoContent());
    }

    @Test
    void findShopsBySearch() throws Exception {
        String keyword = "shop2";
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/search/shops/{keyword}", keyword))
                .andDo(print())
                .andDo(document("shops/search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("keyword").description("검색 키워드")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").description("가게 고유번호"),
                                fieldWithPath("data.[].name").description("가게 이름"),
                                fieldWithPath("data.[].image").description("가게 이미지"),
                                fieldWithPath("data.[].category").description("가게 카테고리"),
                                fieldWithPath("data.[].phone").description("가게 전화번호"),
                                fieldWithPath("data.[].address").description("가게 주소"),
                                fieldWithPath("data.[].hours.open").description("가게 오픈시간"),
                                fieldWithPath("data.[].hours.close").description("가게 마감시간")
                        )
                ))
                .andExpect(status().isOk());
    }
}