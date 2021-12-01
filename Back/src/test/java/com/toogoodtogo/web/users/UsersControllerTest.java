package com.toogoodtogo.web.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.application.user.UserService;
import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private static int id;

    @BeforeEach
    public void setUp() {
        User save = userRepository.save(User.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phoneNumber("010-0000-0000")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        id = Math.toIntExact(save.getId());
    }

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void 회원조회_userId() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(
                get("/api/user/id/{id}", id)
                .param("lang", "ko"));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("users/findById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.id").description("user id"),
                                fieldWithPath("data.email").description("user email"),
                                fieldWithPath("data.password").description("user password"),
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.roles").description("user roles")
                        )
                ))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.name").value("name"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void 회원조회_email() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(
                        get("/api/user/email/{email}", "email@email.com")
                        .param("lang", "ko"));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("users/findByEmail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.id").description("user id"),
                                fieldWithPath("data.email").description("user email"),
                                fieldWithPath("data.password").description("user password"),
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.roles").description("user roles")
                        )
                ))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.name").value("name"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void 전체회원조회() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andDo(document("users/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.[].id").description("user id"),
                                fieldWithPath("data.[].email").description("user email"),
                                fieldWithPath("data.[].password").description("user password"),
                                fieldWithPath("data.[].name").description("user name"),
                                fieldWithPath("data.[].phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.[].roles").description("user roles")
                        )
                ))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void 회원삭제() throws Exception {
        //given
        //when
        ResultActions actions = mockMvc.perform(delete("/api/user/{id}", id));
        //then
        actions
                .andDo(print())
                .andDo(document("users/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").description("success"),
                                fieldWithPath("code").description("code"),
                                fieldWithPath("msg").description("msg")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }
}