package prj.sqsw.telrostest.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.dto.UserUpdateDto;
import prj.sqsw.telrostest.user.exception.UserNotFoundException;
import prj.sqsw.telrostest.user.mapper.UserMapper;
import prj.sqsw.telrostest.user.model.User;
import prj.sqsw.telrostest.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    private User user;
    @MockBean
    private UserRepository mockRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@email.com")
                .lastName("Smith")
                .firstName("John")
                .midName("J")
                .birthday(LocalDate.of(1995, 5, 12))
                .phoneNumber("+79991234567")
                .build();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Create new User, expected repository save method called")
    void createUser() {
        UserCreateDto userCreate = UserCreateDto.builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .phoneNumber(user.getPhoneNumber())
                .build();
        when(mockRepository.save(userMapper.toUser(userCreate))).thenReturn(user);

        UserFullDto expected = userMapper.toUserFullDto(user);
        UserFullDto actual = userService.create(userCreate);
        assertEquals(expected, actual);
        verify(mockRepository, times(1)).save(userMapper.toUser(userCreate));
    }

    @Test
    @DisplayName("Update existing User, expected repository save method called")
    void updateUserAllFields() {
        User userUpdated = User.builder()
                .id(1L)
                .email("updated@email.com")
                .lastName("Doe")
                .firstName("Jane")
                .midName("S")
                .birthday(LocalDate.of(1997, 12, 5))
                .phoneNumber("+79997654321")
                .build();
        when(mockRepository.save(userUpdated)).thenReturn(userUpdated);

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .email(userUpdated.getEmail())
                .lastName(userUpdated.getLastName())
                .firstName(userUpdated.getFirstName())
                .midName(userUpdated.getMidName())
                .birthday(userUpdated.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .phoneNumber(userUpdated.getPhoneNumber())
                .build();
        UserFullDto expected = userMapper.toUserFullDto(userUpdated);
        UserFullDto actual = userService.update(userUpdateDto, 1L);
        assertEquals(expected, actual);
        verify(mockRepository, times(1)).save(userUpdated);
    }

    @Test
    @DisplayName("Partially update User, expected only mentioned fields updated")
    void updateUserPartial() {
        User userUpdated = User.builder()
                .id(1L)
                .email("updated@email.com")
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(LocalDate.of(1997, 12, 5))
                .phoneNumber("+79997654321")
                .build();
        when(mockRepository.save(userUpdated)).thenReturn(userUpdated);

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .email(userUpdated.getEmail())
                .birthday(userUpdated.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .phoneNumber(userUpdated.getPhoneNumber())
                .build();
        UserFullDto expected = userMapper.toUserFullDto(userUpdated);
        UserFullDto actual = userService.update(userUpdateDto, 1L);
        assertEquals(expected, actual);
        verify(mockRepository, times(1)).save(userUpdated);
    }

    @Test
    @DisplayName("Update non existent User, expected UserNotFoundException thrown")
    void updateNonExistentUser() {
        assertThrows(UserNotFoundException.class, () -> userService.update(UserUpdateDto.builder().build(), -1L));
    }

    @Test
    @DisplayName("Get all Users, expected repository findAll method called")
    void findAllUsers() {
        when(mockRepository.findAll()).thenReturn(List.of(user));
        assertEquals(List.of(userMapper.toUserFullDto(user)), userService.findAll());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get User by Id, expected repository findById method called")
    void findUserById() {
        assertEquals(user, userService.findById(1L));
        verify(mockRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get non existent User by Id, expected UserNotFoundException thrown")
    void findNonExistentUserById() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(-1L));
    }

    @Test
    @DisplayName("Delete User, expected repository delete method called")
    void deleteUser() {
        userService.delete(1L);
        verify(mockRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Delete non existent User by Id, expected UserNotFoundException thrown")
    void deleteNonExistentUser() {
        assertThrows(UserNotFoundException.class, () -> userService.delete(-1L));
    }

    @Test
    @DisplayName("Update User contacts, expected repository save method called")
    void updateUserContacts() {
        User userUpdated = User.builder()
                .id(1L)
                .email("updated@email.com")
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(LocalDate.of(1995, 5, 12))
                .phoneNumber("+79997654321")
                .build();
        when(mockRepository.save(userUpdated)).thenReturn(userUpdated);

        UserContactsDto userContactsDto = UserContactsDto.builder()
                .email(userUpdated.getEmail())
                .phoneNumber(userUpdated.getPhoneNumber())
                .build();
        UserFullDto expected = userMapper.toUserFullDto(userUpdated);
        UserFullDto actual = userService.updateContacts(userContactsDto, 1L);
        assertEquals(expected, actual);
        verify(mockRepository, times(1)).save(userUpdated);
    }

    @Test
    @DisplayName("Update non existent User contacts, expected UserNotFoundException thrown")
    void updateNonExistentUserContacts() {
        assertThrows(UserNotFoundException.class,
                () -> userService.updateContacts(UserContactsDto.builder().build(), -1L));
    }
}