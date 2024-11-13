package kg.project.megatest.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;
    String name;

    private String email;

    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @NotNull(message = "Роль не может быть пустой")
    private Long roleId;

}