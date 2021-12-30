package com.toogoodtogo.web.users.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.domain.user.User;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

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
                .role("ROLE_USER")
                .build());
        userRepository.save(User.builder()
                .email("manager@email.com")
                .password(passwordEncoder.encode("manager_pw"))
                .name("managerA")
                .phoneNumber("010-1111-1111")
                .role("ROLE_MANAGER")
                .build());
        userRepository.save(User.builder()
                .email("manager@email.com")
                .password(passwordEncoder.encode("manager_pw"))
                .name("managerA")
                .phoneNumber("010-1111-1111")
                .role("ROLE_MANAGER")
                .build());
    }

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void login_success(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequest.builder()
                .email(role + "@email.com")
                .password(role + "_pw")
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
        String object = objectMapper.writeValueAsString(UserLoginRequest.builder()
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
                                fieldWithPath("message").description("message")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_success(String role) throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(UserSignupRequest.builder()
                .email(role + "B@email.com" + time)
                .password(role + "_pw")
                .name(role + "B")
                .phoneNumber("010-4444-4444")
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
                                fieldWithPath("phoneNumber").description(role + " phoneNumber"),
                                fieldWithPath("role").description(role + " role")
                        ),
                        responseFields(
                                fieldWithPath("data").description("data")
                        )
                ))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_fail(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequest.builder()
                .email(role + "@email.com")
                .password(role + "_pw")
                .name(role + "B")
                .phoneNumber("010-4444-4444")
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
                                fieldWithPath("phoneNumber").description(role + " phoneNumber"),
                                fieldWithPath("role").description(role + " role")
                        ),
                        responseFields(
                                fieldWithPath("reason").description("reason"),
                                fieldWithPath("message").description("message")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void access_success() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"GUEST"})
    public void access_denied() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/accessDenied"));
        ;
    }
}
