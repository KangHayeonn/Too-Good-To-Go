package com.toogoodtogo.web.shops;

import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import com.toogoodtogo.web.users.sign.dto.TokenRequest;
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
        highestRateProductRepository.deleteAllInBatch();
        displayProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        manager = userRepository.save(User.builder()
                .email("shopTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("shopTest@email.com").password("password").build());

        Shop shop1 = Shop.builder()
                .user(manager).name("shop1").image("https://diefqsnmvol80.cloudfront.net/test.png")
                .category(Arrays.asList("??????")).phone("01012345678")
                .address("??????????????? ????????? ?????? 1??????").hours(new Hours("10:00", "22:00")).build();
        shopRepository.save(shop1);
        shopRepository.save(Shop.builder()
                .user(manager).name("shop2").image("test2").category(Arrays.asList("??????")).phone("01056789012")
                .address("??????????????? ????????? ?????? 2??????").hours(new Hours("11:00", "23:00")).build());
        shopId = Math.toIntExact(shop1.getId());
    }

    @AfterEach
    public void setDown() {
        signService.logout(new TokenRequest(token.getAccessToken(), token.getRefreshToken()));
        highestRateProductRepository.deleteAllInBatch();
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
                                fieldWithPath("data.[].id").description("?????? ????????????"),
                                fieldWithPath("data.[].name").description("?????? ??????"),
                                fieldWithPath("data.[].image").description("?????? ?????????"),
                                fieldWithPath("data.[].category").description("?????? ????????????"),
                                fieldWithPath("data.[].phone").description("?????? ????????????"),
                                fieldWithPath("data.[].address").description("?????? ??????"),
                                fieldWithPath("data.[].hours.open").description("?????? ????????????"),
                                fieldWithPath("data.[].hours.close").description("?????? ????????????")
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
                .andDo(document("shops/findManager",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.[].id").description("?????? ????????????"),
                                fieldWithPath("data.[].name").description("?????? ??????"),
                                fieldWithPath("data.[].image").description("?????? ?????????"),
                                fieldWithPath("data.[].category").description("?????? ????????????"),
                                fieldWithPath("data.[].phone").description("?????? ????????????"),
                                fieldWithPath("data.[].address").description("?????? ??????"),
                                fieldWithPath("data.[].hours.open").description("?????? ????????????"),
                                fieldWithPath("data.[].hours.close").description("?????? ????????????")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void findShop() throws Exception {
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/shops/{shopId}", shopId))
                .andDo(print())
                .andDo(document("shops/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("shopId").description("?????? ?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.id").description("?????? ????????????"),
                                fieldWithPath("data.name").description("?????? ??????"),
                                fieldWithPath("data.image").description("?????? ?????????"),
                                fieldWithPath("data.category").description("?????? ????????????"),
                                fieldWithPath("data.phone").description("?????? ????????????"),
                                fieldWithPath("data.address").description("?????? ??????"),
                                fieldWithPath("data.hours.open").description("?????? ????????????"),
                                fieldWithPath("data.hours.close").description("?????? ????????????")
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
        ResultActions actions = mockMvc.perform(multipart("/api/manager/shops")
                .file(new MockMultipartFile("file", null, null, (InputStream) null))
//                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\?????????\\Desktop\\test.png")))
                .file(request).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("shops/add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("???????????? Access Token")),
                        requestParts(
                                partWithName("file").description("?????? ?????????"),
                                partWithName("request").description("?????? ??????")
                        ),
                        requestPartFields("request",
                                fieldWithPath("name").description("?????? ??????"),
                                fieldWithPath("category").description("?????? ????????????"),
                                fieldWithPath("phone").description("?????? ????????????"),
                                fieldWithPath("address").description("?????? ??????"),
                                fieldWithPath("open").description("?????? ????????????"),
                                fieldWithPath("close").description("?????? ????????????")
                        ),
                        responseFields(
                                fieldWithPath("data.id").description("?????? ????????????"),
                                fieldWithPath("data.name").description("?????? ??????"),
                                fieldWithPath("data.image").description("?????? ?????????"),
                                fieldWithPath("data.category").description("?????? ????????????"),
                                fieldWithPath("data.phone").description("?????? ????????????"),
                                fieldWithPath("data.address").description("?????? ??????"),
                                fieldWithPath("data.hours.open").description("?????? ????????????"),
                                fieldWithPath("data.hours.close").description("?????? ????????????")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    void updateShop() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateShopRequest.builder()
                .name("shop3").category(new ArrayList<>(Arrays.asList("category2")))
                .phone("01087654321").address("test3")
                .open("12:00").close("21:00").build());

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/manager/shops/{shopId}", shopId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

//        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
//                .fileUpload("/api/manager/shops/{shopId}", shopId)
//                .file(new MockMultipartFile("file", null, null, (InputStream) null))
////                .file(new MockMultipartFile("file", "test.png", "image/png", new FileInputStream("C:\\Users\\?????????\\Desktop\\test.png")))
//                .file(request).accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("shops/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("???????????? Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("?????? ?????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("name").description("?????? ??????"),
                                fieldWithPath("category").description("?????? ????????????"),
                                fieldWithPath("phone").description("?????? ????????????"),
                                fieldWithPath("address").description("?????? ??????"),
                                fieldWithPath("open").description("?????? ????????????"),
                                fieldWithPath("close").description("?????? ????????????")
                        ),
                        responseFields(
                                fieldWithPath("data.id").description("?????? ????????????"),
                                fieldWithPath("data.name").description("?????? ??????"),
                                fieldWithPath("data.image").description("?????? ?????????"),
                                fieldWithPath("data.category").description("?????? ????????????"),
                                fieldWithPath("data.phone").description("?????? ????????????"),
                                fieldWithPath("data.address").description("?????? ??????"),
                                fieldWithPath("data.hours.open").description("?????? ????????????"),
                                fieldWithPath("data.hours.close").description("?????? ????????????")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void updateShopImage() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                fileUpload("/api/manager/shops/{shopId}/image", shopId)
                .file(new MockMultipartFile("file", "test.png", "image/png", "test".getBytes()))
                .header("Authorization", "Bearer " + token.getAccessToken()));

        //then
        actions
                .andDo(print())
                .andDo(document("shops/updateImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("???????????? Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("?????? ?????? ??????")
                        ),
                        requestParts(partWithName("file").description("?????? ?????????")),
                        responseFields(
                                fieldWithPath("data").description("?????? ?????????")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShopImage() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/manager/shops/{shopId}/image", shopId)
                .header("Authorization", "Bearer " + token.getAccessToken()));
        //then
        actions
                .andDo(print())
                .andDo(document("shops/deleteImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("???????????? Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isNoContent());
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
                        requestHeaders(headerWithName("Authorization").description("???????????? Access Token")),
                        pathParameters(
                                parameterWithName("shopId").description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isNoContent());
    }
}