package prj.sqsw.telrostest.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class UserUpdateDto {
    String email;
    String lastName;
    String firstName;
    String midName;
    String birthday;
    String phoneNumber;
}
