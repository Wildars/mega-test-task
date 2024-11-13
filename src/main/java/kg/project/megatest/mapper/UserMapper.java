package kg.project.megatest.mapper;


import kg.project.megatest.entity.User;
import kg.project.megatest.model.request.UserRequest;
import kg.project.megatest.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
        }
)
public interface UserMapper {
    UserResponse entityToResponse(User entity);

    User requestToEntityRegister(UserRequest request);

    void update(@MappingTarget User entity, UserRequest request);
}