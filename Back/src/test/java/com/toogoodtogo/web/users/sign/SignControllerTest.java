package com.toogoodtogo.web.users.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.TooGoodToGoApplication;
import com.toogoodtogo.application.response.ResponseService;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.application.shop.product.ProductService;
import com.toogoodtogo.application.user.UserService;
import com.toogoodtogo.configuration.security.CustomAccessDeniedHandler;
import com.toogoodtogo.configuration.security.CustomAuthenticationEntryPoint;
import com.toogoodtogo.configuration.security.JwtTokenProvider;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.users.UsersController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.save(User.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phoneNumber("010-0000-0000")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @Test
    public void 로그인_성공() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("email@email.com")
                .password("password")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("users/login/success",
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("user password")
                        ),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg"),
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessTokenExpireDate")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    public void 로그인_실패() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("email@email.com")
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
                .andDo(document("users/login/fail",
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("user password")
                        ),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg")
                        )
                ))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1001));
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("email@email.com" + time)
                .password("myPassword")
                .name("myName")
                .phoneNumber("010-0000-0000")
                .build());

        //then
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("users/signup/success",
                        requestFields(
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("password").description("user password"),
                                fieldWithPath("name").description("user name"),
                                fieldWithPath("phoneNumber").description("user phoneNumber")
                        ),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg"),
                                fieldWithPath("data").description("data")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    public void 회원가입_실패() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("email@email.com")
                .password("password")
                .name("myName")
                .phoneNumber("010-0000-0000")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(object));

        //then
        actions
                .andDo(print())
                .andDo(document("users/signup/fail",
                        requestFields(
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("password").description("user password"),
                                fieldWithPath("name").description("user name"),
                                fieldWithPath("phoneNumber").description("user phoneNumber")
                        ),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg")
                        )
                ))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1002));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"GUEST"})
    public void 접근실패() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/accessDenied"));
        ;
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"GUEST", "USER"})
    public void 접근성공() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
