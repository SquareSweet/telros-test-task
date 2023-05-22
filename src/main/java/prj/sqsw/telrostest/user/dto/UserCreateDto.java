package prj.sqsw.telrostest.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserCreateDto {
    @NotBlank(message = "User email should not be blank")
    @Email(message = "User email should meet email format requirements")
    String email;
    @NotBlank(message = "User last name should not be blank")
    String lastName;
    @NotBlank(message = "User first name should not be blank")
    String firstName;
    @NotBlank(message = "User mid name should not be blank")
    String midName;
    @NotBlank(message = "User birthday should not be blank")
    String birthday;
    String phoneNumber;
}
