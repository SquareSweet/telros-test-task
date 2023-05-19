package prj.sqsw.telrostest.user.service;

import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.dto.UserUpdateDto;
import prj.sqsw.telrostest.user.model.User;

import java.util.List;

public interface UserService {

    UserFullDto create(UserCreateDto userCreateDto);

    UserFullDto update(UserUpdateDto userUpdateDto, Long userId);

    List<UserFullDto> findAll();

    User findById(Long userId);

    void delete(Long userId);

    UserFullDto updateContacts (UserContactsDto userContactsDto, Long userId);

    UserContactsDto getContacts(Long userId);

}
