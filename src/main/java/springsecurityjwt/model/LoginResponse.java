package springsecurityjwt.model;

import lombok.Data;

@Data
public class LoginResponse {

    private final String jwt;
}
