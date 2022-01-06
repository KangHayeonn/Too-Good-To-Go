package com.toogoodtogo.web.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.users.sign.TokenDto;
import com.toogoodtogo.web.users.sign.UserLoginRequest;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    private SignService signService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private TokenDto token;

    @BeforeEach
    public void setUp() {
        userRepository.save(User.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("010-0000-0000")
                .role("ROLE_USER")
                .build());
<<<<<<< HEAD

        token = signService.login(UserLoginRequest.builder().email("email@email.com").password("password").build());
=======
        id = Math.toIntExact(save.getId());
        userRepository.save(User.builder()
                .email("manager@email.com")
                .password(passwordEncoder.encode("manager_pw"))
                .name("managerA")
                .phone("010-1111-1111")
                .role("ROLE_MANAGER")
                .build());
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
    }

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @Test
    public void findUserInfo() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(get("/api/me")
                .header("Authorization", token.getAccessToken())
                .param("lang", "ko"));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("users/me",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.id").description("user id"),
                                fieldWithPath("data.email").description("user email"),
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phone").description("user phone"),
                                fieldWithPath("data.role").description("user role")
                        )
                ))
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.name").value("name"));
    }

<<<<<<< HEAD
    @Test
=======
//    @Test
//    @WithMockUser(roles = "USER")
//    public void findUsers() throws Exception {
//        //then
//        mockMvc.perform(get("/api/users"))
//                .andDo(print())
//                .andDo(document("users/findAll",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("data").description("data"),
//                                fieldWithPath("data.[].id").description("user id"),
//                                fieldWithPath("data.[].email").description("user email"),
//                                fieldWithPath("data.[].name").description("user name"),
//                                fieldWithPath("data.[].phoneNumber").description("user phoneNumber"),
//                                fieldWithPath("data.[].role").description("user role")
//                        )
//                ))
//                .andExpect(status().isOk());
//    }

    @Test
    @WithMockUser(roles = "USER")
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
    public void updateUser() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserUpdateRequest.builder()
                .password("new_password")
                .phone("010-1234-5678")
                .build());

        //when
<<<<<<< HEAD
        ResultActions actions = mockMvc.perform(patch("/api/user")
=======
        ResultActions actions = mockMvc.perform(patch("/api/user/{id}", id)
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
                .content(object)
                .header("Authorization", token.getAccessToken())
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
                                fieldWithPath("data.name").description("user name"),
                                fieldWithPath("data.phone").description("user phone"),
                                fieldWithPath("data.role").description("user role")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.phone").value("010-1234-5678"));
    }

<<<<<<< HEAD
=======
//    @Test
//    @WithMockUser(roles = "USER")
//    public void deleteUser() throws Exception {
//        //given
//        //when
//        ResultActions actions = mockMvc.perform(delete("/api/user/{id}", id));
//        //then
//        actions
//                .andDo(print())
//                .andDo(document("users/delete",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("data").description("data")
//                        )
//                ))
//                .andExpect(status().isOk());
//    }
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
}