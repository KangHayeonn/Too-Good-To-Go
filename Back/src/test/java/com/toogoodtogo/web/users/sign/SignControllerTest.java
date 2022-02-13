package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import com.toogoodtogo.web.users.sign.dto.SignupUserRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.TokenRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SignControllerTest extends ControllerTest {
    @BeforeEach
    public void setUp() {
        productRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();

        signService.signup(SignupUserRequest.builder()
                .email("user@email.com").password("user_password")
                .name("userA").phone("01000000000").role("ROLE_USER").build());
        signService.signup(SignupUserRequest.builder()
                .email("manager@email.com").password("manager_password")
                .name("managerA").phone("01011111111").role("ROLE_MANAGER").build());
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
        String object = objectMapper.writeValueAsString(LoginUserRequest.builder()
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
                .andDo(document("join/login/"+ role + "/success",
                        preprocessRequest(
//                                modifyUris().scheme("https").host("www.tgtg.com").removePort(),
                                prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessToken 만료시간")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void login_fail(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(LoginUserRequest.builder()
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
                .andDo(document("join/login/" + role + "/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("reason").description("에러 이유"),
                                fieldWithPath("message").description("에러 메시지")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_success(String role) throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        String object = objectMapper.writeValueAsString(SignupUserRequest.builder()
                .email(role + "B@email.com")
                .password(role + "_password")
                .name(role + "B")
                .phone("01044444444")
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
                .andDo(document("join/signup/" + role + "/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description(role + " 이메일"),
                                fieldWithPath("password").description(role + " 비밀번호"),
                                fieldWithPath("name").description(role + " 이름"),
                                fieldWithPath("phone").description(role + " 전화번호"),
                                fieldWithPath("role").description(role + " 역할")
                        ),
                        responseFields(
                                fieldWithPath("data.userId").description("유저 고유 번호")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({"user", "manager"})
    public void signUp_fail(String role) throws Exception {
        //given
        String object = objectMapper.writeValueAsString(SignupUserRequest.builder()
                .email(role + "@email.com")
                .password(role + "_password")
                .name(role + "B")
                .phone("01044444444")
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
                .andDo(document("join/signup/" + role + "/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description(role + " 이메일"),
                                fieldWithPath("password").description(role + " 비밀번호"),
                                fieldWithPath("name").description(role + " 이름"),
                                fieldWithPath("phone").description(role + " 전화번호"),
                                fieldWithPath("role").description(role + " 역할")
                        ),
                        responseFields(
                                fieldWithPath("reason").description("에러 이유"),
                                fieldWithPath("message").description("에러 메시지")
                        )
                ))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void reissue() throws Exception {
        //given
        TokenDto userToken = signService.login(LoginUserRequest.builder().email("user@email.com").password("user_password").build());
        String object = objectMapper.writeValueAsString(TokenRequest.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/reissue")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("join/reissue",
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
                                fieldWithPath("data.accessTokenExpireDate").description("accessToken 만료시간")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    public void logout() throws Exception {
        //given
        TokenDto userToken = signService.login(LoginUserRequest.builder().email("user@email.com").password("user_password").build());

        ResultActions actions = mockMvc.perform(delete("/api/logout")
                .header("Authorization", "Bearer " + userToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("join/logout",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token"))
                ))
                .andExpect(status().isNoContent());
    }
}