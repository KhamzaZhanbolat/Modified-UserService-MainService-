package UserServiceTwo.user.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInDto {

    private String username;
    private String password;
}
