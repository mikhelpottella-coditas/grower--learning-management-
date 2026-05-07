package com.practise.grower.repository;

import com.practise.grower.entity.Manager;
import com.practise.grower.entity.User;
import com.practise.grower.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long>
{
    User findByUsername(String username);
    boolean existsByEmail(String email);

    List<User> findAllByRole(Role role);

    List<User> findAllByManager(Manager manager);


    List<User> findByRole(Role role);
}
