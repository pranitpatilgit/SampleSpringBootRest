package com.pranitpatil.repository;

import com.pranitpatil.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> , JpaSpecificationExecutor<Employee> {

    @Override
    Page<Employee> findAll(Pageable pageable);

    @Override
    Optional<Employee> findById(Long id);
}
