package kg.project.megatest.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    String name;

    private String username;

    private String password;

    Long roleId;
}
