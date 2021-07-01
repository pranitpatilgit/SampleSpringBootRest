package com.pranitpatil.service;

import com.pranitpatil.controller.PagedResponse;
import com.pranitpatil.entity.Employee;
import com.pranitpatil.exception.ApplicationException;
import com.pranitpatil.repository.EmployeeCriteriaSpecification;
import com.pranitpatil.repository.EmployeeRepository;
import com.pranitpatil.repository.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public PagedResponse<Employee> getAllEmployees(Pageable pageable, List<SearchCriteria> searchCriteria) {

        Page<Employee> page;

        if(searchCriteria.isEmpty()){
            page = employeeRepository.findAll(pageable);
        }
        else {
            Specification<Employee> specification = getEmployeeSpecification(searchCriteria);
            page = employeeRepository.findAll(specification, pageable);
        }

        PagedResponse<Employee> response = new PagedResponse<>();
        response.setEntity(page.getContent());
        response.setPageNumber(page.getNumber());
        response.setTotalItems(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }

    private Specification<Employee> getEmployeeSpecification( List<SearchCriteria> searchCriteria) {
        Specification<Employee> criteria = Specification.where(new EmployeeCriteriaSpecification(
                new SearchCriteria("id", SearchCriteria.Operation.IS_NOT_NULL, null)));

        for(SearchCriteria sc : searchCriteria){
            criteria = criteria.and(new EmployeeCriteriaSpecification(sc));
        }

        return criteria;
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ApplicationException("Employee Not Found."));
    }
}
