package ir.maktab39.services.user.admin;

import ir.maktab39.entities.User;
import ir.maktab39.services.user.UserServiceImpl;

import java.util.List;

public class AdminService extends UserServiceImpl {

    public List<User> findUsers() {
        return findAll();
    }

}
