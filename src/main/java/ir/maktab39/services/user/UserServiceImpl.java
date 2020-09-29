package ir.maktab39.services.user;

import ir.maktab39.base.service.BaseServiceImpl;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.UniqueConstraintException;
import ir.maktab39.repositories.user.UserRepo;
import ir.maktab39.repositories.user.UserRepoImpl;

public class UserServiceImpl
        extends BaseServiceImpl<Long, User, UserRepo> implements UserService {

    public UserServiceImpl() {
        super(UserRepoImpl.class);
    }

    public User getUser(String username, String password) {
        return repository.getUser(username, password);
    }

}
