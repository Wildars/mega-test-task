package kg.project.megatest.service;

import kg.project.megatest.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    public Page<UserResponse> getAll(int page,
                                     int size,
                                     Optional<Boolean> sortOrder,
                                     String sortBy);

}
