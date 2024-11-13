package kg.project.megatest.service;




import kg.project.megatest.model.request.RoleRequest;
import kg.project.megatest.model.response.RoleResponse;

import java.util.List;


public interface RoleService {
    public RoleResponse create(RoleRequest roleRequest);
    public RoleResponse findById(Long id);

    public RoleResponse update(RoleRequest roleRequest, Long roleId);
    public List<RoleResponse> findAll() ;
    void deleteById(Long id);

}
