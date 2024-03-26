//package com.product.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.product.dto.UserDTO;
//import com.product.entity.User;
//import com.product.service.UserService;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    @Mock // Use @Mock here instead of @MockBean
//    private UserService userService;
//
//    @InjectMocks // This will inject userService into userController
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize mockMvc with userController which now has userService mocked
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testRegisterUser() throws Exception {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername("testUser");
//        userDTO.setPassword("testPass");
//        userDTO.setFirstName("Test");
//        userDTO.setLastName("User");
//        userDTO.setAddress("123 Test Street");
//
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setAddress(userDTO.getAddress());
//
//        given(userService.registerUser(any(UserDTO.class))).willReturn(user);
//
//        mockMvc.perform(post("/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO))
//                .with(csrf()))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(user)));
//
//        verify(userService).registerUser(any(UserDTO.class));
//    }
//}