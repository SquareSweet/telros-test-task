package prj.sqsw.telrostest.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserContactsDto {
    @Email(message = "User email should meet email format requirements")
    String email;
    String phoneNumber;
}
