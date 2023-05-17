package prj.sqsw.telrostest.user.mapper;

import org.springframework.stereotype.Component;
import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserMapper {
    public User toUser (UserCreateDto user) {
        return User.builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(LocalDate.parse(user.getBirthday(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserFullDto toUserFullDto (User user) {
        return UserFullDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .midName(user.getMidName())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserContactsDto toUserContactsDto (User user) {
        return UserContactsDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
