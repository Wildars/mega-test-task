package kg.project.megatest.mapper;

import kg.project.megatest.entity.Role;
import kg.project.megatest.model.request.RoleRequest;
import kg.project.megatest.model.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {

        }
)
public interface RoleMapper {
    RoleResponse entityToResponse(Role user);

    Role requestToEntity(RoleRequest request);

    void update(@MappingTarget Role entity, RoleRequest request);
}
