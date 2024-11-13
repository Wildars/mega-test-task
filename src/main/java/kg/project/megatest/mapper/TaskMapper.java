package kg.project.megatest.mapper;

import kg.project.megatest.entity.Task;
import kg.project.megatest.model.request.TaskRequest;
import kg.project.megatest.model.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {

        }
)
public interface TaskMapper {

    TaskResponse entityToResponse(Task user);

    Task requestToEntity(TaskRequest request);

    void update(@MappingTarget Task entity, TaskRequest request);
}
