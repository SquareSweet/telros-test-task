package prj.sqsw.telrostest.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserFullDto {
    Long id;
    String email;
    String lastName;
    String firstName;
    String midName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    String phoneNumber;
}
