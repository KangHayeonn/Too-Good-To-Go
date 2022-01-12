package com.toogoodtogo.web.users.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class SignControllerTest {
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
    private SignService signService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();

        signService.signup(UserSignupReq.builder()
                .email("user@email.com").password("user_password")
                .name("userA").phone("010-0000-0000").role("ROLE_USER").build());
        signService.signup(UserSignupReq.builder()
                .email("manager@email.com").password("manager_password")
                .name("managerA").phone("010-1111-1111").role("ROLE_MANAGER").build());
    }

    @AfterEach
    public void setDown() {
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void login_success(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginReq.builder()
                .email(role + "@email.com")
                .password(role + "_password")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("login/"+ role + "/success",
                        preprocessRequest(
//                                modifyUris().scheme("https").host("www.tgtg.com").removePort(),
                                prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
                        ),
                        responseFields(
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessTokenExpireDate")
                        )
                ))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void login_fail(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginReq.builder()
                .email(role + "@email.com")
                .password("wrongPassword")
                .build());
        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("login/" + role + "/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
                        ),
                        responseFields(
                                fieldWithPath("reason").description("reason"),
                                fieldWithPath("message").description("message"),
                                fieldWithPath("errors").description("errors")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_success(String role) throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupReq.builder()
                .email(role + "B@email.com")
                .password(role + "_password")
                .name(role + "B")
                .phone("010-4444-4444")
                .role(role.toUpperCase())
                .build());

        //then
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("signup/" + role + "/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description(role + " email"),
                                fieldWithPath("password").description(role + " password"),
                                fieldWithPath("name").description(role + " name"),
                                fieldWithPath("phone").description(role + " phone"),
                                fieldWithPath("role").description(role + " role")
                        ),
                        responseFields(
                                fieldWithPath("data.userId").description("user id")
                        )
                ))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_fail(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupReq.builder()
                .email(role + "@email.com")
                .password(role + "_password")
                .name(role + "B")
                .phone("010-4444-4444")
                .role(role.toUpperCase())
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(object));

        //then
        actions
                .andDo(print())
                .andDo(document("signup/" + role + "/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description(role + " email"),
                                fieldWithPath("password").description(role + " password"),
                                fieldWithPath("name").description(role + " name"),
                                fieldWithPath("phone").description(role + " phone"),
                                fieldWithPath("role").description(role + " role")
                        ),
                        responseFields(
                                fieldWithPath("reason").description("reason"),
                                fieldWithPath("message").description("message"),
                                fieldWithPath("errors").description("errors")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void reissue() throws Exception {
        //given
        TokenDto userToken = signService.login(UserLoginReq.builder().email("user@email.com").password("user_password").build());
        String object = objectMapper.writeValueAsString(TokenReq.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/reissue")
                .content(object)
                .header("Authorization", "Bearer " + userToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("reissue",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("accessToken").description("accessToken"),
                                fieldWithPath("refreshToken").description("refreshToken")
                        ),
                        responseFields(
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessTokenExpireDate")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void logout() throws Exception {
        //given
        TokenDto userToken = signService.login(UserLoginReq.builder().email("user@email.com").password("user_password").build());

        ResultActions actions = mockMvc.perform(get("/api/logout")
                .header("Authorization", "Bearer " + userToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("logout",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("success message")
                        )
                ))
                .andExpect(status().isOk());
    }
}