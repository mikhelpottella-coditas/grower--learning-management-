package com.practise.grower.repository;


import com.practise.grower.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepo extends JpaRepository<Module, Long> {
}
