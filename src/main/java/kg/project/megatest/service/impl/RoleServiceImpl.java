package kg.project.megatest.service.impl;

import kg.project.megatest.entity.Role;
import kg.project.megatest.exception.RecordNotFoundException;
import kg.project.megatest.mapper.RoleMapper;
import kg.project.megatest.model.request.RoleRequest;
import kg.project.megatest.model.response.RoleResponse;
import kg.project.megatest.repository.RoleRepository;
import kg.project.megatest.service.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        if (roleRepository.getByName(roleRequest.getName()) != null) {
            throw new RecordNotFoundException("Роль с таким именем существует!");
        }

        Role role = roleMapper.requestToEntity(roleRequest);
        Role savedRole = roleRepository.save(role);
        return roleMapper.entityToResponse(savedRole);
    }

    @Override
    public RoleResponse findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Роль с таким id не существует!"));
        return roleMapper.entityToResponse(role);
    }

    @Override
    public RoleResponse update(RoleRequest roleRequest, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RecordNotFoundException("Роль с таким id не существует"));
        roleMapper.update(role, roleRequest);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.entityToResponse(updatedRole);
    }

    @Override
    public List<RoleResponse> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Role role = this.roleRepository.getById(id);
        roleRepository.deleteById(id);
    }

}
