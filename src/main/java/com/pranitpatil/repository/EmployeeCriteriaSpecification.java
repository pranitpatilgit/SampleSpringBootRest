package com.pranitpatil.repository;

import com.pranitpatil.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeCriteriaSpecification implements Specification<Employee> {

    private SearchCriteria searchCriteria;

    public EmployeeCriteriaSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.searchCriteria.getOperation()){
            case EQUALS:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());

            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());

            case IS_NULL:
                return criteriaBuilder.isNull(root.get(searchCriteria.getKey()));

            case IS_NOT_NULL:
                return criteriaBuilder.isNotNull(root.get(searchCriteria.getKey()));

            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), (Comparable) searchCriteria.getValue());

            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), (Comparable) searchCriteria.getValue());

        }

        return null;
    }
}
