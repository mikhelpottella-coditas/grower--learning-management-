package com.practise.grower.repository;

import com.practise.grower.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface UserRepo extends JpaRepository<User,Long>
{
    User findByUsername(String username);
}
