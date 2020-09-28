package ir.maktab39.services.role;

import ir.maktab39.base.service.BaseService;
import ir.maktab39.entities.Role;

public interface RoleService extends BaseService<Role, Long> {
    Role getRole(String title);
}
