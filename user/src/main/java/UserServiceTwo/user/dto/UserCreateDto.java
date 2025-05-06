package UserServiceTwo.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDto {

    private String email;
    private String username;
    private String password;
    private String lastName;
    private String firstName;
}
