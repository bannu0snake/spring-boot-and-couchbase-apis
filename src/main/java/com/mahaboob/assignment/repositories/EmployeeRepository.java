package com.mahaboob.assignment.repositories;

import com.mahaboob.assignment.models.Employee;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CouchbaseRepository<Employee, String> {

}
