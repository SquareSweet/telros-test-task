package prj.sqsw.telrostest.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserUpdateDto {
    String email;
    String lastName;
    String firstName;
    String midName;
    String birthday;
    String phoneNumber;
}
