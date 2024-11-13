package kg.project.megatest.controller;

import kg.project.megatest.model.response.UserResponse;
import kg.project.megatest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public Page<UserResponse> findAllBySpecification(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "25") int size,
                                                     @RequestParam(required = false) Optional<Boolean> sortOrder,
                                                     @RequestParam(required = false) String sortBy) {
        return userService.getAll(page, size, sortOrder, sortBy);
    }
}
