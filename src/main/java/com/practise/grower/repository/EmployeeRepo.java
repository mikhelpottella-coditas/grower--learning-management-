package com.practise.grower.repository;

import com.practise.grower.entity.Employee;
import com.practise.grower.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {


}
