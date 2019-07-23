package com.company.suayb.repository;

import com.company.suayb.model.User;
import com.company.suayb.shared.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUserName(String userName);
}
