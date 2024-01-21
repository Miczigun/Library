package pl.polsl.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.service.AdminService;
import pl.polsl.library.service.MemberService;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testGetUsers() throws Exception {
        when(memberService.getMembers()).thenReturn(Collections.singletonList(new Member()));

        mockMvc.perform(get("/admin/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(memberService, times(1)).getMembers();
    }

    @Test
    void testGetUser() throws Exception {
        long userId = 1L;
        when(memberService.getMember(userId)).thenReturn(new Member());

        mockMvc.perform(get("/admin/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());

        verify(memberService, times(1)).getMember(userId);
    }

    @Test
    void testDeleteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(delete("/admin/user/{id}", userId))
                .andExpect(status().isOk());

        verify(memberService, times(1)).deleteMember(userId);
    }

    @Test
    void testAddUser() throws Exception {
        Member member = new Member();

        when(memberService.addMember(any(Member.class))).thenReturn(member);

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());

        verify(memberService, times(1)).addMember(any(Member.class));
    }

    @Test
    void testCreateLibrarianSuccess() throws Exception {
        RegisterMember registerMember = new RegisterMember("librarian@test.com", "password", "Librarian", "LastName", "Address");
        when(adminService.createLibrarian(eq(registerMember))).thenReturn(new Member());

        mockMvc.perform(post("/admin/create-librarian")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));

        verify(adminService, times(1)).createLibrarian(any(RegisterMember.class));
    }

    @Test
    void testCreateLibrarianInvalidData() throws Exception {
        RegisterMember invalidMember = new RegisterMember();

        ResultActions resultActions = mockMvc.perform(post("/admin/create-librarian")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidMember)));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid Data"));

        verify(adminService, never()).createLibrarian(any(RegisterMember.class));
    }

    @Test
    void testSetRole() throws Exception {
        Map<String, String> roleData = Map.of("id", "1", "role", "LIBRARIAN");

        mockMvc.perform(post("/admin/set-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(roleData)))
                .andExpect(status().isOk());

        verify(adminService, times(1)).setRole(1L, "LIBRARIAN");
    }
}

