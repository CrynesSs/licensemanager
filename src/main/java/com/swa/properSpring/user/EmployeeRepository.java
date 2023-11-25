package com.swa.properSpring.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT u FROM Employee u WHERE u.username = :username")
    Employee findByUsername(@Param("username") String username);
}
