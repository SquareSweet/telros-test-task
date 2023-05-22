package prj.sqsw.telrostest.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.dto.UserUpdateDto;
import prj.sqsw.telrostest.user.model.User;
import prj.sqsw.telrostest.user.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    private UserFullDto user;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService mockService;

    @BeforeEach
    void setUp() {
        user = UserFullDto.builder()
                .id(1L)
                .email("test@email.com")
                .lastName("Smith")
                .firstName("John")
                .midName("J")
                .birthday(LocalDate.of(1995, 5, 12))
                .phoneNumber("+79991234567")
                .build();
    }

    @Test
    void getAllUsersTest() throws Exception {
        when(mockService.findAll()).thenReturn(List.of(user));
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("test@email.com")))
                .andExpect(jsonPath("$[0].lastName", is("Smith")))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].midName", is("J")))
                .andExpect(jsonPath("$[0].birthday", is("1995-05-12")))
                .andExpect(jsonPath("$[0].phoneNumber", is("+79991234567")));
        verify(mockService, times(1)).findAll();
    }

    @Test
    void createUser() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .phoneNumber(user.getPhoneNumber())
                .build();
        when(mockService.create(userCreateDto)).thenReturn(user);
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@email.com")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.midName", is("J")))
                .andExpect(jsonPath("$.birthday", is("1995-05-12")))
                .andExpect(jsonPath("$.phoneNumber", is("+79991234567")));
        verify(mockService, times(1)).create(userCreateDto);
    }

    @Test
    void updateUserTest() throws Exception {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .lastName("Doe")
                .firstName("Jane")
                .midName("S")
                .build();
        user.setLastName(userUpdateDto.getLastName());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setMidName(userUpdateDto.getMidName());
        when(mockService.update(userUpdateDto, 1L)).thenReturn(user);
        mvc.perform(patch("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@email.com")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.firstName", is("Jane")))
                .andExpect(jsonPath("$.midName", is("S")))
                .andExpect(jsonPath("$.birthday", is("1995-05-12")))
                .andExpect(jsonPath("$.phoneNumber", is("+79991234567")));
        verify(mockService, times(1)).update(userUpdateDto, 1L);
    }

    @Test
    void deleteUserTest() throws Exception {
        when(mockService.findById(1L)).thenReturn(User.builder().id(1L).build());
        mvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
        verify(mockService, times(1)).delete(1L);
    }

    @Test
    void updateUserContactsTest() throws Exception {
        UserContactsDto userContactsDto = UserContactsDto.builder()
                .email("updated@email.com")
                .phoneNumber("+79997654321")
                .build();
        user.setEmail(userContactsDto.getEmail());
        user.setPhoneNumber(userContactsDto.getPhoneNumber());
        when(mockService.updateContacts(userContactsDto, 1L)).thenReturn(user);
        mvc.perform(patch("/api/users/1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userContactsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("updated@email.com")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.midName", is("J")))
                .andExpect(jsonPath("$.birthday", is("1995-05-12")))
                .andExpect(jsonPath("$.phoneNumber", is("+79997654321")));
        verify(mockService, times(1)).updateContacts(userContactsDto, 1L);
    }

    @Test
    void getUserContactsTest() throws Exception {
        UserContactsDto userContactsDto = UserContactsDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
        when(mockService.getContacts(1L)).thenReturn(userContactsDto);
        mvc.perform(get("/api/users/1/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@email.com")))
                .andExpect(jsonPath("$.phoneNumber", is("+79991234567")));
        verify(mockService, times(1)).getContacts(1L);
    }
}