package com.pranitpatil.controller;

import com.pranitpatil.entity.Department;
import com.pranitpatil.exception.ApplicationException;
import com.pranitpatil.repository.SearchCriteria;
import com.pranitpatil.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "rest/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("{id}")
    public ResponseEntity findEmployee(@PathVariable long id){
        try {
            return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
        }
        catch (ApplicationException e){
            logger.error("Error while getting employee.", e);
            return new ResponseEntity<>(e.getUserMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("search")
    public ResponseEntity<PagedResponse> getAllEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String date,
            @PageableDefault(page = 0, size = 20)
            @SortDefault(sort = "name", direction = Sort.Direction.DESC)
            Pageable pageable){

        List<SearchCriteria> criteria = new ArrayList<>();
        if(StringUtils.isNotEmpty(name))
            criteria.add(new SearchCriteria("name", SearchCriteria.Operation.EQUALS, name));

        if(StringUtils.isNotEmpty(department))
            criteria.add(new SearchCriteria("department", SearchCriteria.Operation.EQUALS, Department.valueOf(department)));

        if(StringUtils.isNotEmpty(date))
            criteria.add(new SearchCriteria("createdAt", SearchCriteria.Operation.GREATER_THAN, new Date(date)));

        return new ResponseEntity<>(employeeService.getAllEmployees(pageable, criteria), HttpStatus.OK);
    }
}
