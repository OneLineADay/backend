package com.lambda.oladbe.repository;

import com.lambda.oladbe.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
