package com.toogoodtogo.web.shops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
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
class ShopsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private static int managerId;

    @Autowired
    private static int shopId;

    @Autowired
    private static User manager;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        manager = userRepository.save(User.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("010-0000-0000")
                .role("ROLE_MANAGER")
                .build());
        managerId = Math.toIntExact(manager.getId());

        shopRepository.deleteAll();
        Shop shop1 = Shop.builder()
                .user(manager).name("shop1").image("test1").category(new String[]{"한식"}).phone("010-1234-5678")
                .address("서울특별시 양천구 목동 1번지").hours(new Hours("10:00", "22:00")).build();
        shopRepository.save(shop1);
        shopRepository.save(Shop.builder()
                .user(manager).name("shop2").image("test2").category(new String[]{"중식"}).phone("010-5678-9012")
                .address("서울특별시 양천구 목동 2번지").hours(new Hours("11:00", "23:00")).build());
        shopId = Math.toIntExact(shop1.getId());
    }

    @AfterEach
    public void setDown() {
        shopRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "MANAGER")
    void findShops() throws Exception {
        //then
        mockMvc.perform(get("/api/manager/{managerId}/shops", managerId))
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
    @WithMockUser(roles = "MANAGER")
    void addProduct() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(AddShopRequest.builder()
                .user(manager).name("shop4").image("test4").category(new String[]{"양식"}).phone("010-4444-4444")
                .address("서울특별시 양천구 목동 4번지").open("10:00").close("22:00").build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/manager/{managerId}/shop", managerId)
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
    @WithMockUser(roles = "MANAGER")
    public void updateShop() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateShopRequest.builder()
                .name("shop3")
                .image("test3")
                .category(new String[]{"일식"})
                .phone("010-8765-4321")
                .address("test3")
                .open("12:00")
                .close("21:00")
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/api/manager/shop/{shopId}", shopId)
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
    @WithMockUser(roles = "MANAGER")
    public void deleteShop() throws Exception {
        //then
        mockMvc.perform(delete("/api/manager/shop/{shopId}", shopId))
                .andDo(print())
                .andDo(document("shops/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data")
                        )
                ))
                .andExpect(status().isOk());
    }
}