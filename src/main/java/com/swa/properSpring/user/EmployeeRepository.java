package com.swa.properSpring.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface EmployeeRepository extends JpaRepository<Employee, Long>, UserDetailsService {

    @Query("SELECT u FROM Employee u WHERE u.username = :username")
    Employee findByUsername(@Param("username") String username);

    @Query("SELECT u from Employee u WHERE u.customer.customerName = :customerName")
    Set<Employee> findByCustomerName(@Param("customerName") String customerName);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = findByUsername(username);
        if(employee != null)return employee;
        return new Employee();
    }
}
