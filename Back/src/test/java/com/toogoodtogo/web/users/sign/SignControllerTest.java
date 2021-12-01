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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
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
                .email("user@email.com")
                .password(passwordEncoder.encode("user_pw"))
                .name("userA")
                .phoneNumber("010-0000-0000")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        userRepository.save(User.builder()
                .email("admin@email.com")
                .password(passwordEncoder.encode("admin_pw"))
                .name("adminA")
                .phoneNumber("010-1111-1111")
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build());
        userRepository.save(User.builder()
                .email("ceo@email.com")
                .password(passwordEncoder.encode("ceo_pw"))
                .name("ceoA")
                .phoneNumber("010-2222-2222")
                .roles(Collections.singletonList("ROLE_CEO"))
                .build());
    }

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @Test
    public void user_login_success() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("user@email.com")
                .password("user_pw")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("login/user/success",
                        preprocessRequest(
//                                modifyUris().scheme("https").host("www.tgtg.com").removePort(),
                                prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void user_login_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("user@email.com")
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
                .andDo(document("login/user/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void admin_login_success() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("admin@email.com")
                .password("admin_pw")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("login/admin/success",
                        preprocessRequest(
//                                modifyUris().scheme("https").host("www.tgtg.com").removePort(),
                                prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void admin_login_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("admin@email.com")
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
                .andDo(document("login/admin/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void ceo_login_success() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("ceo@email.com")
                .password("ceo_pw")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/login")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("login/ceo/success",
                        preprocessRequest(
//                                modifyUris().scheme("https").host("www.tgtg.com").removePort(),
                                prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void ceo_login_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("ceo@email.com")
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
                .andDo(document("login/ceo/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("login email"),
                                fieldWithPath("password").description("login password")
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
    public void user_signUp_success() throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("userB@email.com" + time)
                .password("userB_pw")
                .name("userB")
                .phoneNumber("010-4444-4444")
                .roles("USER")
                .build());

        //then
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("signup/user/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("password").description("user password"),
                                fieldWithPath("name").description("user name"),
                                fieldWithPath("phoneNumber").description("user phoneNumber"),
                                fieldWithPath("roles").description("user role")
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
    public void user_signUp_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("user@email.com")
                .password("userB_pw")
                .name("userB")
                .phoneNumber("010-4444-4444")
                .roles("USER")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(object));

        //then
        actions
                .andDo(print())
                .andDo(document("signup/user/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("password").description("user password"),
                                fieldWithPath("name").description("user name"),
                                fieldWithPath("phoneNumber").description("user phoneNumber"),
                                fieldWithPath("roles").description("user role")
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
    public void admin_signUp_success() throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("adminB@email.com" + time)
                .password("adminB_pw")
                .name("adminB")
                .phoneNumber("010-5555-5555")
                .roles("ADMIN")
                .build());

        //then
        ResultActions actions = mockMvc.perform(post("/api/signup")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("signup/admin/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("admin email"),
                                fieldWithPath("password").description("admin password"),
                                fieldWithPath("name").description("admin name"),
                                fieldWithPath("phoneNumber").description("admin phoneNumber"),
                                fieldWithPath("roles").description("admin role")
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
    public void admin_signUp_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("admin@email.com")
                .password("adminB_pw")
                .name("adminB")
                .phoneNumber("010-5555-5555")
                .roles("ADMIN")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(object));

        //then
        actions
                .andDo(print())
                .andDo(document("signup/admin/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("admin email"),
                                fieldWithPath("password").description("admin password"),
                                fieldWithPath("name").description("admin name"),
                                fieldWithPath("phoneNumber").description("admin phoneNumber"),
                                fieldWithPath("roles").description("admin role")
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
    public void ceo_signUp_success() throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("ceoB@email.com" + time)
                .password("ceoB_pw")
                .name("ceoB")
                .phoneNumber("010-6666-6666")
                .roles("CEO")
                .build());

        //then
        ResultActions actions = mockMvc.perform(post("/api/signup")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("signup/ceo/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("ceo email"),
                                fieldWithPath("password").description("ceo password"),
                                fieldWithPath("name").description("ceo name"),
                                fieldWithPath("phoneNumber").description("ceo phoneNumber"),
                                fieldWithPath("roles").description("ceo role")
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
    public void ceo_signUp_fail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("ceo@email.com")
                .password("ceoB_pw")
                .name("ceoB")
                .phoneNumber("010-6666-6666")
                .roles("CEO")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(object));

        //then
        actions
                .andDo(print())
                .andDo(document("signup/ceo/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("ceo email"),
                                fieldWithPath("password").description("ceo password"),
                                fieldWithPath("name").description("ceo name"),
                                fieldWithPath("phoneNumber").description("ceo phoneNumber"),
                                fieldWithPath("roles").description("ceo role")
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
    @WithMockUser(roles = {"ADMIN"})
    public void access_success() throws Exception {
        //then
        mockMvc.perform(get("/api/admin/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void access_denied() throws Exception {
        //then
        mockMvc.perform(get("/api/admin/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/accessDenied"));
        ;
    }
}
