package com.pranitpatil.service;

import com.pranitpatil.controller.PagedResponse;
import com.pranitpatil.entity.Employee;
import com.pranitpatil.repository.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    PagedResponse<Employee> getAllEmployees(Pageable pageablem, List<SearchCriteria> searchCriteria);

    Employee getEmployeeById(long id);
}
