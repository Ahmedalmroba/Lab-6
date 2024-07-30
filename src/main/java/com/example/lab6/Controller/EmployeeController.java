package com.example.lab6.Controller;
import com.example.lab6.Api.Api;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String Message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(Message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new Api("employee added"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, @Valid @RequestBody Employee employee, Errors errors) {

        if (errors.hasErrors()) {
            String Message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(Message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(200).body(new Api("employee updated"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {

        Employee employeeToDelete = null;
        for (Employee employee : employees) {
            if (employee.getId().equals(index)) {
                employeeToDelete = employee;
                break;
            }
        }
        if (employeeToDelete != null) {
            employees.remove(employeeToDelete);
            return ResponseEntity.status(200).body("Employee deleted successfully");
        } else {
            return ResponseEntity.status(400).body("Employee not found");

        }
    }

    @GetMapping("/position/{position}")
    public ResponseEntity getEmployeesByPosition(@RequestBody String position) {

        for (Employee employee : employees) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                employees.add(employee);
            }
        }
        if (employees.isEmpty()) {
            return ResponseEntity.status(400).body("Employee not found");
        } else {
            return ResponseEntity.ok(employees);
        }

    }
    @GetMapping("/age-range/{minAge}/{maxAge}")
    public ResponseEntity getEmployeesByAgeRange(@PathVariable int minAge, @PathVariable int maxAge) {

        for (Employee employee : employees) {
            if (employee.getAge() >= minAge && employee.getAge() <= maxAge) {
                employees.add(employee);
            }
        }

        if (employees.isEmpty()) {
            return ResponseEntity.status(400).body("Employee not found");
        } else {
            return ResponseEntity.ok(employees);

        }
    }

    @PostMapping("/apply-leave/{employeeId}")
    public ResponseEntity applyLeave(@Valid @RequestBody String employeeId, Errors errors) {
        if (errors.hasErrors()) {
            String Message = errors.getFieldError().getDefaultMessage();

            for (Employee employee : employees) {
                if (employee.getId().equals(employeeId)) {
                    if (employee == null) {
                        return ResponseEntity.status(400).body("Employee not found");
                    }
                    if (employee.isOnLeave() || employee.getAnnualLeave() <= 0) {
                        return ResponseEntity.status(400).body("Employee cannot apply for leave.");
                    }

                    employee.setOnLeave(true);
                    employee.setAnnualLeave(employee.getAnnualLeave() - 1);
                }
            }
            return ResponseEntity.status(400).body("Employee cannot apply for leave.");
        }
         return  ResponseEntity.status(400).body("Employee cannot apply for leave.");
    }
        @GetMapping("/no-annual-leave")
        public ResponseEntity getEmployeesWithNoAnnualLeave() {

    for (Employee employee : employees) {
        if (employee.getAnnualLeave() <= 0) {
            employees.add(employee);
        }
    }
    return ResponseEntity.status(200).body(employees);
}
    @PutMapping("/promote/{employeeId}")
    public ResponseEntity promoteEmployee(@Valid @RequestBody String employeeId ,Errors errors ) {

        if (errors.hasErrors()) {
            String Message = errors.getFieldError().getDefaultMessage();

        Employee employeeToPromote = null;
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                employeeToPromote = employee;
                break;
            }
        }

        if (employeeToPromote == null) {
            return ResponseEntity.status(200).body(employeeToPromote);
        }
        if (employeeToPromote.getAge() >= 30 && !employeeToPromote.isOnLeave()) {
            employeeToPromote.setPosition("supervisor");
        } else {
            return ResponseEntity.status(400).body(new Api("Employee does not meet criteria for promotion."));
        }}

        return ResponseEntity.status(200).body(new Api("Employee promoted successfully"));
        }}
















