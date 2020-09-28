package ir.maktab39.services.user;

import ir.maktab39.base.service.BaseService;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.UniqueConstraintException;

public interface UserService extends BaseService<User, Long> {
    User getUser(String username, String password);

}
