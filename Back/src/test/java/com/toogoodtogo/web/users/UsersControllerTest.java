package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.users.dto.UpdateUserRequest;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import com.toogoodtogo.web.users.sign.dto.LoginUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsersControllerTest extends ControllerTest {
    private User user;
    private TokenDto token;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAllInBatch();
        refreshTokenRepository.deleteAllInBatch();

        user = userRepository.save(User.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_USER")
                .build());

        token = signService.login(LoginUserRequest.builder().email("email@email.com").password("password").build());
    }

    @AfterEach
    public void setDown() {
        signService.logout(user.getId());
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAllInBatch();
    }

    @Test
    public void findUserInfo() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(get("/api/me")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .param("lang", "ko"));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("users/me",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("유저의 Access Token")),
                        responseFields(
                                fieldWithPath("data.id").description("회원 고유번호"),
                                fieldWithPath("data.email").description("회원 이메일"),
                                fieldWithPath("data.name").description("회원 이름"),
                                fieldWithPath("data.phone").description("회원 전화번호"),
                                fieldWithPath("data.role").description("회원 role")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.name").value("name"));
    }

    @Test
    public void updateUser() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .password("new_password")
                .phone("01012345678")
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/api/me")
                .content(object)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("users/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("매니저의 Access Token")),
                        requestFields(
                                fieldWithPath("password").description("회원 비밀번호"),
                                fieldWithPath("phone").description("회원 전화번호")
                        ),
                        responseFields(
                                fieldWithPath("data.id").description("회원 고유번호"),
                                fieldWithPath("data.email").description("회원 이메일"),
                                fieldWithPath("data.name").description("회원 이름"),
                                fieldWithPath("data.phone").description("회원 전화번호"),
                                fieldWithPath("data.role").description("회원 role")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.phone").value("01012345678"));
    }
}