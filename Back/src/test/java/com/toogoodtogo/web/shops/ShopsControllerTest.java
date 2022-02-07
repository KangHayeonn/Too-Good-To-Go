package com.toogoodtogo.web.shops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.AmazonS3MockConfig;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ChoiceProduct;
import com.toogoodtogo.domain.shop.product.ChoiceProductRepository;
import com.toogoodtogo.domain.shop.product.DisplayProductRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
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
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
class ShopsControllerTest {
    @Autowired
    MockMvc mockMvc;

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
    private ChoiceProductRepository choiceProductRepository;

    @Autowired
    private SignService signService;

    @Autowired
    PasswordEncoder passwordEncoder;

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
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.[].id").description("shop id"),
                                fieldWithPath("data.[].name").description("shop name"),
                                fieldWithPath("data.[].image").description("shop image"),
                                fieldWithPath("data.[].category").description("shop category"),
                                fieldWithPath("data.[].phone").description("shop image"),
                                fieldWithPath("data.[].address").description("shop address"),
                                fieldWithPath("data.[].hours").description("shop hours"),
                                fieldWithPath("data.[].hours.open").description("shop open hour"),
                                fieldWithPath("data.[].hours.close").description("shop close hour")
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
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.[].id").description("shop id"),
                                fieldWithPath("data.[].name").description("shop name"),
                                fieldWithPath("data.[].image").description("shop image"),
                                fieldWithPath("data.[].category").description("shop category"),
                                fieldWithPath("data.[].phone").description("shop image"),
                                fieldWithPath("data.[].address").description("shop address"),
                                fieldWithPath("data.[].hours").description("shop hours"),
                                fieldWithPath("data.[].hours.open").description("shop open hour"),
                                fieldWithPath("data.[].hours.close").description("shop close hour")
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
                        responseFields(
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.id").description("shop id"),
                                fieldWithPath("data.name").description("shop name"),
                                fieldWithPath("data.image").description("shop image"),
                                fieldWithPath("data.category").description("shop category"),
                                fieldWithPath("data.phone").description("shop image"),
                                fieldWithPath("data.address").description("shop address"),
                                fieldWithPath("data.hours").description("shop hours"),
                                fieldWithPath("data.hours.open").description("shop open hour"),
                                fieldWithPath("data.hours.close").description("shop close hour")
                        )
                ))
                .andExpect(status().isOk());
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

        ResultActions actions = mockMvc.perform(multipart("/api/manager/shops/{shopId}", shopId)
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
                        responseFields(
                                fieldWithPath("data").description("shop data"),
                                fieldWithPath("data.id").description("shop id"),
                                fieldWithPath("data.name").description("shop name"),
                                fieldWithPath("data.image").description("shop image"),
                                fieldWithPath("data.category").description("shop category"),
                                fieldWithPath("data.phone").description("shop image"),
                                fieldWithPath("data.address").description("shop address"),
                                fieldWithPath("data.hours").description("shop hours"),
                                fieldWithPath("data.hours.open").description("shop open hour"),
                                fieldWithPath("data.hours.close").description("shop close hour")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShop() throws Exception {
        //then
        mockMvc.perform(delete("/api/manager/shops/{shopId}", shopId)
                .header("Authorization", "Bearer " + token.getAccessToken()))
                .andDo(print())
                .andDo(document("shops/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("success message")
                        )
                ))
                .andExpect(status().isOk());
    }
}