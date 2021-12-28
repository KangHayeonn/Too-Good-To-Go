package com.toogoodtogo.web.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.application.user.UserService;
import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.users.sign.UserLoginRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .role("ROLE_USER")
                .build());
        id = Math.toIntExact(save.getId());
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

    @Test
    @WithMockUser(roles = "USER")
    public void findUserInfo() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(
                get("/api/user/id/{id}", id)
                .param("lang", "ko"));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("users/userInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.id").description("user id"),
                                fieldWithPath("data.email").description("user email"),
                                fieldWithPath("data.password").description("user password"),
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.role").description("user role")
                        )
                ))
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.name").value("name"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findPasswordByEmail() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserEmailRequest.builder()
                .email("email@email.com")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/api/user/email")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("users/findPasswordByEmail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.password").description("user password")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findUsers() throws Exception {
        //then
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andDo(document("users/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.[].id").description("user id"),
                                fieldWithPath("data.[].email").description("user email"),
                                fieldWithPath("data.[].password").description("user password"),
                                fieldWithPath("data.[].name").description("user name"),
                                fieldWithPath("data.[].phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.[].role").description("user role")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateUser() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserUpdateRequest.builder()
                .password("new_password")
                .name("new_name")
                .phoneNumber("010-1234-5678")
                .build());

        //when
        ResultActions actions = mockMvc.perform(put("/api/user/{id}", id)
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("users/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.id").description("user id"),
                                fieldWithPath("data.email").description("user email"),
                                fieldWithPath("data.password").description("user password"),
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phoneNumber").description("user phoneNumber"),
                                fieldWithPath("data.role").description("user role")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("new_name"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-5678"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteUser() throws Exception {
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
                                fieldWithPath("data").description("data")
                        )
                ))
                .andExpect(status().isOk());
    }
}