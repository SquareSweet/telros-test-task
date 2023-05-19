package prj.sqsw.telrostest.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.dto.UserUpdateDto;
import prj.sqsw.telrostest.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserFullDto> getAllUsers() {
        log.debug("Got request to get all users");
        return userService.findAll();
    }

    @PostMapping
    public UserFullDto createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        log.debug("Got request to create user");
        return userService.create(userCreateDto);
    }

    @PatchMapping("/{userId}")
    public UserFullDto updateUser(@PathVariable Long userId,
                                  @RequestBody UserUpdateDto userUpdateDto) {
        log.debug("Got request to update user id: {}", userId);
        return userService.update(userUpdateDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.debug("Got request to delete user id: {}", userId);
        userService.delete(userId);
    }

    @PatchMapping("/{userId}/contacts")
    public UserFullDto updateUserContacts(@PathVariable Long userId,
                                          @Valid @RequestBody UserContactsDto userContactsDto) {
        log.debug("Got request to update user contacts id: {}", userId);
        return userService.updateContacts(userContactsDto, userId);
    }

    @GetMapping("/{userId}/contacts")
    public UserContactsDto getAllUsers(@PathVariable Long userId) {
        log.debug("Got request to get user contacts id: {}", userId);
        return userService.getContacts(userId);
    }
}
