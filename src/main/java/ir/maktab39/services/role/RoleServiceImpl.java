package ir.maktab39.services.role;

import ir.maktab39.base.service.BaseServiceImpl;
import ir.maktab39.entities.Role;
import ir.maktab39.repositories.role.RoleRepo;
import ir.maktab39.repositories.role.RoleRepoImpl;

public class RoleServiceImpl
        extends BaseServiceImpl<Long, Role, RoleRepo> implements RoleService {
    public RoleServiceImpl() {
        super(RoleRepoImpl.class, Role.class);
    }

    @Override
    public Role getRole(String title) {
        return repository.getRole(title);
    }
}
