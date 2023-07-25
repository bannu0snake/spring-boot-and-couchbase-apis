package com.mahaboob.assignment.services;

import com.mahaboob.assignment.exceptions.EmployeeNotFoundException;
import com.mahaboob.assignment.exceptions.NthManagerNotFoundException;
import com.mahaboob.assignment.models.Employee;
import com.mahaboob.assignment.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public Employee addEmployee(final Employee employee) {
        if (employee.getManagerId() != null
                && !employeeRepository.existsById(employee.getManagerId())) {
            throw new EmployeeNotFoundException(employee.getManagerId());
        }
        Employee newEmployee = employeeRepository.save(employee);
        Employee manager = getEmployee(newEmployee.getManagerId())
                .orElseThrow(() -> new EmployeeNotFoundException(newEmployee.getManagerId()));
        emailService.sendEmail(manager, newEmployee);
        return newEmployee;
    }

    public Optional<Employee> getEmployee(final String id) {
        return employeeRepository.findById(id);
    }

    public Page<Employee> getAllEmployee(
            final String sortBy,
            final String order,
            final int page,
            final int pageSize) {
        Sort sort = "asc".equals(order.toLowerCase()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize, sort));
        return employees;
    }

    public Employee updateEmployee(final String id, final Employee updatedEmployee) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        updatedEmployee.setId(id);
        return employeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(final String id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    public Employee findNthManager(String id, int n) {
        Employee manager = null;
        for (int i = 0; i <= n; i++) {
            if (id == null) {
                throw new NthManagerNotFoundException(n);
            }
            String finalId = id;
            manager = getEmployee(id)
                    .orElseThrow(() -> new EmployeeNotFoundException(finalId));
            id = manager.getManagerId();
        }
        return manager;

    }
}
