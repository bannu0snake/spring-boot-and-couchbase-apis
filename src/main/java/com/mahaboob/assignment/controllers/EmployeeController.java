package com.mahaboob.assignment.controllers;

import com.mahaboob.assignment.exceptions.EmployeeNotFoundException;
import com.mahaboob.assignment.models.Employee;
import com.mahaboob.assignment.services.EmailService;
import com.mahaboob.assignment.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmailService emailService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Ping to end point");
    }

    @GetMapping()
    public ResponseEntity<Page<Employee>> getAll(
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getAllEmployee(sortBy, order, page, pageSize));
    }

    @GetMapping(value = {"/{id}", "/{id}/{n}"})
    public ResponseEntity<Employee> get(@PathVariable("id") final String id,
                                        @PathVariable(value = "n", required = false) Integer n) {
        Employee employee = null;
        if(n == null){
            employee = employeeService.getEmployee(id)
                    .orElseThrow(() -> new EmployeeNotFoundException(id));
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }

        employee = employeeService.findNthManager(id, n);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping()
    public ResponseEntity<Employee> addEmployee(@RequestBody final Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeB(
            @PathVariable("id") String id,
            @RequestBody final Employee employee) {
        Employee newEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newEmployee);
    }

    @PostMapping("/sendEmail/{id}")
    public ResponseEntity<Void> sendEmail(@PathVariable final String id) {
        Employee employee = employeeService.getEmployee(id)
                .orElseThrow(()-> new EmployeeNotFoundException(id));
        Employee manager = employeeService.getEmployee(employee.getManagerId())
                .orElseThrow(()-> new EmployeeNotFoundException(employee.getManagerId()));
        emailService.sendEmail(manager, employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
