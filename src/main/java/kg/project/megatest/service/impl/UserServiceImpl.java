package kg.project.megatest.service.impl;

import kg.project.megatest.entity.User;
import kg.project.megatest.mapper.UserMapper;
import kg.project.megatest.model.response.UserResponse;
import kg.project.megatest.repository.UserRepository;
import kg.project.megatest.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> getAll(int page,
                                     int size,
                                     Optional<Boolean> sortOrder,
                                     String sortBy) {


        Pageable paging = null;


        if (sortOrder.isPresent()){
            Sort.Direction direction = sortOrder.orElse(true) ? Sort.Direction.ASC : Sort.Direction.DESC;
            paging = PageRequest.of(page, size, direction, sortBy);
        } else {
            paging = PageRequest.of(page, size);
        }
        Page<User> saleItemsPage = userRepository.findAll(paging);

        return saleItemsPage.map(userMapper::entityToResponse);
    }

}
