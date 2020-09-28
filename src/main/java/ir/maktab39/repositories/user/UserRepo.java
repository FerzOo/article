package ir.maktab39.repositories.user;

import ir.maktab39.base.repository.BaseRepository;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.UniqueConstraintException;

public interface UserRepo extends BaseRepository<Long, User> {
    User getUser(String username, String password);

}
