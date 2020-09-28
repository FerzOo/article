package ir.maktab39.repositories.role;

import ir.maktab39.base.repository.BaseRepository;
import ir.maktab39.entities.Role;

public interface RoleRepo extends BaseRepository<Long, Role> {
    Role getRole(String title);
}
